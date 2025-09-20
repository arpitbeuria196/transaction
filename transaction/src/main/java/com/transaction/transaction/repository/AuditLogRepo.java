package com.transaction.transaction.repository;

import com.transaction.transaction.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AuditLogRepo extends JpaRepository<AuditLog,Long> {
}
