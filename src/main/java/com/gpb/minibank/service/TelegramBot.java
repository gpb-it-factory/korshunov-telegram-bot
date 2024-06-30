package com.gpb.minibank.service;

import com.gpb.minibank.service.commandHandler.CommandHandler;
import com.gpb.minibank.service.messageCreater.MessageCreater;
import com.gpb.minibank.service.messageSender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public final class TelegramBot extends TelegramLongPollingBot {

    private final MessageSender messageSender;

    private final CommandHandler commandHandler;

    private final String botName;

    public TelegramBot(
            DefaultBotOptions options,
            String botName,
            String botToken,
            CommandHandler commandHandler,
            MessageSender messageSender) {
        super(options, botToken);
        this.botName = botName;
        this.commandHandler = commandHandler;
        this.messageSender = messageSender;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("-- Получено сообщение от пользователя --");
            var resultOfCommand = commandHandler.work(update);
            log.info("-- Создаю ответ для пользователя --");
            var message = MessageCreater.createMessage(update.getMessage().getChatId(), resultOfCommand);
            log.info("-- Ответ для пользователя создан --");
            try {
                log.info(" -- Отправляю ответ пользователю --");
                messageSender.sendMessage(message);
                log.info("-- Ответ успешно отправлен пользователю --");
            } catch (TelegramApiException e) {
                log.error("-- Произошла ошибка во время отправки ответа пользователю '{}' --", e.getMessage());
            }
        }
    }
}
