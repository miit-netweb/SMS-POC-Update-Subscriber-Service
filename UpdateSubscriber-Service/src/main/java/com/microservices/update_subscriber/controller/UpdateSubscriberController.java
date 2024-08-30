package com.microservices.update_subscriber.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.update_subscriber.dto.PersonalDetailsDto;
import com.microservices.update_subscriber.exception.ValidationException;
import com.microservices.update_subscriber.proxy.JwtServerProxy;
import com.microservices.update_subscriber.service.SubscriberUpdateService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@AllArgsConstructor
public class UpdateSubscriberController {
	private JwtServerProxy jwtServerProxy;
	private SubscriberUpdateService service;

	@PostMapping("/update/{partnerNumber}/{subscriberNumber}")
	public ResponseEntity<String> updateSubscriber(@Valid @RequestBody PersonalDetailsDto request,
			@RequestHeader("Authorization") String token, @PathVariable Long partnerNumber,
			@PathVariable String subscriberNumber) {

		log.info("Started validating user");

		final ResponseEntity<?> responseEntity;
		try {
			// Token Service Call to validate Token
			responseEntity = jwtServerProxy.validateToken(token);
		} catch (RuntimeException ex) {
			log.error("Original exception: {}", ex.getMessage());
			throw new ValidationException(401, "Invalid Token", HttpStatus.UNAUTHORIZED);
		}
		// Claims Check to verify token with partner number
		Object body = responseEntity.getBody();
		@SuppressWarnings("unchecked")
		Map<String, Object> responseBody = (Map<String, Object>) body;
		String partnerNumFromClaim = Optional.ofNullable(responseBody).map(map -> (String) map.get("partnerNumber"))
				.orElse(null);
		if (partnerNumber != null && partnerNumFromClaim.equals(partnerNumber.toString())) {

			if (service.validateSubscriber(subscriberNumber, partnerNumber)) {
				service.updateSubscriber(request, subscriberNumber);
			} else {
				log.error("Subscriber Number is not Associate with Partner Number");
				throw new ValidationException(422,
						"Validation Failed Partner number is not Associate with the Subscriber Number",
						HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} else {
			log.error("Unauthorized User");
			throw new ValidationException(4445, "Token does not belong to the specified partner",
					HttpStatus.UNAUTHORIZED);
		}
		return ResponseEntity.ok("Subscriber Updated Successfully");
	}
}
