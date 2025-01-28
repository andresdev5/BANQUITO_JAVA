package ec.edu.monster.phoneshop.dto;

import ec.edu.monster.phoneshop.entity.SaleDetail;
import ec.edu.monster.phoneshop.enums.PurchaseMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequestDto {
    private PurchaseMethod method;
    private int months;
    private UUID userId;
    private List<PurchaseItemDto> items;
}
