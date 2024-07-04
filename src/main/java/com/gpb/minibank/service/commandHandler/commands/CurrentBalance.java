package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.CurrentBalanceClient;
import com.gpb.minibank.service.commandHandler.commands.dto.response.AccountDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
public final class CurrentBalance implements Command {

    private final String nameOfCommand;

    private final CurrentBalanceClient currentBalanceClient;

    public CurrentBalance(CurrentBalanceClient currentBalanceClient) {
        this.nameOfCommand = "/currentbalance";
        this.currentBalanceClient = currentBalanceClient;
    }

    @Override
    public String getName() {
        return this.nameOfCommand;
    }

    @Override
    public String exec(Update update) {
        try {
            var response = currentBalanceClient.runRequest(update.getMessage().getChatId());
            log.info("-- Ответ получен --");
            return getMessage((ResponseEntity<Result<AccountDTO, Error>>) response);
        } catch (HttpStatusCodeException error) {
            log.info("-- Произошла ошибка: {} --", error.getMessage());
            return "Ошибка!\nЧто-то пошло не так!";
        } catch (RestClientException error) {
            log.info("-- Сервис не доступен --");
            return "Сервис не доступен!";
        }
    }

    public String getMessage(ResponseEntity<Result<AccountDTO, Error>> response) {
        var result = response.getBody().getResponse();
        if (result.isPresent()) {
            log.info("-- Счета пользователя получены --");
            return "Название счёта: " + result.get().getAccountName()
            + "\nБаланс счёта: " + result.get().getAmount() + " руб.";
        }
        log.info("-- Произошла ошибка при запросе на получение счетов пользователя --");
        return response.getBody().getError().get().getMessage();
    }
}
