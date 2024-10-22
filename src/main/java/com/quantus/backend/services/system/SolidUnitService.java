package com.quantus.backend.services.system;

import com.quantus.backend.models.system.SolidUnit;
import com.quantus.backend.repositories.system.SolidUnitRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

@Service
@RequiredArgsConstructor
public class SolidUnitService {

    private final SolidUnitRepository solidUnitRepository;

    public List<SolidUnit> findAllSolidUnits() {
        return solidUnitRepository.getAll();
    }

    public SolidUnit findSolidUnitById(int id) {
        SolidUnit solidUnit = solidUnitRepository.getOne(id);
        if(solidUnit == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The unit cannot be found");
        }
        return solidUnit;
    }

    public SolidUnit createSolidUnit(SolidUnit liquidUnit) {
        return solidUnitRepository.save(liquidUnit);
    }

    public SolidUnit updateSolidUnit(Integer id, String unit, String code) {
        SolidUnit solidUnit = findSolidUnitById(id);
        if(findSolidUnitByCode(code)!=null || findSolidUnitByUnit(unit)!=null) {
            throw new CustomExceptionHandler.BadRequestCustomException("You cannot have duplicate unit entries.");
        }
        solidUnit.setQuantityUnit(unit);
        solidUnit.setQuantityUnitCode(code);
        return solidUnitRepository.save(solidUnit);
    }

    public void deleteSolidUnit(Integer id) {
        SolidUnit solidUnit = findSolidUnitById(id);
        solidUnitRepository.logicalDelete(solidUnit);
    }

    public SolidUnit findSolidUnitByCode(String code) {
        return solidUnitRepository.findByQuantityUnitCode(code);
    }

    public SolidUnit findSolidUnitByUnit(String unit) {
        return solidUnitRepository.findByQuantityUnit(unit);
    }
}
