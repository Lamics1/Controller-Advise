package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.Coach;
import com.example.capstone2controlleradvise.Model.CoachRequest;
import com.example.capstone2controlleradvise.Model.PrivateSession;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.CoachRepository;
import com.example.capstone2controlleradvise.Repository.CoachRequestRepository;
import com.example.capstone2controlleradvise.Repository.PrivateSessionRepository;
import com.example.capstone2controlleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CoachRequestService {
    private final CoachRequestRepository coachRequestRepository;
    private final UserRepository userRepository;
    private final CoachRepository coachRepository;
    private final PrivateSessionRepository privateSessionRepository;

    public List<CoachRequest> getAllRequests() {
        return coachRequestRepository.findAll();
    }

    public void addRequest(CoachRequest coachRequest) {

        User user = userRepository.findUserById(coachRequest.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }

        Coach coach = coachRepository.findCoachById(coachRequest.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach not found");
        }
        if (coach.getAcceptsPrivateTraining() == null || !coach.getAcceptsPrivateTraining()) {
            throw new ApiException("This coach does not accept private training");
        }
        if (coachRequest.getStatus() == null || coachRequest.getStatus().isEmpty()) {
            coachRequest.setStatus("pending");
        } else if (!coachRequest.getStatus().equalsIgnoreCase("pending")
                && !coachRequest.getStatus().equalsIgnoreCase("accepted")
                && !coachRequest.getStatus().equalsIgnoreCase("rejected")) {
            throw new ApiException("Invalid status");
        }

        coachRequestRepository.save(coachRequest);
    }


    // not
    public void updateRequest(Integer id, CoachRequest coachRequest) {

        CoachRequest old = coachRequestRepository.findCoachRequestById(id);
        if (old == null) {
            throw new ApiException("Request not found");
        }

        if (!old.getStatus().equalsIgnoreCase("pending")) {
            throw new ApiException("Only pending requests can be updated");
        }

        User user = userRepository.findUserById(coachRequest.getUserId());
        if (user == null) {
            throw new ApiException("User not found");
        }

        Coach coach = coachRepository.findCoachById(coachRequest.getCoachId());
        if (coach == null) {
            throw new ApiException("Coach not found");
        }

        if (coachRequest.getStatus() != null &&
                !coachRequest.getStatus().equalsIgnoreCase("pending")
                && !coachRequest.getStatus().equalsIgnoreCase("accepted")
                && !coachRequest.getStatus().equalsIgnoreCase("rejected")) {
            throw new ApiException("Invalid status");
        }

        old.setUserId(coachRequest.getUserId());
        old.setCoachId(coachRequest.getCoachId());
        old.setSkillRequested(coachRequest.getSkillRequested());
        old.setDescription(coachRequest.getDescription());
        old.setStatus("pending");

        coachRequestRepository.save(old);
    }

    public void deleteRequest(Integer id) {
        CoachRequest req = coachRequestRepository.findCoachRequestById(id);
        if (req == null) {
            throw new ApiException("Request not found");
        }

        coachRequestRepository.delete(req);
    }

    //accepted

    //extra end point ( 7 )
    public void changeStatusAccepted(Integer requestId, String status) {

        CoachRequest coachRequest = coachRequestRepository.findCoachRequestById(requestId);
        if (coachRequest == null) {
            throw new ApiException("Coach request not found");
        }

        if (!status.equalsIgnoreCase("pending")
                && !status.equalsIgnoreCase("accepted")
                && !status.equalsIgnoreCase("rejected")) {
            throw new ApiException("Invalid status");
        }


        if (coachRequest.getStatus().equalsIgnoreCase("accepted") && status.equalsIgnoreCase("accepted")) {
            throw new ApiException("Request already accepted");
        }

        coachRequest.setStatus(status.toLowerCase());
        coachRequestRepository.save(coachRequest);

        // pending|accepted|rejected

        if (status.equalsIgnoreCase("accepted")) {
            PrivateSession privateSession = new PrivateSession();
            privateSession.setUserId(coachRequest.getUserId());
            privateSession.setCoachId(coachRequest.getCoachId());
            privateSession.setSkillRequested(coachRequest.getSkillRequested());
            privateSession.setDescription(coachRequest.getDescription());

            privateSession.setStatus("accepted");
            coachRequest.setStatus("accepted");
            privateSessionRepository.save(privateSession);
        }


    }


    //extra end point ( 6 )
    public List<CoachRequest> getByCoachAndStatus(Integer coachId, String status) {

        List<CoachRequest> requests = coachRequestRepository.getByCoachAndStatus(coachId, status);

        if (requests.isEmpty()) {
            throw new ApiException("No requests found for this coach with the given status");
        }

        return requests;
    }
    //extra end point ( 11 )
    public void changeStatusToRejected(Integer requestId, String status){

        CoachRequest coachRequest = coachRequestRepository.findCoachRequestById(requestId);
        if (coachRequest == null) {
            throw new ApiException("Coach request not found");
        }

        if (!status.equalsIgnoreCase("pending")
                && !status.equalsIgnoreCase("accepted")
                && !status.equalsIgnoreCase("rejected")) {
            throw new ApiException("Invalid status");
        }


        if (coachRequest.getStatus().equalsIgnoreCase("rejected") && status.equalsIgnoreCase("rejected")) {
            throw new ApiException("Request already rejected");
        }

        coachRequest.setStatus(status.toLowerCase());
        coachRequestRepository.save(coachRequest);

        // pending|accepted|rejected

        if (status.equalsIgnoreCase("rejected")) {

            PrivateSession privateSession = new PrivateSession();
            privateSession.setUserId(coachRequest.getUserId());
            privateSession.setCoachId(coachRequest.getCoachId());
            privateSession.setSkillRequested(coachRequest.getSkillRequested());
            privateSession.setDescription(coachRequest.getDescription());

            privateSession.setStatus("rejected");
            coachRequest.setStatus("rejected");
            privateSessionRepository.save(privateSession);
        }


    }

    //extra end point ( 13 )
    public List<CoachRequest> getRejectedRequestsByUser(Integer userId) {

        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        return coachRequestRepository.findCoachRequestsByUserIdAndStatusIgnoreCase(userId, "rejected");


    }

}
