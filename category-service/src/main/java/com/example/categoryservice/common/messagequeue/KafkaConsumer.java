package com.example.categoryservice.common.messagequeue;

import com.example.categoryservice.common.exception.CategoryNotFoundException;
import com.example.categoryservice.domain.entity.Category;
import com.example.categoryservice.domain.repository.CategoryRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class KafkaConsumer {
    private final CategoryRepository categoryRepository;

    /**
     * category-topic 에 데이터가 쌓이면
     * 그 정보를 가져온다.
     * 대신 선행적으로 listener 가 bean 으로 등록이 되어있어야한다.
     * */
    @KafkaListener(topics = "category-topic")
    public void updateVolunteerCnt(String kafkaMessage) {
        log.info("kafka message = "+ kafkaMessage);

        Map<Object, Object> map = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            map = objectMapper.readValue(kafkaMessage, new TypeReference<Map<Object, Object>>() {});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Category findCategory = categoryRepository
                .findByCompanyId((String) map.get("companyId"))
                .orElseThrow(() -> new CategoryNotFoundException("일치하는 카테고리가 없습니다."));

        findCategory.plusVolunteerCnt();
    }

}
