package com.gpb.minibank.service.commandMaker;


import com.gpb.minibank.service.commandMaker.commands.Command;
import com.gpb.minibank.service.commandMaker.commands.Ponk;
import com.gpb.minibank.service.commandMaker.commands.Start;
import com.gpb.minibank.service.commandMaker.commands.WrongCommand;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CommandMaker {

    public static String work(Update update) {
        var commandString = update.getMessage().getText();
        Command command = switch (commandString) {
            case "/start" -> new Start(update);
            case "/pink" -> new Ponk();
            default -> new WrongCommand(update);
        };
        return command.exec();
    }
}
