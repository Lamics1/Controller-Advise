package com.example.capstone2controlleradvise.Repository;

import com.example.capstone2controlleradvise.Model.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportRepository extends JpaRepository<Sport,Integer> {
    Sport findSportById(Integer id);

    @Query("select s from Sport s where s.level = ?1")
    List<Sport> giveMeSportsByLevel(String level);

    @Query("select s from Sport s where s.level in ?1")
    List<Sport> giveMeSportsByLevels(List<String> levels);

    List<Sport> findByName(String name);

    @Query("""
           select s from Sport s
           where ( lower(:i) like concat('%','football','%')     and lower(s.name) = 'football' )
              or ( lower(:i) like concat('%','basketball','%')   and lower(s.name) = 'basketball' )
              or ( lower(:i) like concat('%','handball','%')     and lower(s.name) = 'handball' )
              or ( lower(:i) like concat('%','swimming','%')     and lower(s.name) = 'swimming' )
              or ( lower(:i) like concat('%','karate','%')       and lower(s.name) = 'karate' )
              or ( lower(:i) like concat('%','boxing','%')       and lower(s.name) = 'boxing' )
              or ( lower(:i) like concat('%','crossfit','%')     and lower(s.name) = 'crossfit' )
              or ( lower(:i) like concat('%','running','%')      and lower(s.name) = 'running' )
              or ( lower(:i) like concat('%','weightlifting','%') and lower(s.name) = 'weightlifting' )
              or ( lower(:i) like concat('%','horse riding','%') and lower(s.name) = 'horse riding' )
           """)
    List<Sport> giveMeSportsByInterests(@Param("i") String interestsText);


}
