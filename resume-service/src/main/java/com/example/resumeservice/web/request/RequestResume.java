package com.example.resumeservice.web.request;

import com.example.resumeservice.domain.dto.ResumeDto;
import lombok.Data;

@Data
public class RequestResume {
    private String companyId;
    private Integer age;
    private Integer birth;
    private Integer career;

    public ResumeDto toDto(String userId) {
        return ResumeDto.builder()
                .companyId(companyId)
                .birth(birth)
                .age(age)
                .career(career)
                .userId(userId)
                .build();
    }
}
