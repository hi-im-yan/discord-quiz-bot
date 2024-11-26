package com.yanajiki.quizapp.kiss.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ChosenAlternativeResult {
    private final boolean isCorrect;
}
