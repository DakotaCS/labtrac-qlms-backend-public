package com.quantus.backend.services.system;

import com.quantus.backend.models.system.SysIdSequence;
import com.quantus.backend.repositories.system.SysIdSequenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@Service
@Transactional
@RequiredArgsConstructor
public class SysIdSequenceService {

    private final SysIdSequenceRepository sysIdSequenceRepository;

    public String getNextSysIdSequence(String currentDate, String entityKey) {
        SysIdSequence newSequence = sysIdSequenceRepository.findByCurrentDateAndEntityKey(currentDate, entityKey);
        int sequenceNumber;

        if (newSequence == null) {
            // No sequence for today, start with 1
            newSequence = new SysIdSequence();
            newSequence.setCurrentDate(currentDate);
            newSequence.setSequenceNumber(1);
            newSequence.setEntityKey(entityKey);
            sequenceNumber = 1;
        } else {
            // Increment the sequence number for the current date
            sequenceNumber = newSequence.getSequenceNumber() + 1;
            newSequence.setSequenceNumber(sequenceNumber);
        }

        sysIdSequenceRepository.save(newSequence);

        return String.format("%s-%s-%d",entityKey, currentDate, sequenceNumber);
    }
}
