package com.example.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 50, unique = true)
    private String email;
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false,unique = true)
    private String userId;
    @Column(nullable = false,unique = true)
    private String encryptedPwd;
    private String role;

    @Builder
    public UserEntity(String email
            , String name
            , String userId
            , String encryptedPwd
            , String role
    ) {
        this.email = email;
        this.name = name;
        this.userId = userId;
        this.encryptedPwd = encryptedPwd;
        this.role = role;
    }
}
