package com.kakaopay.assignment.repository;

import com.kakaopay.assignment.protocol.field.PaymentStatus;
import com.kakaopay.assignment.entity.PaymentHistory;
import com.kakaopay.assignment.entity.QPaymentHistory;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class PaymentHistoryRepositoryImpl extends QuerydslRepositorySupport implements PaymentHistoryRepository {

    private static final QPaymentHistory $ = QPaymentHistory.paymentHistory;

    @Autowired
    private EntityManager em;

    @Autowired
    private JPAQueryFactory queryFactory;

    public PaymentHistoryRepositoryImpl(EntityManager em, JPAQueryFactory queryFactory) {
        super(PaymentHistory.class);
        this.em = em;
        this.queryFactory = queryFactory;
    }

    @Override
    public PaymentHistory findByMgmtNo(String mgmtNo) {
        return queryFactory.selectFrom($)
            .where(QPaymentHistory.paymentHistory.mgmtNo.eq(mgmtNo))
            .fetchOne();
    }

    @Override
    public PaymentHistory findCancellableByMgmtNo(String mgmtNo) {
        return queryFactory.selectFrom($)
            .where(QPaymentHistory.paymentHistory.mgmtNo.eq(mgmtNo)
                       .and(
                           QPaymentHistory.paymentHistory.status.eq(PaymentStatus.PAID)
                               .or(QPaymentHistory.paymentHistory.status.eq(PaymentStatus.PARTIALLY_CANCELLED)
                               )
                       )
            )
            .fetchOne();
    }

    @Override
    public void save(PaymentHistory paymentHistory) {
        em.persist(paymentHistory);
    }
}
