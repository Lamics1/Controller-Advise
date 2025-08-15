package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.PrivateSession;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.CoachRepository;
import com.example.capstone2controlleradvise.Repository.PrivateSessionRepository;
import com.example.capstone2controlleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PrivateSessionService {

    private final PrivateSessionRepository privateSessionRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;

    public List<PrivateSession> getAllSessions() {
        return privateSessionRepository.findAll();
    }

    public void addSession(PrivateSession privateSession) {
        User user = userRepository.findUserById(privateSession.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }

        Coach coach = coachRepository.findCoachById(privateSession.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach not found");
        }

        privateSessionRepository.save(privateSession);
    }

    public void updateSession(Integer id, PrivateSession privateSession) {
        PrivateSession oldSession = privateSessionRepository.findPrivateSessionById(id);
        if (oldSession == null) {
            throw new ApiException("Private session not found");
        }
        oldSession.setSkillRequested(privateSession.getSkillRequested());
        oldSession.setDescription(privateSession.getDescription());
        oldSession.setStatus(privateSession.getStatus());

        privateSessionRepository.save(oldSession);
    }

    public void deleteSession(Integer id) {
        PrivateSession privateSession = privateSessionRepository.findPrivateSessionById(id);
        if (privateSession == null) {
            throw new ApiException("Private session not found");
        }
        privateSessionRepository.delete(privateSession);
    }

    public List<PrivateSession> getByCoachAndStatus(Integer coachId, String status) {
        return privateSessionRepository.findByCoachIdAndStatus(coachId, status);
    }

    //extra end point ( 8 )
    public List<PrivateSession> getCoachPrivateSessionFromDate(Integer coachId, LocalDate fromDate) {
        if (fromDate == null) {
            throw new ApiException("from Date must be not null");
        }
        if (coachRepository.findCoachById(coachId) == null) {
            throw new ApiException("coachId not found");
        }
        LocalDateTime startDate = fromDate.atStartOfDay();
        return privateSessionRepository.giveMeCoachPrivateSessionFromDate(coachId, startDate);

    }
    //extra end point (14)
    public List<PrivateSession> getAcceptedForUser(Integer userId) {
        if (userRepository.findUserById(userId) == null) {
            throw new ApiException("User not found");
        }
        return privateSessionRepository.giveMeAcceptedByUserId(userId);
    }


}
