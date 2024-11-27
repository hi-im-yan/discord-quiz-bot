package com.yanajiki.quizapp.discord.reactions;

import com.yanajiki.quizapp.infra.database.entity.QuestionEntity;
import com.yanajiki.quizapp.infra.database.jpa.QuizJPA;
import com.yanajiki.quizapp.infra.database.jpa.QuizQuestionJPA;
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
import discord4j.core.spec.EmbedCreateSpec;
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
    private final QuizJPA quizRepository;

    @Override
    public Mono<Void> execute(ButtonInteractionEvent event) {
        var buttonReaction = event.getInteraction().getData().data().get().customId().get();
        var splitButtonId = buttonReaction.split("\\|");
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

        QuestionEntity question = getNextQuestionUseCase.execute(quizId);

        if (question == null) {
            var quiz = quizRepository.findById(quizId).get();
            return event.getInteraction().getChannel()
                    .flatMap(channel -> channel.createMessage("""
                            Quiz is over thanks for playing. \nYou got %d correct answers from %d questions!"""
                            .formatted(quiz.getRightAnswersCount(), quiz.getRightAnswersCount() + quiz.getWrongAnswersCount())))
                    .then();
        }
        return event.getInteraction().getChannel()
                .flatMap(channel -> channel.createMessage(buildDiscordQuestionMessage(question, quizId)))
                .then();
    }

    private MessageCreateSpec buildDiscordQuestionMessage(QuestionEntity question, long quizId) {
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

        EmbedCreateSpec.builder()
                .image("https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.reddit.com%2Fr%2FSemContexto%2Fcomments%2F1f92xu2%2Fimagem_aleat%25C3%25B3ria_numa_hora_aleat%25C3%25B3ria%2F&psig=AOvVaw1iNeTkuQui0bewqH6StkfA&ust=1732837737835000&source=images&cd=vfe&opi=89978449&ved=0CBEQjRxqFwoTCNiFi-DZ_YkDFQAAAAAdAAAAABAE")
                .build();
        return MessageCreateSpec.builder()

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
                .build();
    }

}