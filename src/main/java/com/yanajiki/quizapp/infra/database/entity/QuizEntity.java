package com.yanajiki.quizapp.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "quiz")
@Setter
@Getter
@ToString
public class QuizEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "right_answers_count")
    private int rightAnswersCount;
    @Column(name = "wrong_answers_count")
    private int wrongAnswersCount;

    public QuizEntity() {
        this.rightAnswersCount = 0;
        this.wrongAnswersCount = 0;
    }

}