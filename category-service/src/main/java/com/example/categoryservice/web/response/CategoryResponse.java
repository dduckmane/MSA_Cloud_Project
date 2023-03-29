package com.example.categoryservice.web.response;


import com.example.categoryservice.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryResponse {
    private String companyId;
    private String companyName;
    private Integer Income;
    private Integer Day;
    private Date createdAt;

    public CategoryResponse(Category category) {
        this.companyId = category.getCompanyId();
        this.companyName = category.getCompanyName();
        Income = category.getIncome();
        Day = category.getNumberWorkingDay();
        this.createdAt = category.getCreatedAt();
    }
}
