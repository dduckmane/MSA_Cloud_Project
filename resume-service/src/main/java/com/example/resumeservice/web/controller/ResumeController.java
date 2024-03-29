package com.example.resumeservice.web.controller;

import com.example.resumeservice.domain.service.ResumeService;
import com.example.resumeservice.web.request.RequestResume;
import com.example.resumeservice.web.response.ResponseResume;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/resume-service")
@Slf4j
@RequiredArgsConstructor
public class ResumeController {
    private final Environment env;
    private final ResumeService resumeService;


    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Resume Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @PostMapping("/{userId}/resumes")
    public ResponseEntity createOrder(
            @PathVariable("userId") String userId
            , @RequestBody RequestResume requestResume)
    {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeService.writeResume(requestResume.toDto(userId)));
    }

    @GetMapping("/{userId}/resumes")
    public ResponseEntity<List<ResponseResume>> getOrder(@PathVariable("userId") String userId) {
        log.info("user-service 에서 resume-service 에 /{userId}/resumes 호출");
        List<ResponseResume> result = resumeService.getResumesByUserId(userId)
                .stream().map(ResponseResume::new)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}