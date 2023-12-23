package com.uade.fainbot.command;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import org.springframework.stereotype.Component;

@Component
public class PingCommand extends Command {

    public PingCommand() {
        this.commandName = "ping";
        this.commandDescription = "Prueba de conexión";
        this.isEphemeral = true;
    }

    @Override
    public InteractionApplicationCommandCallbackReplyMono execute(ChatInputInteractionEvent event) {
       return event.reply("Pong!").withEphemeral(isEphemeral);
    }
}
