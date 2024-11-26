package com.yanajiki.quizapp.discord.services;

import com.yanajiki.quizapp.discord.EventListener;
import com.yanajiki.quizapp.discord.MessageListener;
import com.yanajiki.quizapp.discord.reactions.MappedReactions;
import com.yanajiki.quizapp.discord.reactions.ReactStrategy;
import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.lang.Long.parseLong;

@Service
@Slf4j
public class MessageReactedListener extends MessageListener implements EventListener<ButtonInteractionEvent> {

    @Autowired
    private ReactStrategy reactStrategy;

    @Override
    public Class<ButtonInteractionEvent> getEventType() {
        return ButtonInteractionEvent.class;
    }

    @Override
    public Mono<Void> execute(ButtonInteractionEvent event) {
        return Mono.just(event)
                .filter(e -> e.getMessage().isPresent())
                .filter(e -> !e.getInteraction().getData().data().isAbsent())
//                .filter(message -> message.map(msg -> !msg.getAuthor().map(user -> user.isBot()).orElse(false)).orElse(false))
                .flatMap(e -> {
                    log.info("Received reaction to: '{}'", e.getMessage().get().getContent());
                    var buttonReaction = event.getInteraction().getData().data().get().customId().get();
                    var splitButtonId = buttonReaction.split("\\|");
                    var reaction = splitButtonId[0];
                    return reactStrategy.execute(MappedReactions.valueOf(reaction), e);
                });

    }
}