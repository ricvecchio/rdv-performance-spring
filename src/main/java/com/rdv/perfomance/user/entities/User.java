package com.rdv.perfomance.user.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_user", nullable = false, updatable = false)
    private UUID idUser;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 100, nullable = false)
    private String passwordHash;

    @Column(name = "phone", length = 20)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", length = 20, nullable = false)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "focus_area", length = 30)
    private FocusArea focusArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private AccountStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "plan_type", length = 20, nullable = false)
    private PlanType planType;

    // Campos específicos de TRAINER (nullable no banco; obrigatório por validação quando TRAINER)
    @Column(name = "cref", length = 50)
    private String cref;

    @Column(name = "bio", length = 200)
    private String bio;

    @Column(name = "gym_name", length = 100)
    private String gymName;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public User() {
    }

    public User(UUID idUser, String name, String email, String passwordHash, String phone, UserType userType, FocusArea focusArea, AccountStatus status, PlanType planType, String cref, String bio, String gymName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.idUser = idUser;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phone = phone;
        this.userType = userType;
        this.focusArea = focusArea;
        this.status = status;
        this.planType = planType;
        this.cref = cref;
        this.bio = bio;
        this.gymName = gymName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getters and setters
    public UUID getIdUser() { return idUser; }
    public void setIdUser(UUID idUser) { this.idUser = idUser; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public UserType getUserType() { return userType; }
    public void setUserType(UserType userType) { this.userType = userType; }

    public FocusArea getFocusArea() { return focusArea; }
    public void setFocusArea(FocusArea focusArea) { this.focusArea = focusArea; }

    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }

    public PlanType getPlanType() { return planType; }
    public void setPlanType(PlanType planType) { this.planType = planType; }

    public String getCref() { return cref; }
    public void setCref(String cref) { this.cref = cref; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }

    public String getGymName() { return gymName; }
    public void setGymName(String gymName) { this.gymName = gymName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

}
