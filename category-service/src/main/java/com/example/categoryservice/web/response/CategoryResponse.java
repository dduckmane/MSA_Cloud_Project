package com.example.categoryservice.web.response;


import com.example.categoryservice.domain.entity.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class CategoryResponse {
    private String companyId;
    private String companyName;
    private Integer income;
    private Integer NumberWorkingDay;

    public CategoryResponse(Category category) {
        this.companyId = category.getCompanyId();
        this.companyName = category.getCompanyName();
        this.income = category.getIncome();
        this.NumberWorkingDay = category.getNumberWorkingDay();
    }
}
