package com.gpb.minibank.service.commandHandler.commands.clients.transferClient.transferClientImpl;

import com.gpb.minibank.service.commandHandler.commands.clients.transferClient.TranferClient;
import com.gpb.minibank.service.commandHandler.commands.dto.request.CreateTransferRequestDTO;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import com.gpb.minibank.service.commandHandler.commands.dto.response.TransferResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Slf4j
@Component
public final class TransferClientHttp implements TranferClient {

    private final String path;

    private final RestClient restClient;

    public TransferClientHttp(@Value("${bot.service.transfer.path}") String path) {
        this.path = path;
        this.restClient = RestClient.create();
    }

    @Override
    public ResponseEntity<Result<TransferResponseDTO, Error>> runRequest(CreateTransferRequestDTO createTransferRequestDTO) {
        log.info("Отправляю запрос на перевод денежных средств");
        return restClient.post()
                .uri(path)
                .body(createTransferRequestDTO)
                .retrieve()
                .toEntity(new ParameterizedTypeReference<>() {});
    }
}
