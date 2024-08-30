package com.microservices.update_subscriber.repository;

import com.microservices.update_subscriber.entity.EmailPending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailPendingRepository extends JpaRepository<EmailPending,Long> {
}
