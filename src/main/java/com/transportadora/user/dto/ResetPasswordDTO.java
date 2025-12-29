package com.transportadora.user.dto;

public record ResetPasswordDTO(String token, String newPassword) {
}
