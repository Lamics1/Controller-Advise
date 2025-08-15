package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Booking;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.TrainingSession;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.BookingRepository;
import com.example.capstone2controlleradvise.Repository.CoachRepository;
import com.example.capstone2controlleradvise.Repository.TrainingSessionRepository;
import com.example.capstone2controlleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;

    // Get all sessions
    public List<TrainingSession> getAllSessions() {
        return trainingSessionRepository.findAll();
    }

    public void addSession(Integer bookingId) {
        Booking booking = bookingRepository.findBookingsById(bookingId);
        if (booking == null) {
            throw new ApiException("Booking not found");
        }

        TrainingSession session = new TrainingSession();
        session.setBookingId(booking.getId());

        //To prevent database conflicts

        session.setStatus("scheduled");

        trainingSessionRepository.save(session);
    }

    // Update session
    public void updateSession(Integer id, TrainingSession session) {
        TrainingSession oldSession = trainingSessionRepository.findTrainingSessionById(id);
        if (oldSession == null) {
            throw new ApiException("Training session not found");
        }

        Booking booking = bookingRepository.findBookingsById(session.getBookingId());
        if (booking == null) {
            throw new ApiException("Booking not found");
        }
        oldSession.setStatus(session.getStatus());
        oldSession.setBookingId(session.getBookingId());
        trainingSessionRepository.save(oldSession);
    }

    // Delete session
    public void deleteSession(Integer id) {
        TrainingSession session = trainingSessionRepository.findTrainingSessionById(id);
        if (session == null) {
            throw new ApiException("Training session not found");
        }
        trainingSessionRepository.delete(session);
    }

    //extra end pointe (1)

    public void updateStatusToCancelled(Integer id, String status) {

        TrainingSession trainingSession = trainingSessionRepository.findTrainingSessionById(id);
        if (trainingSession == null) {
            throw new ApiException("Session not found");
        }

        if (!status.equalsIgnoreCase("scheduled") &&
                !status.equalsIgnoreCase("completed") &&
                !status.equalsIgnoreCase("cancelled")) {
            throw new ApiException("Invalid status");
        }
        if (trainingSession.getStatus().equalsIgnoreCase("cancelled")) {
            throw new ApiException("This session is already cancelled");
        }


        if (status.equalsIgnoreCase("cancelled")) {
            Booking booking = bookingRepository.findBookingsById(trainingSession.getBookingId());
            if (booking == null) {
                throw new ApiException("Booking not found");
            }

            User user = userRepository.findUserById(booking.getUserId());
            Coach coach = coachRepository.findCoachById(booking.getCoachId());

            if (user == null || coach == null) {
                throw new ApiException("User or Coach not found");
            }

            Double sessionPrice = coach.getSessionPrice();

            // add Balance for user
            user.setBalance(user.getBalance() + sessionPrice);
            coach.setBalance(coach.getBalance() - sessionPrice);

            userRepository.save(user);
            coachRepository.save(coach);
        }

        trainingSession.setStatus(status.toLowerCase());
        trainingSessionRepository.save(trainingSession);
    }

    //extra end point (12)

    public void updateStatusToCompleted(Integer id, String status) {

        TrainingSession trainingSession = trainingSessionRepository.findTrainingSessionById(id);
        if (trainingSession == null) {
            throw new ApiException("Session not found");
        }

        if (!status.equalsIgnoreCase("scheduled") &&
                !status.equalsIgnoreCase("completed") &&
                !status.equalsIgnoreCase("cancelled")) {
            throw new ApiException("Invalid status");
        }
        if (trainingSession.getStatus().equalsIgnoreCase("completed")) {
            throw new ApiException("This session is already completed");
        }


        if (status.equalsIgnoreCase("completed")) {
            Booking booking = bookingRepository.findBookingsById(trainingSession.getBookingId());
            if (booking == null) {
                throw new ApiException("Booking not found");
            }

            User user = userRepository.findUserById(booking.getUserId());
            Coach coach = coachRepository.findCoachById(booking.getCoachId());

            if (user == null || coach == null) {
                throw new ApiException("User or Coach not found");
            }

            userRepository.save(user);
            coachRepository.save(coach);
        }

        trainingSession.setStatus(status.toLowerCase());
        trainingSessionRepository.save(trainingSession);
    }

}
