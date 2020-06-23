package com.kakaopay.assignment.repository;

import com.kakaopay.assignment.domain.field.PaymentStatus;
import com.kakaopay.assignment.entity.PaymentHistory;
import com.kakaopay.assignment.entity.QPaymentHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;

@Repository
public class PaymentHistoryRepositoryImpl extends QuerydslRepositorySupport implements PaymentHistoryRepository {

    @Autowired
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory;

    private static final QPaymentHistory $ = QPaymentHistory.paymentHistory;

    public PaymentHistoryRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
        super(PaymentHistory.class);
        this.em = em;
        this.queryFactory = queryFactory;
    }

    @Override
    public PaymentHistory findByMgmtNo(String mgmtNo) {
        return queryFactory.selectFrom($)
            .where(QPaymentHistory.paymentHistory.mgmtNo.eq(mgmtNo))
            .fetchFirst();
    }

    @Override
    public long cancel(String mgmtNo, String cancelAmount, String vat) {
        return queryFactory.update($)
            .where($.mgmtNo.eq(mgmtNo))
            .set($.status, PaymentStatus.COMPLETELY_CANCELLED)
            .set($.updatedAt, LocalDateTime.now())
            .execute();
    }

    @Override
    public void save(PaymentHistory paymentHistory) {
        em.persist(paymentHistory);
    }
}
