package com.example.capstone2controlleradvise.Controller;
import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Service.CoachService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/coach")
public class CoachController {

    private final CoachService coachService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getCoachById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(coachService.getCoachById(id));
    }

    @GetMapping("/get")
    public ResponseEntity<?> getAllCoaches() {
        return ResponseEntity.status(200).body(coachService.getAllCoaches());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addCoach(@Valid @RequestBody Coach coach) {

        coachService.addCoach(coach);
        return ResponseEntity.status(200).body(new ApiResponse("Coach added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCoach(@PathVariable Integer id, @Valid @RequestBody Coach coach) {

        coachService.updateCoach(id, coach);
        return ResponseEntity.status(200).body(new ApiResponse("Coach updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCoach(@PathVariable Integer id) {
        coachService.deleteCoach(id);
        return ResponseEntity.status(200).body(new ApiResponse("Coach deleted successfully"));
    }

    //extra end point ( 3 )
    @GetMapping("/best-coach-by-sport/{sportName}")
    public ResponseEntity<?> getBestCoach(@PathVariable String sportName) {
        return ResponseEntity.status(200).body(coachService.getBestCoachBySportName(sportName));
    }

}

