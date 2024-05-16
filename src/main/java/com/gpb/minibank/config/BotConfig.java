package com.gpb.minibank.config;

import com.gpb.minibank.service.TelegramBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Configuration
public class BotConfig {

    @Bean
    public TelegramBot getTelegramBot(
            @Value("${bot.name}") String name,
            @Value("${bot.token}") String token
    ) throws TelegramApiException {
        var botOptions = new DefaultBotOptions();
        var bot = new TelegramBot(botOptions, name, token);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return bot;
    }
}
