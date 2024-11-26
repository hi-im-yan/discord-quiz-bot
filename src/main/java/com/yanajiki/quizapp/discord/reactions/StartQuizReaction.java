package com.yanajiki.quizapp.discord.reactions;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import com.yanajiki.quizapp.kiss.GetNextQuestionUseCase;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.component.SelectMenu;
import discord4j.core.object.entity.channel.MessageChannel;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import static java.lang.Long.parseLong;

@AllArgsConstructor
@Component("START_QUIZ")
public class StartQuizReaction implements Reaction {

    private final GetNextQuestionUseCase getNextQuestionUseCase;

    @Override
    public Mono<Void> execute(ButtonInteractionEvent event) {
        event.acknowledge().block();

        var buttonReaction = event.getInteraction().getData().data().get().customId().get();
        var splitButtonId = buttonReaction.split("\\|");
        var quizId = parseLong(splitButtonId[1].split(":")[1]);

        QuestionEntity question = getNextQuestionUseCase.execute(quizId);

        ReactionEmoji optionEmojiA = ReactionEmoji.unicode("\uD83C\uDDE6");
        ReactionEmoji optionEmojiB = ReactionEmoji.unicode("\uD83C\uDDE7");
        ReactionEmoji optionEmojiC = ReactionEmoji.unicode("\uD83C\uDDE8");
        ReactionEmoji optionEmojiD = ReactionEmoji.unicode("\uD83C\uDDE9");
        ReactionEmoji optionEmojiE = ReactionEmoji.unicode("\uD83C\uDDEA");
        String buttonA = String.format("%s|QUIZ_ID:%s|QUESTION_ID:%s|SELECTED_OPTION:%s", MappedReactions.CHOOSE_ANSWER.name(), quizId, question.getId(), "A");
        Button customEmoteButtonA = Button.primary(buttonA, optionEmojiA);

        String buttonB = String.format("%s|QUIZ_ID:%s|QUESTION_ID:%s|SELECTED_OPTION:%s", MappedReactions.CHOOSE_ANSWER.name(), quizId, question.getId(), "B");
        Button customEmoteButtonB = Button.primary(buttonB, optionEmojiB);

        String buttonC = String.format("%s|QUIZ_ID:%s|QUESTION_ID:%s|SELECTED_OPTION:%s", MappedReactions.CHOOSE_ANSWER.name(), quizId, question.getId(), "C");
        Button customEmoteButtonC = Button.primary(buttonC, optionEmojiC);

        String buttonD = String.format("%s|QUIZ_ID:%s|QUESTION_ID:%s|SELECTED_OPTION:%s", MappedReactions.CHOOSE_ANSWER.name(), quizId, question.getId(), "D");
        Button customEmoteButtonD = Button.primary(buttonD, optionEmojiD);

        String buttonE = String.format("%s|QUIZ_ID:%s|QUESTION_ID:%s|SELECTED_OPTION:%s", MappedReactions.CHOOSE_ANSWER.name(), quizId, question.getId(), "E");
        Button customEmoteButtonE = Button.primary(buttonE, optionEmojiE);

        return event.getInteraction().getChannel()
                .flatMap(channel -> channel.createMessage(MessageCreateSpec.builder()
                        // Buttons must be in action rows
                        .content(String.format(
                                """
                                        Question: %s
                                        A: %s
                                        B: %s
                                        C: %s
                                        D: %s
                                        E: %s""",
                                question.getStatement(),
                                question.getAlternativeA(),
                                question.getAlternativeB(),
                                question.getAlternativeC(),
                                question.getAlternativeD(),
                                question.getAlternativeE()
                        ))
                        .addComponent(ActionRow.of(customEmoteButtonA, customEmoteButtonB, customEmoteButtonC, customEmoteButtonD, customEmoteButtonE))
                        .build()))
                .then();
    }
}