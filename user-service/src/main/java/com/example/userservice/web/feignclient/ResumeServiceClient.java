package com.example.userservice.web.feignclient;

import com.example.userservice.web.response.ResponseResume;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "resume-service") // api gateway 의 name 으로 요청한다.
public interface ResumeServiceClient {
    @GetMapping("/resume-service/{userId}/resumes")
    List<ResponseResume> getResume(@PathVariable String userId);
}
