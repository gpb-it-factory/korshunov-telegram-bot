package com.gpb.minibank.service.commandMaker;

import com.gpb.minibank.service.commandMaker.commands.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@Slf4j
@Component
public final class CommandMaker {

    private final List<Command> commandList;

    public CommandMaker(List<Command> commandList) {
        this.commandList = commandList;
    }

    public String work(Update update) {
        var commandFromUser = update.getMessage().getText();
        var command = commandList.stream()
                .filter(someCommand -> someCommand.getNameOfCommand().equals(commandFromUser))
                .findFirst();
        if (command.isPresent()) {
            log.info("Выполняю команду {}!", commandFromUser);
            return command.get().exec(update);
        }
        log.info("Получена неизвестная команда '{}'!", commandFromUser);
        return String.format("Я не умею выполнять команду '%s'!", commandFromUser);
    }
}
