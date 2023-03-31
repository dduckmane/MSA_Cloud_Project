package com.example.resumeservice.domain.service;

import com.example.resumeservice.common.messagequeue.KafkaProducer;
import com.example.resumeservice.common.messagequeue.ResumeProducer;
import com.example.resumeservice.domain.dto.ResumeDto;
import com.example.resumeservice.domain.entity.Resume;
import com.example.resumeservice.domain.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final KafkaProducer kafkaProducer;
    private final ResumeProducer resumeProducer;

    @Override
    public String writeResume(ResumeDto resumeDto) {
        resumeDto.setResumeId(UUID.randomUUID().toString());

        Resume resume = Resume.builder()
                .career(resumeDto.getCareer())
                .birth(resumeDto.getBirth())
                .age(resumeDto.getAge())
                .companyId(resumeDto.getCompanyId())
                .userId(resumeDto.getUserId())
                .resumeId(resumeDto.getResumeId())
                .build();

////        //jpa
////        Resume saveResume = resumeRepository.save(resume);

        //kafka
        kafkaProducer.send("category-topic", resumeDto);
        resumeProducer.send("resume",resumeDto);

        return resume.getResumeId();
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