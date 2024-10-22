package com.quantus.backend.utils;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-06-04
 */

public class StringUtils {

    /**
     * Checks if a String is empty and/or null. Returns true if so, false otherwise.
     */
    public static boolean IsStringEmptyOrNull(String str) {
        if (str == null || str.isEmpty()) { return true; } return false;
    }
}
