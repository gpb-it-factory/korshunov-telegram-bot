package com.gpb.minibank.service.commandHandler.commands.dto.request;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CreateAccountDTO {

    private Long userId;

    private String accountName;

}
