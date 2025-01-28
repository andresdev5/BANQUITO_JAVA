package ec.edu.monster.phoneshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponseDto {
    private String message;
    private CreditApplicationResponseDto credit;
    private ResponseStatus status;
    private String invoicePath;
}
