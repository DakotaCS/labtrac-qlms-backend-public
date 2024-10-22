package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.SolidUnitDto;
import com.quantus.backend.models.system.SolidUnit;
import com.quantus.backend.services.system.SolidUnitService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-08
 *
 *  Note: Do not store units in other models as objects. Use only the properties of the unit as units may
 *  be deleted by admins and do not have safety checks on them prior to deletion.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/unit/solid")
public class SolidUnitController {

    private final SolidUnitService solidUnitService;

    /**
     * Retrieve all Solid Unit
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllSolidUnits() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                solidUnitService.findAllSolidUnits(), SolidUnitDto.class));
    }

    /**
     * Create a Solid Unit
     * Constraints: Solid names and codes must be unique.
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createSolidUnit(@RequestBody SolidUnitDto solidUnitDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                solidUnitService.createSolidUnit(DozerEntityMapper.mapObject(solidUnitDto, SolidUnit.class)),
                SolidUnitDto.class));
    }

    /**
     * Update a Solid Unit
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateSolidUnit(
            @PathVariable(name = "id") Integer solidUnitId,
            @RequestBody SolidUnitDto solidUnitDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                solidUnitService.updateSolidUnit(solidUnitId, solidUnitDto.getQuantityUnit(),
                        solidUnitDto.getQuantityUnitCode()), SolidUnitDto.class));
    }

    /**
     * Delete a Solid Unit
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteLocation(
            @PathVariable(name = "id") Integer solidUnitId) {
        solidUnitService.deleteSolidUnit(solidUnitId);
        return ResponseEntity.ok("");
    }
}
