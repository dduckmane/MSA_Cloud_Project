package com.example.resumeservice.domain.repository;

import com.example.resumeservice.domain.entity.Resume;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeRepository extends CrudRepository<Resume, Long> {
    Optional<Resume> findByResumeId(String orderId);
    List<Resume> findByUserId(String userId);

    List<Resume> findAll();
}
