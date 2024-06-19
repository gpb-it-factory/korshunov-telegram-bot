package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.CurrentBalanceClient;
import com.gpb.minibank.service.commandHandler.commands.dto.response.CurrentBalanceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

public class CurrentBalance implements Command {

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
            return getMessage(response);
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public String getMessage(ResponseEntity<?> response) {
        if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
            var result = (CurrentBalanceDTO) response.getBody();
            return "Название счёта: " + result.getAccountName()
            + "\nБаланс счёта: " + result.getAmount();
        }
        return "Упс!\nЧто-то пошло не так!";
    }
}
