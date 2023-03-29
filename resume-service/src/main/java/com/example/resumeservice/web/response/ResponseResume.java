package com.example.resumeservice.web.response;

import com.example.resumeservice.domain.dto.ResumeDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseResume {
    private String companyId;
    private Integer birth;
    private Integer career;
    private String resumeId;

    public ResponseResume(ResumeDto resumeDto) {
        this.companyId = resumeDto.getCompanyId();
        this.birth = resumeDto.getBirth();
        this.career = resumeDto.getCareer();
        this.resumeId = resumeDto.getResumeId();
    }
}
