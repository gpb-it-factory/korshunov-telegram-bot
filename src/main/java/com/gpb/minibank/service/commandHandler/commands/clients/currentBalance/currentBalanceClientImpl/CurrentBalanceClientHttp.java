package com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.currentBalanceClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.currentBalance.CurrentBalanceClient;
import com.gpb.minibank.service.commandHandler.commands.dto.response.CurrentBalanceDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

public class CurrentBalanceClientHttp implements CurrentBalanceClient {

    private final RestClient restClient;

    private final String path;

    public CurrentBalanceClientHttp(@Value("${bot.service.current_balance.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    @Override
    public ResponseEntity<?> runRequest(Long id) {
        try {
            return restClient.get()
                    .uri(path, id)
                    .retrieve()
                    .toEntity(CurrentBalanceDTO.class);
        } catch (HttpClientErrorException error) {
            return ResponseEntity.status(error.getStatusCode()).build();
        }
    }
}
