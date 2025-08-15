package com.example.capstone2controlleradvise.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "email REGEXP '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$'")
@Check(constraints = "gender ='male' or gender ='female'")
@Check(constraints = "age >5")
public class User {

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

    @Pattern(regexp = "male|female")
    @Column(columnDefinition = "varchar(6) not null")
    @NotEmpty(message = "Gender must be not empty !")
    private String gender;

    @Min(value = 6,message = "Age must be greater then 5")
    @Column(columnDefinition = "int not null")
    @NotNull(message = "Age must be not null")
    private Integer age;

    @NotEmpty(message="email must be not Empty !")
    @Column(columnDefinition = "varchar(70) unique not null ")
    private String email;

    @NotEmpty(message = "password must be not empty")
    @Column(columnDefinition = "varchar(50) not null")
    private String password;

    @Column(columnDefinition = "double not null default 0")
    private Double balance = 0.0;

    @Column(columnDefinition = "varchar(200) ")
    private String interests;

}

