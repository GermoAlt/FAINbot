package com.uade.fainbot.command;

import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.spec.InteractionApplicationCommandCallbackReplyMono;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
    protected String commandName;
    protected String commandDescription;
    List<ApplicationCommandOptionData> options = new ArrayList<>();

    protected boolean isEphemeral = true;

    public ApplicationCommandRequest buildCommand() {
        return ApplicationCommandRequest.builder()
                .name(this.commandName)
                .description(this.commandDescription)
                .addAllOptions(this.options)
                .build();


    }

    public InteractionApplicationCommandCallbackReplyMono execute(ChatInputInteractionEvent event) {
        return null;
    }

    public String getCommandName() {
        return commandName;
    }

}
