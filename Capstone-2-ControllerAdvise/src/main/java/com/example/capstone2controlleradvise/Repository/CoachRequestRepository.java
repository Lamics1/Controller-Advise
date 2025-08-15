package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.CoachRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRequestRepository extends JpaRepository<CoachRequest,Integer> {

    CoachRequest findCoachRequestById(Integer id);

    @Query("SELECT cr from CoachRequest cr where cr.coachId=?1 and cr.status =?2")
    List<CoachRequest> getByCoachAndStatus(Integer CoachId, String status);

    List<CoachRequest> findCoachRequestsByUserIdAndStatusIgnoreCase(Integer userId, String status);
}
