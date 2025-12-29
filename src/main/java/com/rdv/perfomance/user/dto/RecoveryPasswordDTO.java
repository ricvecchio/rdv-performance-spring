package com.rdv.perfomance.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RecoveryPasswordDTO(@NotBlank @Email String email) {
}
