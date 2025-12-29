package com.rdv.perfomance.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rdv.perfomance.user.entities.AccountStatus;
import com.rdv.perfomance.user.entities.FocusArea;
import com.rdv.perfomance.user.entities.PlanType;
import com.rdv.perfomance.user.entities.UserType;

import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record UserDTO(
        @JsonProperty("idUser") UUID idUser,
        String name,
        String email,
        String phone,
        UserType userType,
        FocusArea focusArea,
        AccountStatus status,
        PlanType planType,
        String cref,
        String bio,
        String gymName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
