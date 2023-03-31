package com.example.categoryservice.common;

import com.example.categoryservice.domain.entity.Category;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitData {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.createCategory();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager em;

        public void createCategory() {
            Category category1 = Category.builder()
                    .income(3400)
                    .companyName("회사1")
                    .companyId("C_01")
                    .numberWorkingDay(5)
                    .build();
            Category category2 = Category.builder()
                    .income(4400)
                    .companyName("회사2")
                    .companyId("C_02")
                    .numberWorkingDay(3)
                    .build();
            Category category3 = Category.builder()
                    .income(5400)
                    .companyName("회사3")
                    .companyId("C_03")
                    .numberWorkingDay(4)
                    .build();

            em.persist(category1);
            em.persist(category2);
            em.persist(category3);
        }
    }
}
