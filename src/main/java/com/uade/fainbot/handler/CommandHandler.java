package com.uade.fainbot.handler;

import com.uade.fainbot.command.Command;
import com.uade.fainbot.service.DiscordService;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.discordjson.json.ApplicationCommandRequest;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CommandHandler {
    @Value("${spring.profiles.active}")
    private String profile;

    private final Logger logger = LoggerFactory.getLogger(CommandHandler.class);
    private final DiscordService discordService;
    private final List<Command> commands;

    public CommandHandler(DiscordService discordService, List<Command> commands) {
        this.discordService = discordService;
        this.commands = commands;
    }

    @PostConstruct
    private void registerCommands() {
        GatewayDiscordClient client;
        while((client = discordService.getClient()) == null) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("Error while waiting for Discord client to be ready", e);
            }
        }
        Long appId = client.getRestClient().getApplicationId().block();
        List<ApplicationCommandRequest> commandRequest = commands.stream().map(Command::buildCommand).toList();

        logger.info("Registering commands...");

        if (profile.equals("dev")) {
            client.getRestClient().getApplicationService()
                    .bulkOverwriteGuildApplicationCommand(appId, 1187897999309942894L, commandRequest)
                    .subscribe();
        } else {
            client.getRestClient().getApplicationService()
                    .bulkOverwriteGlobalApplicationCommand(appId, commandRequest)
                    .subscribe();
        }

        logger.info("Commands registered!");

        client.on(ChatInputInteractionEvent.class, event -> commands.stream()
            .filter(c -> event.getCommandName().equals(c.getCommandName()))
            .findFirst().get().execute(event)).blockLast();

    }
}
