package com.transaction.transaction.handler;


import com.transaction.transaction.entity.AuditLog;
import com.transaction.transaction.entity.Order;
import com.transaction.transaction.repository.AuditLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuditHandler {

    private final AuditLogRepo auditLogRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void auditOrderDetails(Order order,String action)
    {
        AuditLog auditLog = new AuditLog();

        auditLog.setOrderId(Long.valueOf(order.getId()));
        auditLog.setTimestamp(LocalDateTime.now());
        auditLog.setAction(action);

        auditLogRepo.save(auditLog);

    }
}
