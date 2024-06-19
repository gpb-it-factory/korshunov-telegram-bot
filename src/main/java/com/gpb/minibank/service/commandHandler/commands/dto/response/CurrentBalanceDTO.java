package com.gpb.minibank.service.commandHandler.commands.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class CurrentBalanceDTO {

    private String accountName;

    private BigDecimal amount;
}
