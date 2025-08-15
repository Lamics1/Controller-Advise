package com.example.capstone2controlleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "bookingId must not be null")
    @Column(columnDefinition = "int not null")
    private Integer bookingId;

    @Pattern(regexp = "^(scheduled|completed|cancelled)$", message = "Status must be one of: scheduled, completed, or cancelled")
    @Column(columnDefinition = "varchar(10) not null default 'scheduled'")
    private String status;

}

