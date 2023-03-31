package com.example.resumeservice.domain.service;


import com.example.resumeservice.domain.dto.ResumeDto;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    String writeResume(ResumeDto resumeDto);
    ResumeDto getResumeById(String resumeId);
    List<ResumeDto> getResumesByUserId(String userId);
}