package com.quantus.backend.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-24
 */

@Data
@MappedSuperclass
public abstract class GeneralModel implements Serializable {

    @Basic
    @Column(name = "is_deleted")
    protected Boolean isDeleted = false;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    protected Timestamp createTime;
}
