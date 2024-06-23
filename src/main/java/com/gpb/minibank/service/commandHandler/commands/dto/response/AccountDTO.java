package com.gpb.minibank.service.commandHandler.commands.dto.response;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class AccountDTO {

    private String accountId;

    private String accountName;

    private String amount;
}
