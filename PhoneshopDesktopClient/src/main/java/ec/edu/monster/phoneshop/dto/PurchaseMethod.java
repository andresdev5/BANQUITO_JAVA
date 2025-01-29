package ec.edu.monster.phoneshop.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PurchaseMethod {
    CASH("EFECTIVO"),
    CREDIT("CRÉDITO");

    private final String label;

    @Override
    public String toString() {
        return label;
    }
}
