package com.example.userservice.web;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodName, Response response) {
        switch (response.status()) {
            case 400:
                break;

            case 404:
                if (methodName.contains("getResume")) // getResume 메서드에서 404 에러가 발생한다면
                    return new ResponseStatusException(HttpStatusCode.valueOf(response.status()),
                            // 해당하는 response.status() 로 값을 응답하고
                            "해당하는 userId 가 없습니다."); // 이 메시지를 응답한다.
                break;

            default:
                return new Exception(response.reason());
        }
        return null;
    }
}
