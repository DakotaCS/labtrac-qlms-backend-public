package com.quantus.backend.models.system;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-10-15
 */

@Getter
@Setter
@Entity
@Table(name = "sys_global_variable")
public class GlobalVariable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    protected Integer id;

    @Column(name = "code")
    protected String code;

    @Column(name = "value")
    protected String value;
}
