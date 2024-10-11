package com.laoumri.springbootbackend.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
//    @Min(value = 2, message = "Your first name must have at least 2 letters.")
//    @Max(value = 25, message = "Your first name is too long.")
    private String firstname;

//    @Min(value = 2, message = "Your last name must have at least 2 letters.")
//    @Max(value = 25, message = "Your last name is too long.")
    private String lastname;

//    @Email
    private String email;

//    @Min(value = 6, message = "Your password must have at least 6 letters.")
//    @Max(value = 100, message = "Your password is too long.")
    private String password;

    private Set<String> roles;
}
