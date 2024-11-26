package com.yanajiki.quizapp.infra.database.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "Quiz_Question")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class QuizQuestionEntity {



    @EmbeddedId
    private QuizQuestionId id;

    @Column(name = "selected_alternative")
    private String selectedAlternative;

    @Embeddable
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class QuizQuestionId implements Serializable {
        @Column(name = "quiz_id")
        private Long quizId;

        @Column(name = "question_id")
        private Long questionId;

        // Getters, setters, equals, and hashCode methods
    }

    // Getters and setters for selectedAlternative
}
