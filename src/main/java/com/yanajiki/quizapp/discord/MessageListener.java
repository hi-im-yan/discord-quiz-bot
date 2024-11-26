package com.yanajiki.quizapp.discord;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    private String author = "Unknown";

    public Mono<Void> processMessage(final Message eventMessage) {

        return Mono.just(eventMessage)
                .filter(message -> {
                            final Boolean isNotBot = message.getAuthor()
                                    .map(user -> !user.isBot())
                                    .orElse(false);
                            if (isNotBot) {
                                message.getAuthor().ifPresent(user -> author = user.getUsername());
                            }

                            return isNotBot;
                        }
                )
                .flatMap(message -> {
                    final String content = message.getContent();
                    return message.getChannel();
                })
                .flatMap(channel -> channel.createMessage("Hello " + author + "!"))
                .then();
    }
}
