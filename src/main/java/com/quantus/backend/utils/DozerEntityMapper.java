package com.quantus.backend.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.dozer.DozerBeanMapper;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-05-09
 */

public class DozerEntityMapper {
    private static final DozerBeanMapper dozer = new DozerBeanMapper();

    private DozerEntityMapper() {}

    static {
        List<String> mappingConfig = Collections.singletonList("dozer-global-configuration.xml");
        dozer.setMappingFiles(mappingConfig);
    }

    public static <T> T mapObject(Object source, Class<T> destinationClass) {
        return dozer.map(source, destinationClass);
    }

    public static <T> List<T> mapObjectList(Collection<? extends Object> sourceList, Class<T> destinationClass) {
        List<T> destinationList = new ArrayList<>();
        for (Object sourceObject : sourceList) {
            T destinationObject = dozer.map(sourceObject, destinationClass);
            destinationList.add(destinationObject);
        }
        return destinationList;
    }

    public static void copyEntity(Object source, Object destination) {
        dozer.map(source, destination);
    }
}