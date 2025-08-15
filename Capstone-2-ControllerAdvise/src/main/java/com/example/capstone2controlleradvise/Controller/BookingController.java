package com.example.capstone2controlleradvise.Controller;
import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.Booking;
import com.example.capstone2controlleradvise.Service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    private final BookingService bookingService;

    // Get all bookings
    @GetMapping("/get")
    public ResponseEntity<?> getAllBookings() {
        return ResponseEntity.status(200).body(bookingService.getAllBookings());
    }

    // extra end point ( 9 )
    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@Valid @RequestBody Booking booking) {

        bookingService.addBooking(booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking added successfully"));
    }

    // Update booking
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer id, @Valid @RequestBody Booking booking) {

        bookingService.updateBooking(id, booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking updated successfully"));
    }

    // Delete booking
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id) {
        bookingService.deleteBooking(id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking deleted successfully"));
    }

    //extra end point ( 4 )
    @GetMapping("/by-username/{username}/{password}")
    public ResponseEntity<?> getUserBookings(@PathVariable String username, @PathVariable String password) {
        return ResponseEntity.status(200).body(bookingService.getBookingsByUserUsername(username, password));
    }
}

