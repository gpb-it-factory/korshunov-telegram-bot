package com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.createAccountClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.CreateAccountClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateAccountDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Component
public final class CreateAccountClientHttp implements CreateAccountClient {

    private String path;

    private RestClient restClient;

    public CreateAccountClientHttp(@Value("${bot.service.create_account.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    public ResponseEntity<?> runRequest(CreateAccountDTO createAccountDTO) {
        try {
            return restClient.post()
                    .uri(path, createAccountDTO.getUserId())
                    .body(createAccountDTO)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpStatusCodeException error) {
            return ResponseEntity.status(error.getStatusCode()).build();
        }
    }
}
