package com.kakaopay.assignment.service;

import com.kakaopay.assignment.controller.dto.CancelRequestDto;
import com.kakaopay.assignment.controller.dto.FoundPaymentDto;
import com.kakaopay.assignment.controller.dto.PayRequestDto;
import com.kakaopay.assignment.controller.dto.PayResponseDto;

public interface PaymentHistoryService {

    FoundPaymentDto findByMgmtNo(String mgmtNo);

    PayResponseDto cancel(CancelRequestDto cancelRequestDto) throws Exception;

    PayResponseDto insert(PayRequestDto paymentRequestDto);
}