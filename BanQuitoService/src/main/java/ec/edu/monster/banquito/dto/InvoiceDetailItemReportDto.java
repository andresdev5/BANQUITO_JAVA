package ec.edu.monster.banquito.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class InvoiceDetailItemReportDto {
    private String productName;
    private int quantity;
    private BigDecimal unitPrice;
    private BigDecimal total;
}
