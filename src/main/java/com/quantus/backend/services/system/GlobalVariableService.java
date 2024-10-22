package com.quantus.backend.services.system;

import com.quantus.backend.models.system.GlobalVariable;
import com.quantus.backend.repositories.system.GlobalVariableRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public void updateDefaultPrinter( String defaultPrinterUid) {
        GlobalVariable defaultPrinterUidVar = globalVariableRepository.
                findGlobalVariableByCodeEquals("defaultPrinterUid");
        if(defaultPrinterUidVar == null) {
            throw new CustomExceptionHandler.NotFoundCustomException(
                    "The global variable corresponding to the default printer UID can't be found.");
        }
        defaultPrinterUidVar.setValue(defaultPrinterUid);
        globalVariableRepository.save(defaultPrinterUidVar);
    }
}
