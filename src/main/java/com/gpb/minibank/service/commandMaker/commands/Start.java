package com.gpb.minibank.service.commandMaker.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Start implements Command {

    public String exec(Update update) {
        return "Привет, "
                + update.getMessage().getChat().getFirstName()
                + "!\nЯ telegram-бот от GPB мини-банка!"
                + "\n\nЯ умею выполнять команду /ping."
                + "\nНапиши (или нажми) /ping, и я отвечу тебе!";
    }
}
