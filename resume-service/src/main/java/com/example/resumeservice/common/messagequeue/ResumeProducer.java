package com.example.resumeservice.common.messagequeue;

import com.example.resumeservice.common.messagequeue.dto.Field;
import com.example.resumeservice.common.messagequeue.dto.KafkaResumeDto;
import com.example.resumeservice.common.messagequeue.dto.Payload;
import com.example.resumeservice.common.messagequeue.dto.Schema;
import com.example.resumeservice.domain.dto.ResumeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ResumeProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private List<Field> fields= Arrays.asList(
            new Field("int32", true, "age")
            ,new Field("int32", true, "birth")
            ,new Field("int32", true, "career")
            ,new Field("string", true, "company_id")
            ,new Field("string", true, "resume_id")
            ,new Field("string", true, "user_id")
    );

    private Schema schema= Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("resume") // 데이터 베이스 이름
            .build();

    public ResumeDto send(String topic, ResumeDto resumeDto) {
        Payload payload = Payload.builder()
                .age(resumeDto.getAge())
                .career(resumeDto.getCareer())
                .birth(resumeDto.getBirth())
                .resume_id(resumeDto.getResumeId())
                .company_id(resumeDto.getCompanyId())
                .user_id(resumeDto.getUserId())
                .build();

        KafkaResumeDto kafkaResumeDto = new KafkaResumeDto(schema, payload);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(kafkaResumeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka 를 통한 데이터 전송V2" + kafkaResumeDto);

        return resumeDto;
    }
}
