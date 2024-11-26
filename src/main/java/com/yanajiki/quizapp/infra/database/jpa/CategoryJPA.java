package com.yanajiki.quizapp.infra.database.jpa;

import com.yanajiki.quizapp.infra.database.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJPA extends JpaRepository<CategoryEntity, Long> {

    CategoryEntity findByName(String name);
}
