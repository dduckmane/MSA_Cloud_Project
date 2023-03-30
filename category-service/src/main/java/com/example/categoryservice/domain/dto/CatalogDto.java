package com.example.categoryservice.domain.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serializable;

@Data
public class CatalogDto {
    private String companyId;
    private String companyName;
    private Integer Income;
    private Integer NumberWorkingDay;

}
