package com.microservices.update_subscriber.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.update_subscriber.entity.PersonalDetails;

public interface PersonalDetailsRepository extends JpaRepository<PersonalDetails,Long> {
}
