package com.yanajiki.quizapp.infra.database.jpa;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface QuestionJPA extends JpaRepository<QuestionEntity, Long> {

    @Query(value = "SELECT * FROM question " +
            "WHERE category_id = :categoryId " +
            "ORDER BY RANDOM() " +
            "LIMIT :wantedNumberOfQuestions", nativeQuery = true)
    List<QuestionEntity> getRandomQuestionsFromCategory(@Param("wantedNumberOfQuestions") int wantedNumberOfQuestions,
                                                        @Param("categoryId") long categoryId);
}
