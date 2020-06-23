package com.kakaopay.assignment.controller;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;
import com.kakaopay.assignment.service.PaymentHistoryService;
import com.kakaopay.assignment.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Slf4j
public class PaymentController {
    @Autowired
    private PaymentHistoryService service;

    @PostMapping(
        value = "/pay",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    public PayResponseDto pay(@Validated @RequestBody PayRequestDto request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logBindingResultError(bindingResult);
            throw new IllegalArgumentException();
        }

        log.debug("request: {}", StringUtils.toPrettyJson(request));

        PayResponseDto payResponseDto = service.insert(request);
        log.debug("payResponseDto: {}", StringUtils.toPrettyJson(payResponseDto));

        return payResponseDto;
    }

    @PostMapping(
        value = "/cancel",
        consumes = APPLICATION_JSON_VALUE,
        produces = APPLICATION_JSON_VALUE
    )
    public PayResponseDto cancel(@Validated @RequestBody CancelRequestDto request, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            logBindingResultError(bindingResult);
            throw new IllegalArgumentException();
        }

        log.debug("request: {}", StringUtils.toPrettyJson(request));

        PayResponseDto payResponseDto = service.cancel(request);
        log.debug("payResponseDto: {}", StringUtils.toPrettyJson(payResponseDto));

        return payResponseDto;
    }

    @GetMapping(
        value = "/find/{mgmtNo}",
        produces = APPLICATION_JSON_VALUE
    )
    public FoundPaymentDto findByMgmtNo(@PathVariable String mgmtNo) {
        if (StringUtils.isEmpty(mgmtNo)) {
            throw new IllegalArgumentException();
        }

        log.debug("mgmtNo: {}", mgmtNo);

        FoundPaymentDto foundPaymentDto = service.findByMgmtNo(mgmtNo);
        log.debug("foundPaymentDto: {}", StringUtils.toPrettyJson(foundPaymentDto));

        return foundPaymentDto;
    }

    private void logBindingResultError(BindingResult bindingResult) {
        bindingResult.getAllErrors()
            .stream()
            .forEach(error -> log.error("error: {}", error));
    }
}
