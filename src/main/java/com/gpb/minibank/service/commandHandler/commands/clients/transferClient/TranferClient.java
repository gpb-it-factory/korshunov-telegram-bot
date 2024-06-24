package com.gpb.minibank.service.commandHandler.commands.clients.transferClient;

import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateTransferRequestDTO;
import org.springframework.http.ResponseEntity;

public interface TranferClient {
    ResponseEntity<?> runRequest(CreateTransferRequestDTO createTransferRequestDTO);
}
