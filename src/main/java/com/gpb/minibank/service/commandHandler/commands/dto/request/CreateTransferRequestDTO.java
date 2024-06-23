package com.gpb.minibank.service.commandHandler.commands.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateTransferRequestDTO {

    @NotBlank
    private String from;

    @NotBlank
    private String to;

    @NotBlank
    private String amount;
}
