package com.example.resumeservice.common.messagequeue.dto;

import lombok.Builder;
import lombok.Data;

@Data
public class Payload {
    // 실제 db 컬럼과 일치해야한다.
    private int age;
    private int birth;
    private int career;
    private String company_id;
    private String resume_id;
    private String user_id;

    @Builder
    public Payload(int age
            , int birth
            , int career
            , String company_id
            , String resume_id
            , String user_id
    ) {
        this.age = age;
        this.birth = birth;
        this.career = career;
        this.company_id = company_id;
        this.resume_id = resume_id;
        this.user_id = user_id;
    }
}
