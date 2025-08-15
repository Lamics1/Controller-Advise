package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Booking;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.Sport;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final TrainingSessionRepository trainingSessionService;
    private final SportRepository sportRepository;
    private final TrainingSessionRepository trainingSessionRepository;

    // Get all bookings
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    // extra end point ( 9 )
    public void addBooking(Booking booking) {

        User user = userRepository.findUserById(booking.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }

        Coach coach = coachRepository.findCoachById(booking.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach not found");
        }

        Sport sport = sportRepository.findSportById(coach.getSportId());
        if (sport == null) {
            throw new ApiException("Sport not found");
        }

        if (sport.getLevel().equalsIgnoreCase("advanced") && user.getAge() < 18) {
            throw new ApiException("This sport is for advanced users 18 years or older only");
        }

        Double sessionPrice = coach.getSessionPrice();
        if (user.getBalance() < sessionPrice) {
            throw new ApiException("Insufficient balance");
        }

        if (booking.getStartTime() == null) {
            throw new ApiException("Start time must not be null");
        }
        if (booking.getStartTime().isBefore(LocalDate.now())) {
            throw new ApiException("Start date must be today or in the future");
        }

        user.setBalance(user.getBalance() - sessionPrice);
        userRepository.save(user);

        coach.setBalance(coach.getBalance() + sessionPrice);
        coachRepository.save(coach);

        Booking savedBooking = bookingRepository.save(booking);

        // Add session after complete Booking

        trainingSessionService.addSession(savedBooking.getId());

    }

    public void updateBooking(Integer id, Booking booking) {
        Booking oldBooking = bookingRepository.findBookingsById(id);
        if (oldBooking == null) {
            throw new ApiException("Booking not found");
        }

        User user = userRepository.findUserById(booking.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }

        Coach coach = coachRepository.findCoachById(booking.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach not found");
        }

        Sport sport = sportRepository.findSportById(coach.getSportId());
        if (sport == null) {
            throw new ApiException("Sport not found");
        }

        if (sport.getLevel().equalsIgnoreCase("advanced") && user.getAge() < 18) {
            throw new ApiException("This sport is for advanced users 18 years or older only");
        }

        oldBooking.setCoachId(booking.getCoachId());
        oldBooking.setUserId(booking.getUserId());
        oldBooking.setStartTime(booking.getStartTime());
        // oldBooking.setStatus(booking.getStatus());

        bookingRepository.save(oldBooking);
    }

    // Delete booking
    public void deleteBooking(Integer id) {
        Booking booking = bookingRepository.findBookingsById(id);
        if (booking == null) {
            throw new ApiException("Booking not found");
        }
        bookingRepository.delete(booking);
    }

    //extra end point ( 4 )
    public List<Booking> getBookingsByUserUsername(String username,String password) {

        User user = userRepository.findUserByUsernameAndPassword(username,password);
        if (user == null) {
            throw new ApiException("User not found");
        }

        List<Booking> list = bookingRepository.findByUserIdOrderByStartTimeDesc(user.getId());
        if (list == null || list.isEmpty()) {
            throw new ApiException("No bookings for this user");
        }

        return list;
    }

}

