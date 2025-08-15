package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserById(Integer id);

    @Query("select u from User u where u.id = ?1")
    User giveMeUserById(Integer id);

    @Query("select u from User u where u.age =?1")
    User GiveMyUserByAge(Integer age);

    User findUserByUsernameAndPassword(String username, String password);

    @Query("select avg (u.age) from User u where lower(u.gender)=lower(?1) ")
    Double giveMyAverageAgeByGender(String gender);


}
