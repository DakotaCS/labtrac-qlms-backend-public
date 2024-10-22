package com.quantus.backend.services.system;

import com.quantus.backend.models.system.LiquidUnit;
import com.quantus.backend.repositories.system.LiquidUnitRepository;
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
public class LiquidUnitService {

    private final LiquidUnitRepository liquidUnitRepository;

    public List<LiquidUnit> findAllLiquidUnits() {
        return liquidUnitRepository.getAll();
    }

    public LiquidUnit findLiquidUnitById(int id) {
        LiquidUnit liquidUnit = liquidUnitRepository.getOne(id);
        if(liquidUnit == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The unit cannot be found");
        }
        return liquidUnit;
    }

    public LiquidUnit createLiquidUnit(LiquidUnit liquidUnit) {
        return liquidUnitRepository.save(liquidUnit);
    }

    public LiquidUnit updateLiquidUnit(Integer id, String unit, String code) {
        LiquidUnit liquidUnit = findLiquidUnitById(id);
        if(findLiquidUnitByCode(code)!=null || findLiquidUnitByUnit(unit)!=null) {
            throw new CustomExceptionHandler.BadRequestCustomException("You cannot have duplicate unit entries.");
        }
        liquidUnit.setQuantityUnit(unit);
        liquidUnit.setQuantityUnitCode(code);
        return liquidUnitRepository.save(liquidUnit);
    }

    public void deleteLiquidUnit(Integer id) {
        LiquidUnit liquidUnit = findLiquidUnitById(id);
        liquidUnitRepository.logicalDelete(liquidUnit);
    }

    public LiquidUnit findLiquidUnitByCode(String code) {
        return liquidUnitRepository.findByQuantityUnitCode(code);
    }

    public LiquidUnit findLiquidUnitByUnit(String unit) {
        return liquidUnitRepository.findByQuantityUnit(unit);
    }
}
