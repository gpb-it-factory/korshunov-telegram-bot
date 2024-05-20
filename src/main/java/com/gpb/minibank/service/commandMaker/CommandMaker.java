package com.gpb.minibank.service.commandMaker;

import com.gpb.minibank.service.commandMaker.commands.Ping;
import com.gpb.minibank.service.commandMaker.commands.Start;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class CommandMaker {

    public static String work(Update update) {
        var commandString = update.getMessage().getText();
        return switch (commandString) {
            case "/start":
                log.info("Выполняю команду {}!", commandString);
                yield new Start(update).exec();
            case "/ping":
                log.info("Выполняю команду {}!", commandString);
                yield new Ping().exec();
            default:
                log.info("Получена неизвестная команда '{}'!", commandString);
                yield String.format("Я не умею выполнять команду '%s'!", commandString);
        };
    }
}
