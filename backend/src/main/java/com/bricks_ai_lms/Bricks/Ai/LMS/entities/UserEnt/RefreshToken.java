package com.bricks_ai_lms.bricks.ai.lms.entities.UserEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 1000)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private boolean isActive;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiryDate);
    }

    @Column(nullable = false)
    private String username;

    public RefreshToken() {
        this.createdAt = LocalDateTime.now();
        this.isActive = true;
    }

    public RefreshToken(String token, String username, LocalDateTime expiryDate) {
        this();
        this.token = token;
        this.username = username;
        this.expiryDate = expiryDate;
    }
}


