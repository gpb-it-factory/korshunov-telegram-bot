package com.gpb.minibank.service.commandHandler.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class Start implements Command {

    private final String nameOfCommand;

    public Start() {
        this.nameOfCommand = "/start";
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    public String exec(Update update) {
        return "Привет, "
                + update.getMessage().getChat().getFirstName()
                + "!\nЯ telegram-бот от GPB мини-банка!"
                + "\n\nЯ умею выполнять команду /ping."
                + "\nНапиши (или нажми) /ping, и я отвечу тебе!";
    }
}
