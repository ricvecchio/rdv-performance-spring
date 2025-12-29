package com.rdv.perfomance.user.dto;

import com.rdv.perfomance.user.entities.FocusArea;
import com.rdv.perfomance.user.entities.PlanType;
import com.rdv.perfomance.user.entities.UserType;
import jakarta.validation.constraints.*;

public class RegisterRequestDTO {

    @NotBlank
    @Size(max = 100)
    public String name;

    @NotBlank
    @Email
    @Size(max = 100)
    public String email;

    @NotBlank
    @Size(min = 8, max = 100)
    public String password;

    @Size(max = 20)
    public String phone;

    @NotNull
    public UserType userType;

    public FocusArea focusArea;

    public PlanType planType;

    @Size(max = 50)
    public String cref;

    @Size(max = 200)
    public String bio;

    @Size(max = 100)
    public String gymName;

    // getters/setters omitted for brevity — using public fields for record-like DTO

}
