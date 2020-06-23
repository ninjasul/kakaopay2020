package com.kakaopay.assignment.repository;

import com.kakaopay.assignment.entity.PaymentHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentHistoryRepository {
    PaymentHistory findByMgmtNo(String mgmtNo);
    long cancel(String mgmtNo, String cancelAmount, String vat);
    void save(PaymentHistory paymentHistory);
}