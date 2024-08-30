package com.microservices.update_subscriber.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private int errorcode;
	private String message;
	private HttpStatus status;
}
