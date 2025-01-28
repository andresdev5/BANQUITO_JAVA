package ec.edu.monster.phoneshop.controller;

import ec.edu.monster.phoneshop.dto.PurchaseRequestDto;
import ec.edu.monster.phoneshop.dto.PurchaseResponseDto;
import ec.edu.monster.phoneshop.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public/purchase")
@RequiredArgsConstructor
public class PurchaseController {
    private final PurchaseService purchaseService;

    @PostMapping
    public PurchaseResponseDto purchase(@RequestBody PurchaseRequestDto request) throws Exception {
        return purchaseService.purchase(request) ;
    }
}
