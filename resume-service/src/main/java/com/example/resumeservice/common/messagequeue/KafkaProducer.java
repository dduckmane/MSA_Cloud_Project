package com.example.resumeservice.common.messagequeue;

import com.example.resumeservice.domain.dto.ResumeDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class KafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ResumeDto send(String topic, ResumeDto resumeDto) {
        // 객체를 Json 타입으로 변경을 해서 전달을 해야한다.
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = objectMapper.writeValueAsString(resumeDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        kafkaTemplate.send(topic, jsonInString);
        log.info("Kafka 를 통한 데이터 전송" + resumeDto);

        return resumeDto;
    }
}
