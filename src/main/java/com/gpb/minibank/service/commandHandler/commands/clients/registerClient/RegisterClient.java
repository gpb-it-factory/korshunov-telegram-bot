package com.gpb.minibank.service.commandHandler.commands.clients.registerClient;

import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateUserDTO;
import org.springframework.http.ResponseEntity;

public interface RegisterClient {
    ResponseEntity<?> runRequest(CreateUserDTO createUserDTO);
}
