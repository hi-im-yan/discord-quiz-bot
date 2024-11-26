package com.yanajiki.quizapp.kiss.dto;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class QuestionInfoWithoutAnswer {

    private Long id;
    private String statement;
    private String alternativeA;
    private String alternativeB;
    private String alternativeC;
    private String alternativeD;
    private String alternativeE;
    private QuestionCategory category;

    public static QuestionInfoWithoutAnswer fromEntity(QuestionEntity entity) {
        var dto = new QuestionInfoWithoutAnswer();
        dto.setId(entity.getId());
        dto.setStatement(entity.getStatement());
        dto.setAlternativeA(entity.getAlternativeA());
        dto.setAlternativeB(entity.getAlternativeB());
        dto.setAlternativeC(entity.getAlternativeC());
        dto.setAlternativeD(entity.getAlternativeD());
        dto.setAlternativeE(entity.getAlternativeE());
        dto.setCategory(QuestionCategory.valueOf(entity.getCategory().getName()));
        return dto;
    }
}
