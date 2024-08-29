package com.microservices.update_subscriber.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.update_subscriber.entity.Subscriber;

public interface SubscriberRepository extends JpaRepository<Subscriber,Long> {
	
	public Optional<Subscriber> findSubscriberBySubscriberNumber(String subscriberNumber);
	public Optional<Subscriber> findSubscriberByMemId(Long memId );
}
