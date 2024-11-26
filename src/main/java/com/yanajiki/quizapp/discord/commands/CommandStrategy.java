package com.yanajiki.quizapp.discord.commands;

import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@AllArgsConstructor
public class CommandStrategy {

    private final Map<String, Command> commands;

    public Mono<Void> execute(String command, Message message) {
        if (!command.startsWith("!")) {
            return Mono.empty();
        }

        for (String key : commands.keySet()) {
            if (command.contains(key)) {
                return commands.get(key).execute(message);
            }
        }

        return showInvalidCommand(message);
    }

    private Mono<Void> showInvalidCommand(Message message) {
        return Mono.just(message)
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Unknown command"))
                .then();
    }
}
