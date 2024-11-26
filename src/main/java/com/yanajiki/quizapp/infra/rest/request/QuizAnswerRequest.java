package com.yanajiki.quizapp.infra.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizAnswerRequest {

    private long quizId;
    private long questionId;
    private String chosenAlternative;
}
