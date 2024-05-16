package com.gpb.minibank.service.commandMaker.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public class Start implements Command {

    private Update update;

    public Start(Update update) {
        this.update = update;
    }

    @Override
    public String exec() {
        return "Привет, "
                + update.getMessage().getChat().getFirstName()
                + "!\nЯ telegram-бот от GPB мини-банка!"
                + "\n\nЯ умею выполнять команду /pink.\nНапиши (или нажми) /pink, и я отвечу тебе!";
    }
}
