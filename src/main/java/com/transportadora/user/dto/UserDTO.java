package com.transportadora.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(
        @JsonProperty("idUser") java.util.UUID idUser,
        String name,
        String email,
        String username,
        String password,
        String permission) {
}
