package com.gpb.minibank.service.commandHandler.commands.clients.currentBalance;

import org.springframework.http.ResponseEntity;

public interface CurrentBalanceClient {
    ResponseEntity<?> runRequest(Long id);
}
