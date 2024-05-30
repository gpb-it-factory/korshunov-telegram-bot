package com.gpb.minibank.service;

import com.gpb.minibank.service.commandMaker.CommandMaker;
import com.gpb.minibank.service.messageCreater.MessageCreater;
import com.gpb.minibank.service.messageSender.MessageSender;
import lombok.extern.slf4j.Slf4j;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Service
public final class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private MessageSender messageSender;

    @Autowired
    private CommandMaker commandMaker;

    private final String botName;

    public TelegramBot(
            DefaultBotOptions options,
            String botName,
            String botToken) {
        super(options, botToken);
        this.botName = botName;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            log.info("! Получено сообщение ! ");
            var resultOfCommand = commandMaker.work(update);
            log.info("Создаю ответ.");
            var message = MessageCreater.createMessage(update.getMessage().getChatId(), resultOfCommand);
            log.info("Ответ создан!");
            try {
                log.info("Отправляю ответ.");
                messageSender.sendMessage(message);
                log.info("Ответ успешно отправлен!");
            } catch (TelegramApiException e) {
                log.error("Произошла ошибка во время отправки ответа '{}'!", e.getMessage());
            }
        }
    }
}
