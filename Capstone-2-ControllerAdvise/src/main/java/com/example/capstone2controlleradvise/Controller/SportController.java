package com.example.capstone2controlleradvise.Controller;

import com.example.capstone2controlleradvise.Api.ApiResponse;
import com.example.capstone2controlleradvise.Model.Sport;
import com.example.capstone2controlleradvise.Service.SportService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/sport")
public class SportController {

    final private SportService sportService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllSports() {
        return ResponseEntity.status(200).body(sportService.getAllSports());
    }


    @PostMapping("/add")
    public ResponseEntity<?> addSport(@Valid @RequestBody Sport sport) {

        sportService.addSport(sport);
        return ResponseEntity.status(200).body(new ApiResponse("Sport added successfully"));
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateSport(@PathVariable Integer id, @Valid @RequestBody Sport sport) {

        sportService.updateSport(id, sport);
        return ResponseEntity.status(200).body(new ApiResponse("Sport updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteSport(@PathVariable Integer id) {
        sportService.deleteSport(id);
        return ResponseEntity.status(200).body(new ApiResponse("Sport deleted successfully"));
    }

    // extra end point ( 2 )

    @GetMapping("/get-suitable/{userId}")
    public ResponseEntity<?> getSuitableSports(@PathVariable Integer userId) {
        List<Sport> suitableSports = sportService.getSuitableSportsForUser(userId);
        return ResponseEntity.status(200).body(suitableSports);
    }

    //extra end point ( 5 )
    @GetMapping("/sports/match-interests/{userId}")
    public ResponseEntity<?> getSportsByUserInterests(@PathVariable Integer userId){
        List<Sport> Interests = sportService.getSportsByUserInterests(userId);
        return ResponseEntity.status(200).body(Interests);
    }
    //extra end point ( 15 )
    @GetMapping("/sports/affordable/{userId}")
    public ResponseEntity<?> getAffordableSportsForUser(@PathVariable Integer userId) {
        return ResponseEntity.status(200).body(sportService.getAffordableSportsForUser(userId));
    }

}
