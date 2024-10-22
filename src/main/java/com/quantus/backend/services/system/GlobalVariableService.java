package com.quantus.backend.services.system;

import com.quantus.backend.models.system.GlobalVariable;
import com.quantus.backend.repositories.system.GlobalVariableRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.regex.Pattern;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-15
 */

@Service
@RequiredArgsConstructor
public class GlobalVariableService {

    private final GlobalVariableRepository globalVariableRepository;

    public String findGlobalVariableValueByCode(String code) {
        GlobalVariable globalVariable = globalVariableRepository.findGlobalVariableByCodeEquals(code);
        if(globalVariable == null) {
            throw new CustomExceptionHandler.NotFoundCustomException(
                    "The global variable corresponding to the code can't be found.");
        }
        return globalVariable.getValue();
    }

    public void updatePrinterLocale(String printerConfiguration) {
        if(!printerConfiguration.equals("LOCAL") && !printerConfiguration.equals("NETWORK")) {
            throw new CustomExceptionHandler.BadRequestCustomException("The printer configuration is invalid.");
        }
        GlobalVariable globalVariable = globalVariableRepository.findGlobalVariableByCodeEquals("PrinterConfig");
        if(globalVariable == null) {
            throw new CustomExceptionHandler.NotFoundCustomException(
                    "The global variable corresponding to the printer configuration can't be found.");
        }
        globalVariable.setValue(printerConfiguration);
        globalVariableRepository.save(globalVariable);
    }

    public void updatePrinterIp(String printerNetworkIp) {

        String ipv4Regex = "^((25[0-5]|2[0-4]\\d|1\\d{2}|[1-9]?\\d)(\\.|$)){4}$";

        boolean isValid = Pattern.matches(ipv4Regex, printerNetworkIp);
        if(!isValid) {
            throw new CustomExceptionHandler.BadRequestCustomException(
                    "The printer IP is invalid.");
        }

        GlobalVariable globalVariable = globalVariableRepository.findGlobalVariableByCodeEquals("PrinterNetworkIP");
        if(globalVariable == null) {
            throw new CustomExceptionHandler.NotFoundCustomException(
                    "The global variable corresponding to the printer IP can't be found.");
        }
        globalVariable.setValue(printerNetworkIp);
        globalVariableRepository.save(globalVariable);
    }

    public void updatePrinterPort(Integer printerNetworkPort) {

        if(printerNetworkPort != 9100 && printerNetworkPort != 9101) {
            throw new CustomExceptionHandler.BadRequestCustomException("The printer Port is invalid.");
        }

        GlobalVariable globalVariable = globalVariableRepository.findGlobalVariableByCodeEquals("PrinterNetworkPort");
        if(globalVariable == null) {
            throw new CustomExceptionHandler.NotFoundCustomException(
                    "The global variable corresponding to the printer port can't be found.");
        }
        globalVariable.setValue(String.valueOf(printerNetworkPort));
        globalVariableRepository.save(globalVariable);
    }
}
