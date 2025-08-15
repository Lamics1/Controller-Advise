package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Integer> {

    TrainingSession findTrainingSessionById(Integer id);

    @Query("select t from TrainingSession t where t.id = ?1")
    TrainingSession giveMeSessionById(Integer id);

    TrainingSession findByStatusAndBookingId(String status, Integer bookingId);
}
