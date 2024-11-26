package com.yanajiki.quizapp.kiss;


import com.yanajiki.quizapp.infra.database.entity.QuizQuestionEntity;
import com.yanajiki.quizapp.infra.database.jpa.QuestionJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizQuestionJPA;
import com.yanajiki.quizapp.kiss.dto.ChosenAlternativeResult;
import com.yanajiki.quizapp.kiss.exception.AlreadyAnsweredException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AnswerQuestionUseCase {
    private final QuizJPA quizRepository;
    private final QuizQuestionJPA quizQuestionRepository;
    private final QuestionJPA questionRepository;

    public ChosenAlternativeResult execute(long quizId, long questionId, String chosenAlternative) {



        var quiz = quizRepository.findById(quizId).orElseThrow();
        var question = questionRepository.findById(questionId).orElseThrow();

        var optQuizQuestion = quizQuestionRepository.findById(new QuizQuestionEntity.QuizQuestionId(quizId, questionId));
        if (optQuizQuestion.isEmpty()) {
            throw new IllegalArgumentException("Question not found in quiz");
        }

        var quizQuestionEntity = optQuizQuestion.get();
        if (quizQuestionEntity.getSelectedAlternative() != null) {
            throw new AlreadyAnsweredException("Question already answered");
        }

        if (question.getCorrectAlternative().equals(chosenAlternative)) {
            log.info("Correct answer chosen");
            quizQuestionEntity.setSelectedAlternative(chosenAlternative);
            quiz.setRightAnswersCount(quiz.getRightAnswersCount() + 1);
            quizRepository.save(quiz);
            quizQuestionRepository.save(quizQuestionEntity);
            return new ChosenAlternativeResult(true);
        } else {
            log.info("Wrong answer chosen");
            quizQuestionEntity.setSelectedAlternative(chosenAlternative);
            quiz.setWrongAnswersCount(quiz.getWrongAnswersCount() + 1);
            quizRepository.save(quiz);
            quizQuestionRepository.save(quizQuestionEntity);
            return new ChosenAlternativeResult(false);
        }

    }
}
