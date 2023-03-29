package com.example.resumeservice.domain.service;

import com.example.resumeservice.domain.dto.ResumeDto;
import com.example.resumeservice.domain.entity.Resume;
import com.example.resumeservice.domain.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;

    @Override
    public Long writeResume(ResumeDto resumeDto) {
        resumeDto.setResumeId(UUID.randomUUID().toString());

        Resume resume = Resume.builder()
                .career(resumeDto.getCareer())
                .age(resumeDto.getBirth())
                .companyId(resumeDto.getCompanyId())
                .userId(resumeDto.getUserId())
                .resumeId(resumeDto.getResumeId())
                .build();

        Resume saveResume = resumeRepository.save(resume);

        return saveResume.getId();
    }

    @Override
    public ResumeDto getResumeById(String resumeId) {
        return resumeRepository.findByResumeId(resumeId)
                .map(ResumeDto::new)
                .orElseThrow();
    }

    @Override
    public List<ResumeDto> getResumesByUserId(String userId) {
        return resumeRepository
                .findAll().stream().map(ResumeDto::new)
                .collect(Collectors.toList());
    }
}