package com.yanajiki.quizapp.infra.rest.request;

import com.yanajiki.quizapp.kiss.dto.QuestionCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class QuizBuilderRequest {

    private Integer numberOfQuestions;
    private QuestionCategory category;

}
