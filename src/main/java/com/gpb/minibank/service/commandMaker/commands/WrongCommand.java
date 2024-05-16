package com.gpb.minibank.service.commandMaker.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public class WrongCommand implements Command {

    private Update update;

    public WrongCommand(Update update) {
        this.update = update;
    }

    @Override
    public String exec() {
        return "Command " + update.getMessage().getText() + " not found!";
    }
}
