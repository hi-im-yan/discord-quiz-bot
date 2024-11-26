package com.yanajiki.quizapp.kiss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class QuizInfoDTO {
    long id;
    List<QuestionInfoWithoutAnswer> questions;
}
