package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach,Integer> {

    Coach findCoachById(Integer id);

    List<Coach> findBySportId(Integer sportId);

    List<Coach> findBySportIdIn(List<Integer> sportIds);

    Coach findCoachByUsername(String username);

    Coach findTopBySportIdOrderBySessionPriceAsc(Integer sportId);

    List<Coach> findBySessionPriceLessThanEqual(Double maxPrice);

}
