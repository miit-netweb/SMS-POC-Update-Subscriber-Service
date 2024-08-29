package com.microservices.update_subscriber.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonalDetailsDto {

	@NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Phone number is required")
    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    @Digits(integer = 10, fraction = 0, message = "Phone number must be numeric and exactly 10 digits")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Card number is required")
    @Size(min = 16, max = 16, message = "Card number must be exactly 16 digits")
    @Digits(integer = 16, fraction = 0, message = "Card number must be numeric and exactly 16 digits")
    private String cardNumber;

    @NotBlank(message = "Card type is required")
    private String cardType;

    @NotBlank(message = "Card holder name is required")
    private String cardHolder;

    @NotNull(message = "Card expiry date is required")
    @PastOrPresent(message = "Card expiry date must be in the past or present")
    private LocalDate cardExpiry;

    public Long getPhoneNumber() {
        return Long.parseLong(phoneNumber);
    }
}
