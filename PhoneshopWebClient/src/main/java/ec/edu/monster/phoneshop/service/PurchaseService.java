package ec.edu.monster.phoneshop.service;

import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.dto.PurchaseRequestDto;
import ec.edu.monster.phoneshop.dto.PurchaseResponseDto;

public interface PurchaseService {
    PurchaseResponseDto purchase(PurchaseRequestDto request);
}
