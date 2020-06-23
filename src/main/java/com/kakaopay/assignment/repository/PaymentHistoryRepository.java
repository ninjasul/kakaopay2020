package com.kakaopay.assignment.repository;

import com.kakaopay.assignment.entity.PaymentHistory;

public interface PaymentHistoryRepository {
    PaymentHistory findByMgmtNo(String mgmtNo);

    PaymentHistory findCancellableByMgmtNo(String mgmtNo);

    void save(PaymentHistory paymentHistory);
}