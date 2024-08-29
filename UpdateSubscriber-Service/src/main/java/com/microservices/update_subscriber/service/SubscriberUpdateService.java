package com.microservices.update_subscriber.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.update_subscriber.dto.PersonalDetailsDto;
import com.microservices.update_subscriber.entity.PersonalDetails;
import com.microservices.update_subscriber.entity.Subscriber;
import com.microservices.update_subscriber.repository.PersonalDetailsRepository;
import com.microservices.update_subscriber.repository.SubscriberRepository;

import jakarta.transaction.Transactional;

@Service
public class SubscriberUpdateService {
	
	@Autowired
	private SubscriberRepository subscriberRepo;
	@Autowired
	private PersonalDetailsRepository personalDetailsRepo;

	public boolean validateSubscriber(String subscriberNumber, Long partnerNumber) {
		Optional<Subscriber> optionalSubscriber = subscriberRepo.findSubscriberBySubscriberNumber(subscriberNumber);
		Subscriber subscriber;
		if (optionalSubscriber.isPresent()) {
			subscriber = optionalSubscriber.get();
		} else {
			throw new RuntimeException("Subscriber Not Found");
		}
		Long partnerNumberFromSubscriber = subscriber.getPartnerNumber();

		if (partnerNumber.toString().equals(partnerNumberFromSubscriber.toString())) {
			return true;
		}
		return false;
	}
	
	@Transactional
	public String updateSubscriber(PersonalDetailsDto personalDetailsDto,String subscriberNumber) {
		Optional<Subscriber> optionalSubscriber = subscriberRepo.findSubscriberBySubscriberNumber(subscriberNumber);
		Subscriber subscriber;
		if (optionalSubscriber.isPresent()) {
			subscriber = optionalSubscriber.get();
		} else {
			throw new RuntimeException("Subscriber Not Found");
		}
		PersonalDetails personalDetails=PersonalDetails.builder()
				.personalId(subscriber.getPersonalDetails().getPersonalId())
				.firstName(personalDetailsDto.getFirstName())
				.lastName(personalDetailsDto.getLastName())
				.phoneNumber(personalDetailsDto.getPhoneNumber())
				.email(personalDetailsDto.getEmail())
				.address(personalDetailsDto.getAddress())
				.cardNumber(personalDetailsDto.getCardNumber())
				.cardType(personalDetailsDto.getCardType())
				.cardHolder(personalDetailsDto.getCardHolder())
				.cardExpiry(personalDetailsDto.getCardExpiry()).build();
		
		
		personalDetailsRepo.save(personalDetails);
		return "Subscriber Updated Successfully";
	}
	
}