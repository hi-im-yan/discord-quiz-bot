package com.yanajiki.quizapp.kiss;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import com.yanajiki.quizapp.infra.database.entity.QuizQuestionEntity;
import com.yanajiki.quizapp.infra.database.jpa.QuestionJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizQuestionJPA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class GetNextQuestionUseCase {

    private final QuizQuestionJPA quizQuestionRepository;
    private final QuestionJPA questionRepository;

    public QuestionEntity execute(Long quizId) {
        log.info("Getting next question for quizId: '{}'", quizId);
        List<QuizQuestionEntity> byQuizId = quizQuestionRepository.findByIdQuizId(quizId);

        QuizQuestionEntity nextQuestion = byQuizId.stream()
                .filter(question -> question.getSelectedAlternative() == null)
                .findFirst()
                .orElse(null);

        if (nextQuestion != null) {
            log.info("Next question found: '{}'", nextQuestion);
            return questionRepository.findById(nextQuestion.getId().getQuestionId()).orElse(null);
        }

        log.info("No more questions available for quizId: {}", quizId);

        return null;
    }
}
