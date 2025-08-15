package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.Sport;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.CoachRepository;
import com.example.capstone2controlleradvise.Repository.SportRepository;
import com.example.capstone2controlleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SportService {

    private final UserRepository userRepository;
    private final SportRepository sportRepository;
    private final CoachRepository coachRepository;

    public List<Sport> getAllSports() {
        return sportRepository.findAll();
    }

    public void addSport(Sport sport) {
        sportRepository.save(sport);
    }

    public void updateSport(Integer id, Sport sport) {
        Sport oldSport = sportRepository.findSportById(id);
        if (oldSport == null) {
            throw new ApiException("Sport not found");
        }
        oldSport.setLevel(sport.getLevel());
        oldSport.setName(sport.getName());
        oldSport.setDescription(sport.getDescription());
        sportRepository.save(oldSport);
    }

    public void deleteSport(Integer id) {
        Sport sport = sportRepository.findSportById(id);
        if (sport == null) {
            throw new ApiException("Sport not found");
        }
        sportRepository.delete(sport);
    }

    // extra end point ( 2 )

    public List<Sport> getSuitableSportsForUser(Integer userId) {

        User u = userRepository.giveMeUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }

        Integer age = u.getAge();
        if (age == null) {
            throw new ApiException("User age is missing");
        }

        if (age >= 18) {
            return sportRepository.findAll();
        } else if (age < 12) {
            return sportRepository.giveMeSportsByLevel("beginner");
        } else {
            return sportRepository.giveMeSportsByLevels(List.of("beginner", "intermediate"));
        }
    }

    //extra end point ( 5 )

    public List<Sport> getSportsByUserInterests(Integer userId) {
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }

        if (u.getInterests() == null || u.getInterests().trim().isEmpty()) {
            throw new ApiException("User has no interests");
        }

        String i = u.getInterests().toLowerCase();
        return sportRepository.giveMeSportsByInterests(i);

    }
    public List<Sport> getAffordableSportsForUser(Integer userId) {
        User u = userRepository.findUserById(userId);
        if (u == null) {
            throw new ApiException("User not found");
        }

        Integer age = u.getAge();
        if (age == null) {
            throw new ApiException("User age is missing");
        }

        List<Coach> coaches = coachRepository.findBySessionPriceLessThanEqual(u.getBalance());

        List<Sport> sports = new ArrayList<>();
        for (Coach coach : coaches) {
            Sport sport = sportRepository.findSportById(coach.getSportId());
            if (sport != null) {
                if (age >= 18) {
                    sports.add(sport);
                } else if (age < 12 && sport.getLevel().equalsIgnoreCase("beginner")) {
                    sports.add(sport);
                } else if (age >= 12 && sport.getLevel().matches("(?i)beginner|intermediate")) {
                    sports.add(sport);
                }
            }
        }

        if (sports.isEmpty()) {
            throw new ApiException("No suitable sports found for your age and balance");
        }

        return sports;
    }
}
