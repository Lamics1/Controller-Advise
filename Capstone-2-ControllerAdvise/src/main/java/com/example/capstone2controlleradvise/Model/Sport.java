package com.example.capstone2controlleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Sport {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 3,message = "Must be Length of name more than 2 ")
    @NotEmpty(message="name must be not Empty !")
    @Column(columnDefinition = "varchar(50) not null ")
    private String name;

    @Column(columnDefinition = "varchar(150) not null")
    @NotEmpty(message = "description must be not Empty!")
    private String description;

    @Column(columnDefinition = "varchar(20) not null")
    @Pattern(regexp = "beginner|intermediate|advanced", message = "Sport level must be beginner, intermediate, or advanced")
    private String level;

}
