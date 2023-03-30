package com.example.categoryservice.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, unique = true)
    private String companyId;
    @Column(nullable = false)
    private String companyName;
    @Column(nullable = false)
    private Integer Income;
    @Column(nullable = false)
    private Integer NumberWorkingDay;

    @Builder
    public Category(String companyId, String companyName, Integer income, Integer numberWorkingDay) {
        this.companyId = companyId;
        this.companyName = companyName;
        Income = income;
        NumberWorkingDay = numberWorkingDay;
    }
}
