package com.rdv.perfomance.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequestDTO {

    @NotBlank
    @Email
    @Size(max = 100)
    public String email;

    @NotBlank
    @Size(min = 8, max = 100)
    public String password;

    // getters/setters omitted

}
