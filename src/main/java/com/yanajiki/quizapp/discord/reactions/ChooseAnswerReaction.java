package com.yanajiki.quizapp.discord.reactions;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import com.yanajiki.quizapp.kiss.AnswerQuestionUseCase;
import com.yanajiki.quizapp.kiss.GetNextQuestionUseCase;
import com.yanajiki.quizapp.kiss.dto.ChosenAlternativeResult;
import com.yanajiki.quizapp.kiss.exception.AlreadyAnsweredException;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.SelectMenu;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import discord4j.rest.http.client.ClientException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component("CHOOSE_ANSWER")
public class ChooseAnswerReaction implements Reaction {

    private final GetNextQuestionUseCase getNextQuestionUseCase;
    private final AnswerQuestionUseCase answerQuestionUseCase;

    @Override
    public Mono<Void> execute(ButtonInteractionEvent event) {
        var buttonReaction = event.getInteraction().getData().data().get().customId().get();
        var splitButtonId = buttonReaction.split("\\|");
        var reaction = splitButtonId[0];
        var quizId = Long.parseLong(splitButtonId[1].split(":")[1]);
        var questionId = Long.parseLong(splitButtonId[2].split(":")[1]);
        var selectedOption = splitButtonId[3].split(":")[1];

        System.out.println("ChooseAnswerReaction quizId: " + quizId);
        System.out.println("ChooseAnswerReaction questionId: " + questionId);
        System.out.println("ChooseAnswerReaction selectedOption: " + selectedOption);

        try {
            ChosenAlternativeResult result = answerQuestionUseCase.execute(quizId, questionId, selectedOption);
            String s = result.isCorrect() ? "correct" : "incorrect";
            event.reply().withEphemeral(true).withContent("Your Answer was " + s).block();
        } catch (AlreadyAnsweredException e) {
            event.reply().withEphemeral(true).withContent("You have already answered this question").block();
        }

        return Mono.empty();

    }
}
