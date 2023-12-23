package com.uade.fainbot.command;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import org.springframework.stereotype.Component;

@Component
public class HelpCommand extends Command {
    public HelpCommand() {
        this.commandName = "help";
        this.commandDescription = "Muestra la lista de comandos disponibles";
        this.isEphemeral = true;
    }

    @Override
    public InteractionApplicationCommandCallbackReplyMono execute(ChatInputInteractionEvent event) {
        return event.reply().withContent("""
                Lista de comandos disponibles:
                /ping: Prueba de conexión
                /help: Muestra la lista de comandos disponibles
                """
        ).withEphemeral(isEphemeral);
    }
}
