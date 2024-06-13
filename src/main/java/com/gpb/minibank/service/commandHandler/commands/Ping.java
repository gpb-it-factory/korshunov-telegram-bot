package com.gpb.minibank.service.commandHandler.commands;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public final class Ping implements Command {

    private final String nameOfCommand;

    public Ping() {
        this.nameOfCommand = "/ping";
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    public String exec(Update update) {
        return "pong";
    }
}
