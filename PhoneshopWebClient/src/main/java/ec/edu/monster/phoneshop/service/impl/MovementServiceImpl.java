package ec.edu.monster.phoneshop.service.impl;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.dto.MovementDto;
import ec.edu.monster.phoneshop.service.MovementService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {
    private final PhoneShopServerClient phoneshopBankServerClient;
    private final HttpSession session;

    @Override
    public List<MovementDto> getCurrentUserMovements() {
        try {
            return phoneshopBankServerClient.getMovements();
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return List.of();
        }
    }
}
