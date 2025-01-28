package ec.edu.monster.phoneshop.service.impl;

import ec.edu.monster.banquito.dto.CreditApplicationRequestDto;
import ec.edu.monster.banquito.dto.CreditApplicationResponseDto;
import ec.edu.monster.banquito.dto.InvoiceDetailItemReportDto;
import ec.edu.monster.banquito.dto.ResponseStatus;
import ec.edu.monster.banquito.entity.User;
import ec.edu.monster.banquito.repository.UserRepository;
import ec.edu.monster.banquito.service.CreditService;
import ec.edu.monster.phoneshop.dto.PurchaseItemDto;
import ec.edu.monster.phoneshop.dto.PurchaseRequestDto;
import ec.edu.monster.phoneshop.dto.PurchaseResponseDto;
import ec.edu.monster.phoneshop.entity.Product;
import ec.edu.monster.phoneshop.entity.Sale;
import ec.edu.monster.phoneshop.entity.SaleDetail;
import ec.edu.monster.phoneshop.enums.PurchaseMethod;
import ec.edu.monster.phoneshop.repository.ProductRepository;
import ec.edu.monster.phoneshop.repository.SaleRepository;
import ec.edu.monster.phoneshop.service.PurchaseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseServiceImpl implements PurchaseService {
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final UserRepository userRepository;
    private final CreditService creditService;
    private final ModelMapper modelMapper;

    @Value("${app.phoneshop.sales.tax-percentage}")
    private Double taxPercentageValue;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public PurchaseResponseDto purchase(PurchaseRequestDto request) throws Exception {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal subtotal;
        BigDecimal taxAmount;
        BigDecimal taxPercentage = BigDecimal.valueOf(taxPercentageValue);
        List<SaleDetail> details = new ArrayList<>();

        if (request.getItems().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La lista de productos no puede estar vacía");
        }

        for (PurchaseItemDto item : request.getItems()) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado"));
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            details.add(SaleDetail.builder()
                    .product(product)
                    .quantity(item.getQuantity())
                    .unitPrice(product.getPrice())
                    .build());
        }

        subtotal = total;
        taxAmount = total.multiply(taxPercentage);
        total = total.add(taxAmount);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado"));

        Sale sale = Sale.builder()
                .subtotal(subtotal)
                .total(total)
                .taxAmount(taxAmount)
                .taxPercentage(taxPercentage)
                .purchaseMethod(request.getMethod())
                .user(User.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .identificationNumber(user.getIdentificationNumber())
                        .build())
                .details(details)
                .build();

        CreditApplicationResponseDto creditApplicationResponse;
        PurchaseResponseDto result = PurchaseResponseDto.builder()
                .status(ResponseStatus.SUCCESS)
                .message("Compra realizada con éxito")
                .build();

        if (request.getMethod() == PurchaseMethod.CREDIT) {
            try {
                creditApplicationResponse = creditService.applyForCredit(CreditApplicationRequestDto.builder()
                        .identificationNumber(user.getIdentificationNumber())
                        .amount(total)
                        .months(request.getMonths())
                        .build());
                result.setCredit(creditApplicationResponse);
            } catch (ResponseStatusException e) {
                return PurchaseResponseDto.builder()
                        .status(ResponseStatus.ERROR)
                        .message(e.getMessage())
                        .build();
            }
        }

        sale.setDetails(details.stream().peek(detail -> detail.setSale(sale)).toList());
        Sale saved = saleRepository.save(sale);

        try {
            result.setInvoicePath(generateInvoice(saved));
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al generar la factura, intentalo denuevo");
        }

        return result;
    }

    private String generateInvoice(Sale sale) throws Exception {
        List<InvoiceDetailItemReportDto> items = sale.getDetails().stream().map(detail -> InvoiceDetailItemReportDto.builder()
                .productName(detail.getProduct().getName())
                .quantity(detail.getQuantity())
                .unitPrice(detail.getUnitPrice().setScale(2, RoundingMode.HALF_UP))
                .total(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())).setScale(2, RoundingMode.HALF_UP))
                .build()).toList();
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(items);

        Long invoiceNumber = saleRepository.getLastSaleNumber();
        String invoiceNumberFormatted = String.format("%010d", invoiceNumber);

        Map<String, Object> parameters = new HashMap<>(Map.of(
                "INVOICE_NUMBER", invoiceNumberFormatted,
                "INVOICE_TOTAL", sale.getTotal().setScale(2, RoundingMode.HALF_UP),
                "INVOICE_SUBTOTAL", sale.getSubtotal().setScale(2, RoundingMode.HALF_UP),
                "INVOICE_TAX_AMOUNT", sale.getTaxAmount().setScale(2, RoundingMode.HALF_UP),
                "INVOICE_TAX_PERCENTAGE", sale.getTaxPercentage().multiply(BigDecimal.valueOf(100)).setScale(2, RoundingMode.HALF_UP),
                "PAYMENT_METHOD", sale.getPurchaseMethod().name(),
                "CUSTOMER_IDENTIFICATION", sale.getUser().getIdentificationNumber(),
                "CUSTOMER_EMAIL", sale.getUser().getEmail(),
                "INVOICE_DETAILS_DATASOURCE", dataSource
        ));

        String filename = String.format("invoice-%s.pdf", sale.getId());
        Path path = Path.of("uploads/invoices", filename);
        File report = ResourceUtils.getFile("classpath:jasper/invoice.jasper");
        File outputFile = path.toFile();
        OutputStream outputStream = new FileOutputStream(outputFile);
        JasperPrint jasperPrint = JasperFillManager.fillReport(report.getAbsolutePath(), parameters, new JREmptyDataSource());
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
        outputStream.close();

        return "/" + path.toString().replace("\\", "/");
    }
}
