package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.LiquidUnitDto;
import com.quantus.backend.models.system.LiquidUnit;
import com.quantus.backend.services.system.LiquidUnitService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 *
 * Note: Do not store units in other models as objects. Use only the properties of the unit as units may
 * be deleted by admins and do not have safety checks on them prior to deletion.
 *
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/unit/liquid")
public class LiquidUnitController {

    private final LiquidUnitService liquidUnitService;

    /**
     * Retrieve all Liquid Unit
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllLiquidUnits() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                liquidUnitService.findAllLiquidUnits(), LiquidUnitDto.class));
    }

    /**
     * Create a Liquid Unit
     * Constraints: Liquid names and codes must be unique.
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createLiquidUnit(@RequestBody LiquidUnitDto liquidUnitDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                liquidUnitService.createLiquidUnit(DozerEntityMapper.mapObject(liquidUnitDto, LiquidUnit.class)
                ), LiquidUnitDto.class));
    }

    /**
     * Update a Liquid Unit
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateCategory(
            @PathVariable(name = "id") Integer liquidUnitId,
            @RequestBody LiquidUnitDto liquidUnitDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                liquidUnitService.updateLiquidUnit(liquidUnitId, liquidUnitDto.getQuantityUnit(),
                        liquidUnitDto.getQuantityUnitCode()), LiquidUnitDto.class));
    }

    /**
     * Delete a Liquid Unit
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteLocation(
            @PathVariable(name = "id") Integer liquidUnitId) {
        liquidUnitService.deleteLiquidUnit(liquidUnitId);
        return ResponseEntity.ok("");
    }
}
