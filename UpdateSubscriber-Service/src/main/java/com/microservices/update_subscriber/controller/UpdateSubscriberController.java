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
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UpdateSubscriberController {
	private JwtServerProxy jwtServerProxy;
	private SubscriberUpdateService service;

	public UpdateSubscriberController(JwtServerProxy jwtServerProxy, SubscriberUpdateService service) {
		super();
		this.jwtServerProxy = jwtServerProxy;
		this.service = service;
	}

	@PostMapping("/update")
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
			throw new ValidationException(401, "Invalid Token", HttpStatus.BAD_REQUEST);
		}
		// Claims Check to verify token with partner number
		Object body = responseEntity.getBody();
		Map<String, Object> responseBody = (Map<String, Object>) body;
		String partnerNumFromClaim = Optional.ofNullable(responseBody).map(map -> (String) map.get("partnerNumber"))
				.orElse(null);
		if (partnerNumber != null && partnerNumFromClaim.equals(partnerNumber.toString())) {

			if (service.validateSubscriber(subscriberNumber, partnerNumber)) {
				service.updateSubscriber(request, subscriberNumber);
			} else {
				throw new RuntimeException(
						"Validation Failed Partner number is not Associate with the Subscriber Number");
			}
		} else {
			throw new RuntimeException("Token does not belong to the specified partner");
		}
		return ResponseEntity.ok("Updated");
	}
}
