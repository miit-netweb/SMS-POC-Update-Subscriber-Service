package com.microservices.update_subscriber.service;

import com.microservices.update_subscriber.entity.EmailPending;
import com.microservices.update_subscriber.repository.EmailPendingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private EmailPendingRepository emailPendingRepository;

    public EmailPending addPendingEntry(EmailPending emailPending){
        return emailPendingRepository.save(emailPending);
    }
}
