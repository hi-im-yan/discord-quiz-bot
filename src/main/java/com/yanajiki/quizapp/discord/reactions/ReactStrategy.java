package com.yanajiki.quizapp.discord.reactions;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Message;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
@AllArgsConstructor
public class ReactStrategy {

    private final Map<String, Reaction> reactionMap;

    public Mono<Void> execute(MappedReactions reaction, ButtonInteractionEvent event) {

        for (String key : reactionMap.keySet()) {
            if (key.startsWith(reaction.name())) {
                return reactionMap.get(key).execute(event);
            }
        }
        return Mono.empty();
    }

}
