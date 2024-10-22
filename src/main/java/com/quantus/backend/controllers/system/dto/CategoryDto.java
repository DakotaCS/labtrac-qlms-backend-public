package com.quantus.backend.controllers.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * @author Dakota Soares
 * @version 2024.1
 * @since 2024-09-27
 */

@Data
public class CategoryDto {
    protected Integer id;
    protected String categoryId;
    protected String name;
    protected String description;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    protected Date createTime;
}
