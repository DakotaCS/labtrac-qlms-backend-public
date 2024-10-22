package com.quantus.backend.controllers.system;

import com.quantus.backend.controllers.system.dto.LocationDto;
import com.quantus.backend.controllers.system.dto.LocationAndCategoryPatchRequest;
import com.quantus.backend.models.system.Location;
import com.quantus.backend.services.inventory.InventoryItemService;
import com.quantus.backend.services.system.LocationService;
import com.quantus.backend.utils.DozerEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-13
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.url.prefix}/system/location")
public class LocationController {

    public final LocationService locationService;
    public final InventoryItemService inventoryItemService;

    /**
     * Retrieve all Locations
     */
    @GetMapping(value = "")
    public ResponseEntity<Object> findAllLocations() {
        return ResponseEntity.ok(DozerEntityMapper.mapObjectList(
                locationService.findAllLocations(), LocationDto.class));
    }

    /**
     * Retrieve a Location by ID
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<Object> findLocationById(@PathVariable(name = "id") Integer locationId) {
        return ResponseEntity.ok(
                DozerEntityMapper.mapObject(locationService.findLocationById(locationId), LocationDto.class));
    }

    /**
     * Create a Location
     * Constraints: Location names must be unique
     */
    @PostMapping(value = "")
    public ResponseEntity<Object> createLocation(@RequestBody LocationDto locationDto) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                locationService.createLocation(DozerEntityMapper.mapObject(locationDto, Location.class)
                ), LocationDto.class));
    }

    /**
     * Update a Location
     */
    @PatchMapping(value = "/{id}")
    public ResponseEntity<Object> updateLocation(
            @PathVariable(name = "id") Integer locationId,
            @RequestBody LocationAndCategoryPatchRequest locationAndCategoryPatchRequest) {
        return ResponseEntity.ok(DozerEntityMapper.mapObject(
                locationService.updateLocation(locationAndCategoryPatchRequest.getName(),
                        locationAndCategoryPatchRequest.getDescription(), locationId), LocationDto.class));
    }

    /**
     * Delete a Location
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Object> deleteLocation(
            @PathVariable(name = "id") Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.ok("");
    }
}
