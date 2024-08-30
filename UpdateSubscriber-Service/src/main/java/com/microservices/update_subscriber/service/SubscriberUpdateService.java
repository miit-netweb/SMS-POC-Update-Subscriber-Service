package com.microservices.update_subscriber.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.microservices.update_subscriber.dto.PersonalDetailsDto;
import com.microservices.update_subscriber.entity.PersonalDetails;
import com.microservices.update_subscriber.entity.Subscriber;
import com.microservices.update_subscriber.exception.SubscriberNotFoundException;
import com.microservices.update_subscriber.repository.PersonalDetailsRepository;
import com.microservices.update_subscriber.repository.SubscriberRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubscriberUpdateService {
	
	private SubscriberRepository subscriberRepo;
	private PersonalDetailsRepository personalDetailsRepo;

	public boolean validateSubscriber(String subscriberNumber, Long partnerNumber) {
		Optional<Subscriber> optionalSubscriber = subscriberRepo.findSubscriberBySubscriberNumber(subscriberNumber);
		Subscriber subscriber;
		if (optionalSubscriber.isPresent()) {
			subscriber = optionalSubscriber.get();
		} else {
			throw new SubscriberNotFoundException(404, "Subscriber Not Found", HttpStatus.NOT_FOUND);
		}
		return Objects.equals(partnerNumber, subscriber.getPartnerNumber());
	}

	@Transactional
	public String updateSubscriber(PersonalDetailsDto personalDetailsDto, String subscriberNumber) {
		Optional<Subscriber> optionalSubscriber = subscriberRepo.findSubscriberBySubscriberNumber(subscriberNumber);
		final Subscriber subscriber;
		if (optionalSubscriber.isPresent()) {
			subscriber = optionalSubscriber.get();
		} else {
			throw new SubscriberNotFoundException(404, "Subscriber Not Found", HttpStatus.NOT_FOUND);
		}
		PersonalDetails personalDetails = PersonalDetails.builder()
				.personalId(subscriber.getPersonalDetails().getPersonalId())
				.firstName(personalDetailsDto.getFirstName()).lastName(personalDetailsDto.getLastName())
				.phoneNumber(personalDetailsDto.getPhoneNumber()).email(personalDetailsDto.getEmail())
				.address(personalDetailsDto.getAddress()).cardNumber(personalDetailsDto.getCardNumber())
				.cardType(personalDetailsDto.getCardType()).cardHolder(personalDetailsDto.getCardHolder())
				.cardExpiry(personalDetailsDto.getCardExpiry()).build();

		personalDetailsRepo.save(personalDetails);
		return "Subscriber Updated Successfully";
	}

}