package com.example.capstone2controlleradvise.Controller;

import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.TrainingSession;
import com.example.capstone2controlleradvise.Service.TrainingSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/session")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    // Get all sessions
    @GetMapping("/get")
    public ResponseEntity<?> getAllSessions() {
        return ResponseEntity.status(200).body(trainingSessionService.getAllSessions());
    }

    // Add session manually (optional)
    @PostMapping("/add/{bookingId}")
    public ResponseEntity<?> addSession(@PathVariable Integer bookingId) {
        trainingSessionService.addSession(bookingId);
        return ResponseEntity.status(200).body(new ApiResponse("Training session created successfully"));
    }

    // Update session
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSession(@PathVariable Integer id, @Valid @RequestBody TrainingSession session) {

        trainingSessionService.updateSession(id, session);
        return ResponseEntity.status(200).body(new ApiResponse("Training session updated successfully"));
    }

    // Delete session
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSession(@PathVariable Integer id) {
        trainingSessionService.deleteSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Training session deleted successfully"));
    }

    //extra end point (1)
    @PutMapping("/update-status-cancelled/{id}/{status}")
    public ResponseEntity<?> updateStatusToCancelled(@PathVariable Integer id, @PathVariable String status) {
        trainingSessionService.updateStatusToCancelled(id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Session status updated successfully"));
    }

    //extra end point (12)
    @PutMapping("/update-status-completed/{id}/{status}")
    public ResponseEntity<?> updateStatusToCompleted(@PathVariable Integer id, @PathVariable String status) {
        trainingSessionService.updateStatusToCompleted(id, status);
        return ResponseEntity.status(200).body(new ApiResponse("Session status updated successfully"));
    }

}
