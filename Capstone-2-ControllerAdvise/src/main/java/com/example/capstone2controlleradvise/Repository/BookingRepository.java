package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Integer> {

    Booking findBookingsById(Integer id);

    List<Booking> findByCoachId(Integer coachId);

    List<Booking> findByUserId(Integer userId);

    List<Booking> findByUserIdOrderByStartTimeDesc(Integer userId);

}

