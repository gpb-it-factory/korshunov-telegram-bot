package com.gpb.minibank.service.commandHandler.commands.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CreateUserDTO {

    @NotNull
    @Positive
    private Long userId;

    @NotBlank
    private String userName;

}
