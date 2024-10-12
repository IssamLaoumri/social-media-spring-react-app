package com.laoumri.springbootbackend.dto.requests;

import jakarta.validation.constraints.Email;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Length(min = 2, message = "Your first name must have at least 2 letters.")
    @Length(max = 25, message = "Your first name is too long.")
    private String firstname;

    @Length(min = 2, message = "Your last name must have at least 2 letters.")
    @Length(max = 25, message = "Your last name is too long.")
    private String lastname;

    @Email
    private String email;

    @Length(min = 6, message = "Your password must have at least 6 letters.")
    @Length(max = 100, message = "Your password is too long.")
    private String password;

    private Set<String> roles;
}
