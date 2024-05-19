package com.gpb.minibank.service.commandMaker;


import com.gpb.minibank.service.commandMaker.commands.Command;
import com.gpb.minibank.service.commandMaker.commands.Ponk;
import com.gpb.minibank.service.commandMaker.commands.Start;
import com.gpb.minibank.service.commandMaker.commands.WrongCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public class CommandMaker {

    public static String work(Update update) {
        var commandString = update.getMessage().getText();
        boolean wrong = false;
        Command command = switch (commandString) {
            case "/start":
                yield new Start(update);
            case "/pink":
                yield new Ponk();
            default:
                wrong = true;
                yield new WrongCommand(update);
        };

        if (wrong) {
            log.info("Команда '{}' не найдена! Выполняется команда 'WrongCommand'.", commandString);
            var result = command.exec();
            log.info("Команда 'WrongCommand' выполнена!");
            return result;
        }
        log.info("Выполняется команда '{}'!", commandString);
        var result = command.exec();
        log.info("Команда '{}' выполнена!", commandString);
        return result;
    }
}
