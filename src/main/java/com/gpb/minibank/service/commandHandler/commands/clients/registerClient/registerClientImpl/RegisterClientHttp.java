package com.gpb.minibank.service.commandHandler.commands.clients.registerClient.registerClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.registerClient.RegisterClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public final class RegisterClientHttp implements RegisterClient {

    private final RestClient restClient;
    private final String path;

    public RegisterClientHttp(@Value("${bot.service.register.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    public ResponseEntity<?> runRequest(CreateUserDTO createUserDTO) {
        log.info("Отправляю запрос на регистрацию пользователя с id: {}", createUserDTO.getUserId());
        try {
            return restClient.post()
                    .uri(path)
                    .body(createUserDTO)
                    .retrieve()
                    .toBodilessEntity();
        } catch (HttpStatusCodeException error) {
            return ResponseEntity.status(error.getStatusCode()).build();
        }
    }
}
