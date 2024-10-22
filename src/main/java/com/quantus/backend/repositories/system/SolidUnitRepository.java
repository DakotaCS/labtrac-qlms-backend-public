package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.SolidUnit;
import com.quantus.backend.repositories.GeneralRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 */


public interface SolidUnitRepository extends GeneralRepository<SolidUnit, Integer> {
    SolidUnit findByQuantityUnitCode(String quantityUnitCode);
    SolidUnit findByQuantityUnit(String quantityUnit);
}
