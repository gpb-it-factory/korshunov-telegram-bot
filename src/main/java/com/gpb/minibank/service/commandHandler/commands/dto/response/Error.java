package com.gpb.minibank.service.commandHandler.commands.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Error {

    private String message;

    private String type;

    private String code;

    private String trace_id;
}
