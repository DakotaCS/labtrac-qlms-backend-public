package com.quantus.backend.models.system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@Getter
@Setter
@Entity
@Table(name = "sys_id_sequence")
public class SysIdSequence {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "entry_date")
    protected String currentDate;
    @Column(name = "seq_no")
    protected int sequenceNumber;
    @Column(name = "entity_key")
    protected String entityKey;
}
