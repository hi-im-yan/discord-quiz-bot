package com.yanajiki.quizapp.kiss;


import com.yanajiki.quizapp.infra.database.entity.QuizEntity;
import com.yanajiki.quizapp.infra.database.entity.QuizQuestionEntity;
import com.yanajiki.quizapp.infra.database.jpa.CategoryJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuestionJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizQuestionJPA;
import com.yanajiki.quizapp.kiss.dto.QuestionCategory;
import com.yanajiki.quizapp.kiss.dto.QuestionInfoWithoutAnswer;
import com.yanajiki.quizapp.kiss.dto.QuizInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class BuildQuizUseCase {

    private final QuizJPA quizRepository;
    private final QuestionJPA questionRepository;
    private final CategoryJPA categoryRepository;
    private final QuizQuestionJPA quizQuestionRepository;
    private static final int DEFAULT_NUMBER_OF_QUESTIONS = 5;

    public QuizInfoDTO execute(int wantedNumberOfQuestions, QuestionCategory category) {
        if (wantedNumberOfQuestions <= 0) {
            wantedNumberOfQuestions = DEFAULT_NUMBER_OF_QUESTIONS;
        }

        var categoryEntity = categoryRepository.findByName(category.name());

        log.info("Building quiz with '{}' questions from category '{}'", wantedNumberOfQuestions, category);
        var fetchedQuestions = questionRepository.getRandomQuestionsFromCategory(wantedNumberOfQuestions, categoryEntity.getId());
        var newQuiz = new QuizEntity();
        var savedQuiz = quizRepository.save(newQuiz);

        // Populating intermediary table
        fetchedQuestions.forEach(question -> {
            var embeddedId = new QuizQuestionEntity.QuizQuestionId();
            embeddedId.setQuizId(savedQuiz.getId());
            embeddedId.setQuestionId(question.getId());

            var quizQuestion = new QuizQuestionEntity();
            quizQuestion.setId(embeddedId);
            quizQuestionRepository.save(quizQuestion);
        });

        var quizInfoDTO = new QuizInfoDTO(savedQuiz.getId(),
                fetchedQuestions.stream()
                        .map(QuestionInfoWithoutAnswer::fromEntity)
                        .collect(Collectors.toList()));

        log.info("Quiz built: '{}'", savedQuiz);

        return quizInfoDTO;
    }

}
