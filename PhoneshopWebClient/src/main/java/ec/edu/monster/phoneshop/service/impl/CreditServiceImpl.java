package ec.edu.monster.phoneshop.service.impl;

import ec.edu.monster.phoneshop.client.PhoneShopServerClient;
import ec.edu.monster.phoneshop.common.AppContextHolder;
import ec.edu.monster.phoneshop.dto.ApiCommunicationType;
import ec.edu.monster.phoneshop.dto.CheckCreditRequestDto;
import ec.edu.monster.phoneshop.dto.CheckCreditResultDto;
import ec.edu.monster.phoneshop.dto.ResponseStatus;
import ec.edu.monster.phoneshop.service.CreditService;
import ec.edu.monster.phoneshop.ws.AppServicePort;
import ec.edu.monster.phoneshop.ws.CheckCreditRequest;
import ec.edu.monster.phoneshop.ws.CheckCreditResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CreditServiceImpl implements CreditService {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    private final PhoneShopServerClient phoneshopBankServerClient;
    private final AppContextHolder appContextHolder;

    @Override
    public CheckCreditResultDto checkCredit(CheckCreditRequestDto request) {
        return appContextHolder.getAppContext().getApiCommunicationType() == ApiCommunicationType.REST
                ? checkCreditThroughRest(request)
                : checkCreditThroughSoap(request);
    }

    private CheckCreditResultDto checkCreditThroughRest(CheckCreditRequestDto request) {
        logger.info("Checking credit through REST");

        try {
            return phoneshopBankServerClient.checkCredit(request);
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return CheckCreditResultDto.builder()
                    .message("An error occurred while checking the credit")
                    .build();
        }
    }

    private CheckCreditResultDto checkCreditThroughSoap(CheckCreditRequestDto request) {
        logger.info("Checking credit through SOAP");

        AppServicePort port = appContextHolder.getAppContext().buildSoapClient();
        CheckCreditRequest checkCreditRequest = new CheckCreditRequest();
        checkCreditRequest.setAmount(request.getAmount());
        checkCreditRequest.setIdentification(request.getIdentificationNumber());

        try {
            CheckCreditResponse response = port.checkCredit(checkCreditRequest);
            return CheckCreditResultDto.builder()
                    .message(response.getMessage())
                    .eligible(response.isEligible())
                    .maxAmount(response.getMaxAmount())
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
            return CheckCreditResultDto.builder()
                    .message("Ocurrió un error al verificar el crédito")
                    .build();
        }
    }
}
