package com.yanajiki.quizapp.discord.commands;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface Command {
    Mono<Void> execute(Message message);
}