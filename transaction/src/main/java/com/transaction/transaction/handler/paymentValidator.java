package com.transaction.transaction.handler;

import com.transaction.transaction.entity.AuditLog;
import com.transaction.transaction.entity.Order;
import com.transaction.transaction.repository.AuditLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class paymentValidator {

    @Autowired
    private AuditLogRepo auditLogRepo;

    @Transactional(propagation = Propagation.MANDATORY)
    public void validatePayment(Order order) {
        // Assume payment processing happens here
        boolean paymentSuccessful = false;

        // If payment is unsuccessful, we log the payment failure in the mandatory transaction
        if (!paymentSuccessful) {
            AuditLog paymentFailureLog = new AuditLog();
            paymentFailureLog.setOrderId(Long.valueOf(order.getId()));
            paymentFailureLog.setAction("Payment Failed for Order");
            paymentFailureLog.setTimestamp(LocalDateTime.now());

            if(order.getTotalPrice()>1000){
                throw new RuntimeException("Error in payment validator");
            }
            // Save the payment failure log
            auditLogRepo.save(paymentFailureLog);
        }

    }
}
