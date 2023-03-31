package com.example.resumeservice.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;


import java.io.Serializable;
import java.util.Date;

@Getter
@Entity
@NoArgsConstructor

public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120, name = "company_id")
    private String companyId;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private Integer birth;
    @Column(nullable = false)
    private Integer career;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false, unique = true)
    private String resumeId;

    @Builder
    public Resume(String companyId, Integer age, Integer birth, Integer career, String userId, String resumeId) {
        this.companyId = companyId;
        this.age = age;
        this.birth = birth;
        this.career = career;
        this.userId = userId;
        this.resumeId = resumeId;
    }
}