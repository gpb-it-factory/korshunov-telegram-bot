package com.gpb.minibank.service.commandHandler.commands;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.CurrentBalanceClient;
import com.gpb.minibank.service.commandHandler.commands.dto.response.AccountDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

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

//    @Override
//    public String exec(Update update) {
//        try {
//            var response = currentBalanceClient.runRequest(update.getMessage().getChatId());
//            return getMessage(response);
//        } catch (RestClientException error) {
//            return "Сервис не доступен!";
//        }
//    }
//
//    public String getMessage(ResponseEntity<?> response) {
//        if (response.getStatusCode().isSameCodeAs(HttpStatus.OK)) {
//            var result = (CurrentBalanceDTO) response.getBody();
//            return "Название счёта: " + result.getAccountName()
//            + "\nБаланс счёта: " + result.getAmount();
//        }
//        return "Упс!\nЧто-то пошло не так!";
//    }

    @Override
    public String exec(Update update) {
        try {
            var response = currentBalanceClient.runRequest(update.getMessage().getChatId());
            return getMessage((ResponseEntity<Result<AccountDTO, Error>>) response);
        } catch (HttpStatusCodeException error) {
            return "Ошибка!\nЧто-то пошло не так!";
        } catch (RestClientException error) {
            return "Сервис не доступен!";
        }
    }

    public String getMessage(ResponseEntity<Result<AccountDTO, Error>> response) {
        var result = response.getBody().getResponse();
        if (result.isPresent()) {
            return "Название счёта: " + result.get().getAccountName()
            + "\nБаланс счёта: " + result.get().getAmount();
        }
        return response.getBody().getError().get().getMessage();
    }
}
