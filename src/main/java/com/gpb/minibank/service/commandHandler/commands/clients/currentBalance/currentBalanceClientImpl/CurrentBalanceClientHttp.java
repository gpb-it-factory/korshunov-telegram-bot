package com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.currentBalanceClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.CurrentBalanceClient;
import com.gpb.minibank.service.commandHandler.commands.dto.response.AccountDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public final class CurrentBalanceClientHttp implements CurrentBalanceClient {

    private final RestClient restClient;

    private final String path;

    public CurrentBalanceClientHttp(@Value("${bot.service.current_balance.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    @Override
    public ResponseEntity<Result<AccountDTO, Error>> runRequest(Long id) {
        return restClient.get()
                .uri(path, id)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}
