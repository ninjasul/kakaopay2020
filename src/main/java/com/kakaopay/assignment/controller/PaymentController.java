package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.PayFailResponseDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class PaymentController {

    @RequestMapping(
        value = "/pay",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    public String pay(@Validated @RequestBody PayRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logBindingResultError(bindingResult);
            throw new IllegalArgumentException();
        }

        log.debug("request: {}", StringUtils.toPrettyJson(request));

        PayResponseDto payResponse = new PayResponseDto();
        return StringUtils.toJson(payResponse);
    }

    private void logBindingResultError(BindingResult bindingResult) {
        bindingResult.getAllErrors()
            .stream()
            .forEach(error -> log.error("error: {}", error));
    }
}
