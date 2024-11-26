package com.yanajiki.quizapp.infra.database.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name = "Question")
@Getter
@Setter
@ToString
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String statement;

    @Column(nullable = false, name = "alternative_a")
    private String alternativeA;

    @Column(nullable = false, name = "alternative_b")
    private String alternativeB;

    @Column(nullable = false, name = "alternative_c")
    private String alternativeC;

    @Column(nullable = false, name = "alternative_d")
    private String alternativeD;

    @Column(nullable = false, name = "alternative_e")
    private String alternativeE;

    @Column(nullable = false, length = 1, name = "correct_alternative")
    private String correctAlternative;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

}
