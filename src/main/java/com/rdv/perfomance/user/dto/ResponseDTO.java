package com.rdv.perfomance.user.dto;

import com.rdv.perfomance.user.entities.AccountStatus;
import com.rdv.perfomance.user.entities.PlanType;
import com.rdv.perfomance.user.entities.UserType;

import java.util.UUID;

public record ResponseDTO(UUID idUser, String name, String email, String token, UserType userType, PlanType planType, AccountStatus status) {
}
