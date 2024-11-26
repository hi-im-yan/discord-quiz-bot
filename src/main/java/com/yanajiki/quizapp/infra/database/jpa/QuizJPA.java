package com.yanajiki.quizapp.infra.database.jpa;

import com.yanajiki.quizapp.infra.database.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizJPA extends JpaRepository<QuizEntity, Long> {
}
