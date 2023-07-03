package com.zegasoftware.eventremainder.model.payload;

import com.zegasoftware.eventremainder.validation.UniqueEmail;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class UserRegistrationRequest {
    @NotBlank(message = "Email field is required")
    @UniqueEmail(message = "Email already exist")
    @Email(regexp = "^(([^<>()\\[\\]\\\\.,;:\\s@\"]+(\\.[^<>()\\[\\]\\\\.,;:\\s@\"]+)*)|(\".+\"))@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$", message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password field is required")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[\\W_])(?!.* ).{8,}$", message = "Password must contain: 1 uppercase letter, 1 lowercase letter, 1 special character, 1 number, minimum size is 8 characters")
    private String password;

    @NotBlank(message = "First name field is required")
    @Length(min = 3, message = "The first name should have minimum 3 characters")
    @Length(max = 50, message = "The password should have maximum 32 characters")
    private String firstName;

    @NotBlank(message = "Last name field is required")
    @Length(min = 3, message = "The last name should have minimum 3 characters")
    @Length(max = 50, message = "The password should have maximum 32 characters")
    private String lastName;
}

