package com.example.userservice.web.response;

import lombok.Data;

import java.util.Date;

@Data
public class ResponseResume {
    private String companyId;
    private Integer birth;
    private Integer career;
    private String resumeId;
}
