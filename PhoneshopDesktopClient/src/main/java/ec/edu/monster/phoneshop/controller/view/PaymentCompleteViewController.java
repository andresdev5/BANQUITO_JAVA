package ec.edu.monster.phoneshop.controller.view;

import ec.edu.monster.phoneshop.App;
import ec.edu.monster.phoneshop.dto.CreditInstallmentDto;
import ec.edu.monster.phoneshop.dto.PurchaseResponseDto;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentCompleteViewController extends BaseView {
    @Getter
    @Setter
    @Builder
    public static class CreditInstallmentTableItem {
        private String number;
        private String amount;
        private String capital;
        private String interest;
        private String balance;
    }

    @FXML
    WebView invoiceWebView;

    @FXML
    Button printButton;

    @FXML
    TableView<CreditInstallmentTableItem> creditInstallmentsTableView = new TableView<>();

    @FXML
    TableColumn<CreditInstallmentTableItem, String> installmentNumberColumn;
    @FXML
    TableColumn<CreditInstallmentTableItem, String> installmentAmountColumn;
    @FXML
    TableColumn<CreditInstallmentTableItem, String> installmentInterestColumn;
    @FXML
    TableColumn<CreditInstallmentTableItem, String> installmentCapitalColumn;
    @FXML
    TableColumn<CreditInstallmentTableItem, String> installmentBalanceColumn;

    private PurchaseResponseDto purchaseResponse;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        printButton.setOnAction(ev -> {
            printInvoice();
        });

        installmentNumberColumn.setCellValueFactory(new PropertyValueFactory<CreditInstallmentTableItem, String>("number"));
        installmentAmountColumn.setCellValueFactory(new PropertyValueFactory<CreditInstallmentTableItem, String>("amount"));
        installmentInterestColumn.setCellValueFactory(new PropertyValueFactory<CreditInstallmentTableItem, String>("interest"));
        installmentCapitalColumn.setCellValueFactory(new PropertyValueFactory<CreditInstallmentTableItem, String>("capital"));
        installmentBalanceColumn.setCellValueFactory(new PropertyValueFactory<CreditInstallmentTableItem, String>("balance"));
    }

    @Override
    public void preNavigate(java.util.Map<String, Object> parameters) {
        purchaseResponse = (PurchaseResponseDto) parameters.get("purchaseResponse");
        invoiceWebView.getEngine().load(parameters.get("invoiceUrl").toString());
        invoiceWebView.setZoom(1.2);

        if (purchaseResponse.getCredit() != null && purchaseResponse.getCredit().getInstallments() != null) {
            creditInstallmentsTableView.setVisible(true);
            creditInstallmentsTableView.setPrefHeight(200);
            populateCreditInstallmentsTable();
        } else {
            creditInstallmentsTableView.setVisible(false);
            creditInstallmentsTableView.setPrefHeight(0);
            creditInstallmentsTableView.getItems().clear();
        }
    }

    private void printInvoice() {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        if (printerJob == null || !printerJob.showPrintDialog(App.getScene().getWindow())) {
            return;
        }

        printerJob.getJobSettings().setJobName(purchaseResponse.getInvoicePath().replace(".pdf", ""));
        invoiceWebView.getEngine().print(printerJob);
        printerJob.endJob();
    }

    private void populateCreditInstallmentsTable() {
        List<CreditInstallmentDto> installments = purchaseResponse.getCredit().getInstallments();
        List<CreditInstallmentTableItem> items = new ArrayList<>();
        int number = 1;

        for (CreditInstallmentDto installment : installments) {
            items.add(CreditInstallmentTableItem.builder()
                    .number(String.valueOf(number++))
                    .amount(String.valueOf(installment.getAmount()))
                    .capital(String.valueOf(installment.getCapital()))
                    .interest(String.valueOf(installment.getInterest()))
                    .balance(String.valueOf(installment.getBalance()))
                    .build());
        }

        creditInstallmentsTableView.getItems().setAll(items);
    }
}
