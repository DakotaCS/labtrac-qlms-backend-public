package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.LiquidUnit;
import com.quantus.backend.repositories.GeneralRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */

public interface LiquidUnitRepository extends GeneralRepository<LiquidUnit, Integer> {
    LiquidUnit findByQuantityUnitCode(String quantityUnitCode);
    LiquidUnit findByQuantityUnit(String quantityUnit);
}
