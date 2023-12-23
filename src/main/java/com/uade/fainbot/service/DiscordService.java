package com.uade.fainbot.service;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DiscordService {
    private final Logger logger = LoggerFactory.getLogger(DiscordService.class);
    private GatewayDiscordClient client;

    public DiscordService() {
        logger.info("Logging in to Discord...");
        DiscordClient discordClient = DiscordClient.create(System.getenv("DISCORD_TOKEN"));
        discordClient.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(ReadyEvent.class, event ->
                        Mono.fromRunnable(() -> {
                            final User self = event.getSelf();
                            logger.info("Logged in as {}", self.getUsername());
                            client = gateway;
                        }))).subscribe();
    }

    public GatewayDiscordClient getClient() {
        return client;
    }
}
