package com.quantus.backend.repositories.system;

import com.quantus.backend.models.system.SysIdSequence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */
public interface SysIdSequenceRepository extends JpaRepository<SysIdSequence, Integer> {
    SysIdSequence findByCurrentDateAndEntityKey(String currentDate, String key);
}
