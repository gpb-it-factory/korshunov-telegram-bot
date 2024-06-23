package com.gpb.minibank.service.commandHandler;

import com.gpb.minibank.service.commandHandler.commands.Command;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.List;

@Slf4j
@Component
public final class CommandHandler {

    private final List<Command> commandList;

    public CommandHandler(List<Command> commandList) {
        this.commandList = commandList;
    }

    public String work(Update update) {
        var commandFromUser = update.getMessage().getText().split(" ")[0];
        var command = commandList.stream()
                .filter(someCommand -> someCommand.getName().equals(commandFromUser))
                .findFirst();
        if (command.isPresent()) {
            log.info("Выполняю команду {}!", commandFromUser);
            return command.get().exec(update);
        }
        log.info("Получена неизвестная команда '{}'!", commandFromUser);
        return String.format("Я не умею выполнять команду '%s'!", commandFromUser);
    }
}
