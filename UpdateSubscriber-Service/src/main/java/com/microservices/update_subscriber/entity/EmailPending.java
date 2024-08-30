package com.microservices.update_subscriber.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class EmailPending {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String subscriberNumber;
	private String emailId;
	private int code;
	private String status;

	public EmailPending(String subscriberNumber, String emailId, int code, String status) {
		super();
		this.subscriberNumber = subscriberNumber;
		this.emailId = emailId;
		this.code = code;
		this.status = status;
	}
}
