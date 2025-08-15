package com.example.capstone2controlleradvise.Controller;

import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.CoachReviews;
import com.example.capstone2controlleradvise.Service.CoachReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coach-review")
public class CoachReviewController {
    private final CoachReviewService coachReviewService;

    // Get all reviews
    @GetMapping("/get")
    public ResponseEntity<?> getAllReviews() {
        return ResponseEntity.status(200).body(coachReviewService.getAllReviews());
    }

    // Add review
    @PostMapping("/add")
    public ResponseEntity<?> addReview(@Valid @RequestBody CoachReviews coachReview) {

        coachReviewService.addReview(coachReview);
        return ResponseEntity.status(200).body(new ApiResponse("Review added successfully"));
    }

    // Update review
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Integer id, @Valid @RequestBody CoachReviews coachReview) {

        coachReviewService.updateReview(id, coachReview);
        return ResponseEntity.status(200).body(new ApiResponse("Review updated successfully"));
    }

    // Delete review
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Integer id) {
        coachReviewService.deleteReview(id);
        return ResponseEntity.status(200).body(new ApiResponse("Review deleted successfully"));
    }
}
