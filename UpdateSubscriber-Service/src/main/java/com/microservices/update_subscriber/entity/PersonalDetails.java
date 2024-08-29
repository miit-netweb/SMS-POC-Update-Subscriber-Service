package com.microservices.update_subscriber.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PersonalDetails{
  
	@Id
    private long personalId;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private Long phoneNumber;
    @Column(unique = true)
    private String email;
    private String address;
    private String cardNumber;
    private String cardType;
    private String cardHolder;
    private LocalDate cardExpiry;
}