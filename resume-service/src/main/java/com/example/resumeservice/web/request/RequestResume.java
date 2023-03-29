package com.example.resumeservice.web.request;

import com.example.resumeservice.domain.dto.ResumeDto;
import lombok.Data;

@Data
public class RequestResume {
    private String categoryId;
    private Integer birth;
    private Integer career;

    public ResumeDto toDto(String userId) {
        return ResumeDto.builder()
                .companyId(categoryId)
                .birth(birth)
                .career(career)
                .userId(userId)
                .build();
    }
}
