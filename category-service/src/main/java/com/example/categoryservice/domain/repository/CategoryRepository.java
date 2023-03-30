package com.example.categoryservice.domain.repository;


import com.example.categoryservice.domain.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    List<Category> findAll();
    Optional<Category> findByCompanyId(String companyId);
}
