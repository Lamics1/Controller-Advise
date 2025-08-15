package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.CoachReviews;
import com.example.capstone2controlleradvise.Model.Sport;
import com.example.capstone2controlleradvise.Repository.CoachRepository;
import com.example.capstone2controlleradvise.Repository.CoachReviewsRepository;
import com.example.capstone2controlleradvise.Repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoachService {
    private final CoachReviewsRepository coachReviewRepository;
    private final CoachRepository coachRepository;
    private final SportRepository sportRepository;

    public Coach getCoachById(Integer id) {
        Coach coach = coachRepository.findCoachById(id);
        if (coach == null) {
            throw new ApiException("Coach not found");
        }
        return coach;
    }

    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public void addCoach(Coach coach) {
        Sport oldSport = sportRepository.findSportById(coach.getSportId());
        if (oldSport == null) {
            throw new ApiException("Sport ID not found");
        }
        coach.setBalance(0.0);


        if (coach.getSessionPrice() == null || coach.getSessionPrice() <= 0) {
            throw new ApiException("Session price must be greater than 0");
        }

        coachRepository.save(coach);
    }

    public void updateCoach(Integer id, Coach coach) {

        Coach oldCoach = coachRepository.findCoachById(id);
        if (oldCoach == null) {
            throw new ApiException("Coach not found");
        }

        Sport oldSport = sportRepository.findSportById(coach.getSportId());
        if (oldSport == null) {
            throw new ApiException("Sport ID not found");
        }

        oldCoach.setName(coach.getName());
        oldCoach.setUsername(coach.getUsername());
        oldCoach.setPassword(coach.getPassword());
        oldCoach.setEmail(coach.getEmail());
        oldCoach.setSportId(coach.getSportId());
        // oldCoach.setBalance(coach.getBalance());
        //  oldCoach.setSessionPrice(coach.getSessionPrice());

        if (coach.getSessionPrice() != null && coach.getSessionPrice() > 0) {
            oldCoach.setSessionPrice(coach.getSessionPrice());

        }
        coachRepository.save(oldCoach);
    }

    public void deleteCoach(Integer id) {
        Coach coach = coachRepository.findCoachById(id);
        if (coach == null) {
            throw new ApiException("Coach not found");
        }
        coachRepository.delete(coach);
    }

//extra end point ( 3 )

    public Coach getBestCoachBySportName(String sportName) {

        List<Sport> sports = sportRepository.findByName(sportName);
        if (sports == null || sports.isEmpty()) {
            throw new ApiException("Sport not found");
        }

        Coach bestCoach = null;
        double bestAvg = -1;

        for (Sport sport : sports) {

            List<Coach> coaches = coachRepository.findBySportId(sport.getId());
            if (coaches != null && !coaches.isEmpty()) {
                for (Coach coach : coaches) {
                    List<CoachReviews> reviews = coachReviewRepository.findByCoachId(coach.getId());
                    if (reviews != null && !reviews.isEmpty()) {
                        double sum = 0;
                        for (CoachReviews r : reviews) {
                            sum += r.getRating();
                        }
                        double avg = sum / reviews.size();

                        if (avg > bestAvg) {
                            bestAvg = avg;
                            bestCoach = coach;
                        }
                    }
                }
            }
        }

        if (bestCoach == null) {
            throw new ApiException("No coaches with reviews for this sport");
        }

        return bestCoach;
    }

}
