package com.gpb.minibank.service.commandHandler.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {

    String getName();

    String exec(Update update);
}
