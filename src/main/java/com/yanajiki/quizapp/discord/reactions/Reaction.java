package com.yanajiki.quizapp.discord.reactions;

import discord4j.core.event.domain.interaction.ButtonInteractionEvent;
import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public interface Reaction {
    Mono<Void> execute(ButtonInteractionEvent event);
}