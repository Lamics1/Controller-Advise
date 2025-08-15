package com.example.capstone2controlleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3,message = "Must be Length of name more than 2 ")
    @NotEmpty(message="name must be not Empty !")
    @Column(columnDefinition = "varchar(50) not null ")
    private String name;

    @Size(min = 5,message = "Must be Length of username more than 4 ")
    @NotEmpty(message="username must be not Empty !")
    @Column(columnDefinition = "varchar(50) unique not null ")
    private String username;

    @NotEmpty(message = "password must be not empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;

    @NotEmpty(message="username must be not Empty !")
    @Column(columnDefinition = "varchar(70) unique not null ")
    private String email;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "sportId must be not null")
    private Integer sportId;

    @Column(columnDefinition = "double not null default 0")
    private Double balance = 0.0;

    @NotNull(message = "Session price must not be null")
    @Column(columnDefinition = "double not null")
    private Double sessionPrice;

    @Column(columnDefinition = "boolean not null default true")
    private Boolean acceptsPrivateTraining = true;
}
