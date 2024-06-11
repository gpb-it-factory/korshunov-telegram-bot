package com.gpb.minibank.service.commandMaker.commands.clients.registerClient.registerClientImpl;

import com.gpb.minibank.service.commandMaker.commands.clients.registerClient.RegisterClient;
import com.gpb.minibank.service.commandMaker.commands.dto.request.CreateUserDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public final class RegisterClientHttp implements RegisterClient {

    private final RestClient restClient;
    private final String path;

    public RegisterClientHttp(@Value("${bot.service.register.path}") String path) {
//        this.restTemplate = restTemplateBuilder.build();
        this.path = path;
        this.restClient = RestClient.create();
    }

    public ResponseEntity<?> runRequest(CreateUserDTO createUserDTO) {
//        HttpEntity<CreateUserDTO> entity = new HttpEntity<>(new CreateUserDTO(id, userName));
//        return restTemplate.postForEntity(path, entity, String.class);
        return restClient.post()
                .uri(path)
                .body(createUserDTO)
                .retrieve()
                .toBodilessEntity();
    }
}