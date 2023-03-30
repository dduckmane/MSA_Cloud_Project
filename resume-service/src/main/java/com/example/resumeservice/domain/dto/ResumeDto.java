package com.example.resumeservice.domain.dto;

import com.example.resumeservice.domain.entity.Resume;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResumeDto {
    private String companyId;
    private Integer age;
    private Integer birth;
    private Integer career;
    private String resumeId;
    private String userId;

    @Builder
    public ResumeDto(String companyId
            , Integer age
            , Integer birth
            , Integer career
            , String resumeId
            , String userId) {
        this.companyId = companyId;
        this.age = age;
        this.birth = birth;
        this.career = career;
        this.resumeId = resumeId;
        this.userId = userId;
    }

    public ResumeDto(Resume resume) {
        this.companyId = resume.getCompanyId();
        this.age = resume.getAge();
        this.birth = resume.getBirth();
        this.career = resume.getCareer();
        this.resumeId = resume.getResumeId();
        this.userId = resume.getUserId();
    }
}
