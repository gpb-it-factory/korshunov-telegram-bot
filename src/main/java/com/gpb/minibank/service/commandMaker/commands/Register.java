package com.gpb.minibank.service.commandMaker.commands;

import com.gpb.minibank.service.commandMaker.dto.CreateUserDTO;
import com.gpb.minibank.service.commandMaker.paths.Paths;
import org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestTemplateBuilderConfigurer;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Register implements Command {

    private final RestTemplateAutoConfiguration restTemplateAutoConfiguration;

    public Register(RestTemplateAutoConfiguration restTemplate) {
        this.restTemplateAutoConfiguration = restTemplate;
    }

    public String exec(Update update) throws RestClientException {
        try {
            return runRequest(update);
        } catch (RestClientException error) {
            return "Что-то пошло не так!\nПопробуйте позже.";
        }
    }

    public String runRequest(Update update) {
        var id = update.getMessage().getChatId();
        HttpEntity<CreateUserDTO> entity = new HttpEntity<>(new CreateUserDTO(id));
        ResponseEntity<String> result = restTemplateAutoConfiguration
                .restTemplateBuilder(new RestTemplateBuilderConfigurer())
                .build().postForEntity(Paths.REGISTER, entity, String.class);
        return result.getBody();
    }
}
