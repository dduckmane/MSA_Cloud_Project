package com.example.resumeservice.common.messagequeue.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class KafkaResumeDto implements Serializable {
    private Schema schema;
    private Payload  payload;

}
