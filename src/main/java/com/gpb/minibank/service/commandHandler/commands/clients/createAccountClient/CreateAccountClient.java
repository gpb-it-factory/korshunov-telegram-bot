package com.gpb.minibank.service.commandHandler.commands.clients.createAccountClient;

import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateAccountDTO;
import org.springframework.http.ResponseEntity;

public interface CreateAccountClient {
    ResponseEntity<?> runRequest(CreateAccountDTO createAccountDTO);
}
