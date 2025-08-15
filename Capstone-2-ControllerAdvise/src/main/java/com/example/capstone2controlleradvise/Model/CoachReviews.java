package com.example.capstone2controlleradvise.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Check(constraints = "rating >= 1 AND rating <= 5")
public class CoachReviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "rating must not be null")
    @Min(value = 1, message = "rating must be at least 1")
    @Max( value = 5, message = "rating must be at most 5")
    @Column(columnDefinition = "int not null")
    private Integer rating;

    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "comment must be not empty")
    private String comment;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "coachId must be not null")
    private Integer coachId;

    @Column(columnDefinition = "int not null")
    @NotNull(message = "userId must be not null")
    private Integer userId;

}

