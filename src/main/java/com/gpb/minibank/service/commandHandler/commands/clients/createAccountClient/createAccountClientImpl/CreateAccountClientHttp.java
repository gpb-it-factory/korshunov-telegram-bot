package com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.createAccountClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient.CreateAccountClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateAccountDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public final class CreateAccountClientHttp implements CreateAccountClient {

    private final String path;

    private final RestClient restClient;

    public CreateAccountClientHttp(@Value("${bot.service.create_account.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    public ResponseEntity<?> runRequest(Long id, CreateAccountDTO createAccountDTO) {
        log.info("Отправляю запрос на создание счёта для пользователя с id: {}", id);
        try {
            return restClient.post()
                    .uri(path, id)
                    .body(createAccountDTO)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpStatusCodeException error) {
            return ResponseEntity.status(error.getStatusCode()).body(error.getMessage());
        }
    }
}
