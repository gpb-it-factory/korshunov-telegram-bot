package com.gpb.minibank.service.commandHandler.commands.dto.response;

import java.util.Optional;

public class Result <T, E> {

    private final T response;

    private final E error;

    public Result(T response, E error) {
        this.response = response;
        this.error = error;
    }

    public boolean isResponse() {
        return response != null;
    }

    public boolean isError() {
        return error != null;
    }

    public Optional<T> getResponse() {
        return Optional.ofNullable(response);
    }

    public Optional<E> getError() {
        return Optional.ofNullable(error);
    }

    public static <T, E> Result<T, E> response(T response) {
        return new Result<>(response, null);
    }

    public static <T, E> Result<T, E> error(E error) {
        return new Result<>(null, error);
    }
}
