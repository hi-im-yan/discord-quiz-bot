package com.yanajiki.quizapp.discord.services;

import com.yanajiki.quizapp.discord.EventListener;
import com.yanajiki.quizapp.discord.MessageListener;
import com.yanajiki.quizapp.discord.commands.CommandStrategy;
import discord4j.core.event.domain.message.MessageCreateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class MessageCreateListener extends MessageListener implements EventListener<MessageCreateEvent> {

    @Autowired
    private CommandStrategy commandStrategy;

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {

        return Mono.just(event.getMessage())
                .filter(message -> message.getAuthor().map(user -> !user.isBot()).orElse(false))
                .flatMap(message -> commandStrategy.execute(message.getContent(), message));

    }
}