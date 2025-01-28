package ec.edu.monster.phoneshop.dto;

import ec.edu.monster.banquito.dto.CreditApplicationResponseDto;
import ec.edu.monster.banquito.dto.ResponseStatus;
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
