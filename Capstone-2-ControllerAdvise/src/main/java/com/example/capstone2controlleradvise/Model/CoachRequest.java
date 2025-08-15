package com.example.capstone2controlleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachRequest {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "userId must be not null")
    private Integer userId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "coachId must be not null")
    private Integer coachId;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "skillRequested must be not empty")
    private String skillRequested;

    @Column(columnDefinition = "varchar(200)")
    private String description;

    @Pattern(regexp = "pending|accepted|rejected")
    @Column(columnDefinition = "varchar(10) not null default 'pending'")
    private String status = "pending";

    @Column(columnDefinition = "varchar(200)")
    private String responseNote;

    @CreationTimestamp
    @Column(columnDefinition = "TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP")
    private LocalDate createdAt;
}

