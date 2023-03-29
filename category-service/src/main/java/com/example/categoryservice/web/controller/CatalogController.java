package com.example.categoryservice.web.controller;

import com.example.categoryservice.domain.service.CategoryService;
import com.example.categoryservice.web.response.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category-service")
@RequiredArgsConstructor
public class CatalogController {
    private final Environment env;
    private final CategoryService catalogService;

    @GetMapping("/health_check")
    public String status() {
        return String.format("카테고리 서버 포트는 %s 입니다.",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<CategoryResponse>> getCatalogs() {
        List<CategoryResponse> result =
                catalogService.getAllCatalogs()
                        .stream().map(CategoryResponse::new)
                        .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}