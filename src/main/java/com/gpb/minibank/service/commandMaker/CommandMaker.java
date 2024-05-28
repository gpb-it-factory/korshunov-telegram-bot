package com.gpb.minibank.service.commandMaker;

import com.gpb.minibank.service.commandMaker.commands.Ping;
import com.gpb.minibank.service.commandMaker.commands.Register;
import com.gpb.minibank.service.commandMaker.commands.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class CommandMaker {

    @Autowired
    private Start start;

    @Autowired
    private Ping ping;

    @Autowired
    private Register register;

    public String work(Update update) {
        var commandString = update.getMessage().getText();
        return switch (commandString) {
            case "/start":
                writeLog(commandString);
                yield start.exec(update);
            case "/ping":
                writeLog(commandString);
                yield ping.exec();
            case "/register":
                writeLog(commandString);
                yield register.exec(update);
            default:
                log.info("Получена неизвестная команда '{}'!", commandString);
                yield String.format("Я не умею выполнять команду '%s'!", commandString);
        };
    }

    public void writeLog(String commandString) {
        log.info("Выполняю команду {}!", commandString);
    }
}
