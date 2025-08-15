package com.example.capstone2controlleradvise.Controller;

import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.PrivateSession;
import com.example.capstone2controlleradvise.Service.PrivateSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/private-session")
public class PrivateSessionController {

    private final PrivateSessionService privateSessionService;

    @GetMapping("/get")
    public ResponseEntity<?> getAll() {
        return ResponseEntity.status(200).body(privateSessionService.getAllSessions());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody PrivateSession privateSession) {

        privateSessionService.addSession(privateSession);
        return ResponseEntity.status(200).body(new ApiResponse("Private session added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody PrivateSession privateSession) {

        privateSessionService.updateSession(id, privateSession);
        return ResponseEntity.status(200).body(new ApiResponse("Private session updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        privateSessionService.deleteSession(id);
        return ResponseEntity.status(200).body(new ApiResponse("Private session deleted successfully"));
    }

    @GetMapping("/coach/{coachId}/status/{status}")
    public ResponseEntity<?> getByCoachAndStatus(@PathVariable Integer coachId, @PathVariable String status) {
        return ResponseEntity.status(200).body(privateSessionService.getByCoachAndStatus(coachId, status));
    }

    //extra end point ( 8 )
    @GetMapping("/coach/{coachId}/from{date}")
    public ResponseEntity<?>getCoachPrivateSessionFromDate(@PathVariable Integer coachId, @PathVariable LocalDate date){
        return ResponseEntity.status(200).body(privateSessionService.getCoachPrivateSessionFromDate(coachId,date));
    }
    //extra end point ( 14 )

    @GetMapping("/accepted/user/{userId}")
    public ResponseEntity<?> getAcceptedForUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(privateSessionService.getAcceptedForUser(userId));
    }

}
