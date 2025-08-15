package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.PrivateSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PrivateSessionRepository extends JpaRepository<PrivateSession,Integer> {

    PrivateSession findPrivateSessionById(Integer id);

    List<PrivateSession> findByUserId(Integer userId);

    List<PrivateSession> findByCoachId(Integer coachId);

    List<PrivateSession> findByCoachIdAndStatus(Integer coachId, String status);

    @Query("select ps from PrivateSession ps where ps.coachId =?1 and ps.createdAt >= ?2 order by ps.createdAt desc ")
    List<PrivateSession> giveMeCoachPrivateSessionFromDate(Integer coachId, LocalDateTime fromDate);

    @Query("select p from PrivateSession p where p.userId = ?1 and lower(p.status) = 'accepted'")
    List<PrivateSession> giveMeAcceptedByUserId(Integer userId);
}
