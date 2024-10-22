package com.quantus.backend.services.system;

import com.quantus.backend.models.system.Location;
import com.quantus.backend.repositories.system.LocationRepository;
import com.quantus.backend.utils.CustomExceptionHandler;
import com.quantus.backend.utils.DateAndTimeUtils;
import com.quantus.backend.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

@Service
@RequiredArgsConstructor
public class LocationService {

    public final LocationRepository locationRepository;
    public final SysIdSequenceService sysIdSequenceService;

    public List<Location> findAllLocations() {
        return locationRepository.getAll();
    }

    public Location findLocationById(Integer locationId) {
        Location newLocation = locationRepository.getOne(locationId);
        if(newLocation == null) {
            throw new CustomExceptionHandler.NotFoundCustomException("The location cannot be found");
        }
        return newLocation;
    }

    public Location createLocation(Location newLocation) {

        if(StringUtils.IsStringEmptyOrNull(newLocation.getName())) {
            throw new CustomExceptionHandler.BadRequestCustomException("Location name is not valid.");
        }

        //If there is no matching names, then we can create the Location
        if (!checkIfLocationNameExists(newLocation.getName())) {
            newLocation.setLocationId(sysIdSequenceService.getNextSysIdSequence(
                    DateAndTimeUtils.getCurrentDate(), "L"));
            return locationRepository.save(newLocation);
        }
        //Otherwise throw an error.
        throw new CustomExceptionHandler.BadRequestCustomException("Location name already exists.");
    }

    public Location updateLocation(String name, String description, Integer locationId) {
        Location location = findLocationById(locationId);
        //If the name field is empty/null or the name already exists, throw an error.
        if(StringUtils.IsStringEmptyOrNull(name)) {
            throw new CustomExceptionHandler.BadRequestCustomException("Location name cannot be empty.");
        }
        location.setName(name);
        location.setDescription(description);
        return locationRepository.save(location);
    }

    public void deleteLocation(Integer locationId) {
        Location location = findLocationById(locationId);
        locationRepository.logicalDelete(location);
    }

    public Boolean checkIfLocationNameExists(String name) {
        return locationRepository.findAll().stream().anyMatch(
                existingLocation -> existingLocation.getName().equalsIgnoreCase(name));
    }
}