package com.gpb.minibank.service.commandHandler.commands.dto;

import com.gpb.minibank.service.commandHandler.commands.dto.response.Error;
import com.gpb.minibank.service.commandHandler.commands.dto.response.Result;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultTest {

    @Test
    void testResult_ErrorEntityInsideTrue() {
        var result = Result.error(new Error("message", "type", "code", "trace_id"));

        var errorEntityCheck = result.isError();
        var responseEntityCheck = result.isResponse();

        Assertions.assertTrue(errorEntityCheck);
        Assertions.assertFalse(responseEntityCheck);
    }

    @Test
    void testResult_ResponseEntityInsideTrue() {
        var result = Result.response("response");

        var errorEntityCheck = result.isError();
        var responseEntityCheck = result.isResponse();

        Assertions.assertTrue(responseEntityCheck);
        Assertions.assertFalse(errorEntityCheck);
    }

    @Test
    void testResult_getErrorMethodTrue_getResponseMethodFalse() {
        var result = Result.error(new Error("message", "type", "code", "trace_id"));

        var optionalWithErrorForCheck = result.getError();
        var optionalWithResponseForCheck = result.getResponse();

        Assertions.assertTrue(optionalWithErrorForCheck.isPresent());
        Assertions.assertTrue(optionalWithResponseForCheck.isEmpty());
    }

    @Test
    void testResult_getErrorMethodFalse_getResponseMethodTrue() {
        var result = Result.response("response");

        var optionalWithErrorForCheck = result.getError();
        var optionalWithResponseForCheck = result.getResponse();

        Assertions.assertTrue(optionalWithErrorForCheck.isEmpty());
        Assertions.assertTrue(optionalWithResponseForCheck.isPresent());
    }

    @Test
    void testResult_errorMethodTrue() {
        var result = Result.error(new Error("message", "type", "code", "trace_id"));

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isError());
        Assertions.assertTrue(result.getResponse().isEmpty());
    }

    @Test
    void testResult_responseMethodTrue() {
        var result = Result.response("response");

        Assertions.assertNotNull(result);
        Assertions.assertTrue(result.isResponse());
        Assertions.assertTrue(result.getError().isEmpty());
    }
}
