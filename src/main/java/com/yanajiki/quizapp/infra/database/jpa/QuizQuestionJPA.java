package com.yanajiki.quizapp.infra.database.jpa;

import com.yanajiki.quizapp.infra.database.entity.QuizQuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizQuestionJPA extends JpaRepository<QuizQuestionEntity, QuizQuestionEntity.QuizQuestionId> {

    List<QuizQuestionEntity> findByIdQuizId(long quizId);
}
