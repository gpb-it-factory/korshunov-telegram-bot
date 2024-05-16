package com.gpb.minibank.service;

import com.gpb.minibank.service.commandMaker.CommandMaker;
import com.gpb.minibank.service.messageCreater.MessageCreater;
import com.gpb.minibank.service.messageSender.MessageSender;
import org.jvnet.hk2.annotations.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Service
public class TelegramBot extends TelegramLongPollingBot {

    private final String botName;

    private final String botToken;

    public TelegramBot(
            DefaultBotOptions options,
            String botName,
            String botToken) {
        super(options, botToken);
        this.botName = botName;
        this.botToken = botToken;
    }

    @Override
    public String getBotUsername() {
        return this.botName;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            // выполняем и получаем результат выбранной пользователем команды
            var resultOfCommand = CommandMaker.work(update);
            // создаём ответ
            var message = MessageCreater.createMessage(update.getMessage().getChatId(), resultOfCommand);
            try {
                // отправляем ответ пользователю
                new MessageSender(this.botToken, message).sendMessage();
            } catch (TelegramApiException e) {
                System.out.print(e.getMessage());
            }
        }
    }
}