package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.GlobalVariable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-15
 */
public interface GlobalVariableRepository extends JpaRepository<GlobalVariable, Integer> {
    GlobalVariable findGlobalVariableByCodeEquals(String code);
}
