package com.uade.fainbot.service;

import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.lifecycle.ReadyEvent;
import discord4j.core.object.entity.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class DiscordService {
    private final Logger logger = LoggerFactory.getLogger(DiscordService.class);

    @Value("${fainbot.token}")
    private String token;
    private GatewayDiscordClient client;
    @PostConstruct
    public void login() {
        logger.info("Logging in to Discord...");
        DiscordClient discordClient = DiscordClient.create(token);
        discordClient.withGateway((GatewayDiscordClient gateway) ->
                gateway.on(ReadyEvent.class, event ->
                        Mono.fromRunnable(() -> {
                            final User self = event.getSelf();
                            logger.info("Logged in as {}", self.getUsername());
                            client = gateway;
                        }))).block();
    }

    public GatewayDiscordClient getClient() {
        return client;
    }
}
