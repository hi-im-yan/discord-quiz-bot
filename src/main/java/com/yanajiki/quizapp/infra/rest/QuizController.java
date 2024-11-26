package com.yanajiki.quizapp.infra.rest;

import com.yanajiki.quizapp.infra.rest.request.QuizAnswerRequest;
import com.yanajiki.quizapp.infra.rest.request.QuizBuilderRequest;
import com.yanajiki.quizapp.kiss.AnswerQuestionUseCase;
import com.yanajiki.quizapp.kiss.BuildQuizUseCase;
import com.yanajiki.quizapp.kiss.dto.ChosenAlternativeResult;
import com.yanajiki.quizapp.kiss.dto.QuizInfoDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
@Slf4j
public class QuizController {

    private final BuildQuizUseCase buildQuizUseCase;
    private final AnswerQuestionUseCase answerQuestionUseCase;

    @PostMapping("/build")
    public QuizInfoDTO buildQuiz(@RequestBody QuizBuilderRequest request) {
        log.info("Received request to build quiz with params: '{}'", request);
        return buildQuizUseCase.execute(request.getNumberOfQuestions(), request.getCategory());
    }

    @PostMapping("/answer")
    public ChosenAlternativeResult answerQuestion(@RequestBody QuizAnswerRequest request) {
        log.info("Received request to answer question with params: '{}'", request);
        return answerQuestionUseCase.execute(request.getQuizId(), request.getQuestionId(), request.getChosenAlternative());
    }
}
