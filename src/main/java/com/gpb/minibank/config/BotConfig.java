package com.gpb.minibank.config;

import com.gpb.minibank.service.TelegramBot;
import com.gpb.minibank.service.commandMaker.CommandMaker;
import com.gpb.minibank.service.messageSender.MessageSender;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Configuration
public class BotConfig {

    private final CommandMaker commandMaker;

    private final MessageSender messageSender;

    public BotConfig(CommandMaker commandMaker, MessageSender messageSender) {
        this.commandMaker = commandMaker;
        this.messageSender = messageSender;
    }

    @Bean
    public TelegramBot getTelegramBot(
            @Value("${bot.name}") String name,
            @Value("${bot.token}") String token,
            CommandMaker commandMaker,
            MessageSender messageSender
    ) throws TelegramApiException {
        var botOptions = new DefaultBotOptions();
        var bot = new TelegramBot(botOptions, name, token, commandMaker, messageSender);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return bot;
    }
}
