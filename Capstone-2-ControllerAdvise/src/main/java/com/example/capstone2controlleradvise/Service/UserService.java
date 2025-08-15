package com.example.capstone2controlleradvise.Service;

import com.example.capstone2controlleradvise.Api.ApiException;
import com.example.capstone2controlleradvise.Model.User;
import com.example.capstone2controlleradvise.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    final private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id, User user) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            throw new ApiException("User not found");
        }
        oldUser.setName(user.getName());
        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setGender(user.getGender());
        oldUser.setAge(user.getAge());
        oldUser.setBalance(user.getBalance());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        User oldUser = userRepository.findUserById(id);
        if (oldUser == null) {
            throw new ApiException("user not found");
        }
        userRepository.delete(oldUser);
    }
    //??
    public Double giveMyAverageAgeByGender(String gender){
        if (gender == null){
            throw new ApiException("gender must be not null");
        }

        Double average = userRepository.giveMyAverageAgeByGender(gender);
        if (average == null){
            throw new ApiException("gender not found");
        }
        return average;
    }
}
