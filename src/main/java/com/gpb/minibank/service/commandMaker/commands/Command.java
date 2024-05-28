package com.gpb.minibank.service.commandMaker.commands;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String exec(Update update);
}
