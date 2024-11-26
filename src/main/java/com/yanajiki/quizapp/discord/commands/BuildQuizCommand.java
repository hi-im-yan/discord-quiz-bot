package com.yanajiki.quizapp.discord.commands;

import com.yanajiki.quizapp.discord.reactions.MappedReactions;
import com.yanajiki.quizapp.kiss.BuildQuizUseCase;
import com.yanajiki.quizapp.kiss.dto.QuestionCategory;
import com.yanajiki.quizapp.kiss.dto.QuizInfoDTO;
import discord4j.core.object.component.ActionRow;
import discord4j.core.object.component.Button;
import discord4j.core.object.entity.Message;
import discord4j.core.object.reaction.ReactionEmoji;
import discord4j.core.spec.MessageCreateSpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component("!build_quiz")
@AllArgsConstructor
@Slf4j
public class BuildQuizCommand implements Command {

    private final BuildQuizUseCase buildQuizUseCase;

    @Override
    public Mono<Void> execute(Message message) {
        // Custom non-animated emote

        return Mono.just(message)
                .flatMap(msg -> {
                    final String content = msg.getContent();
                    final String[] quizParameters = content.split(" ");
                    if (quizParameters.length < 2) {
                        return msg.getChannel()
                                .flatMap(channel -> channel.createMessage("Invalid command. Type !build_quiz 5 MATH"));
                    }
                    int numberOfQuestions;
                    String possibleCategory;
                    try {
                        numberOfQuestions = Integer.parseInt(quizParameters[1]);
                        possibleCategory = quizParameters[2];

                        for (QuestionCategory category : QuestionCategory.values()) {
                            if (category.name().equals(possibleCategory)) {
                                QuizInfoDTO info = buildQuizUseCase.execute(numberOfQuestions, category);
                                ReactionEmoji customEmoji = ReactionEmoji.unicode("✅");
                                String buttonId = String.format("%s|ID:%s", MappedReactions.START_QUIZ.name(), info.getId());
                                Button customEmoteButton = Button.primary(buttonId, customEmoji, "Start Quiz");
                                Mono<Void> then = msg.getChannel()
                                        .flatMap(channel -> channel.createMessage(MessageCreateSpec.builder()
                                                // Buttons must be in action rows
                                                .content("Quiz built with " + numberOfQuestions +
                                                        " questions from category " + category.name() + ".")
                                                .addComponent(ActionRow.of(customEmoteButton))
                                                .build()))
                                        .then();

                                return then.doOnError(subscription -> log.info("Quiz built with " + numberOfQuestions +
                                        " questions from category " + category.name() + "."));
                            }
                        }

                        throw new RuntimeException("Invalid Question Category");
                    } catch (Exception e) {
                        return msg.getChannel()
                                .flatMap(channel -> channel.createMessage("Invalid command. Use !build_quiz <number_of_questions> <questions_category (MATH)>"));
                    }


                }).then();
    }
}
