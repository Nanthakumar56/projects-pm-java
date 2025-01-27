package com.manage_projects.projects.repository;

import com.manage_projects.projects.entity.MileStones;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MileStoneRepository extends CrudRepository<MileStones, String> {

    List<MileStones> findByProjectidOrderByStepAsc(String projectid);

    List<MileStones> findByStepGreaterThanEqualAndProjectid(int step, String projectid);

    @Modifying
    @Transactional
    @Query("UPDATE MileStones m SET m.step = m.step - 1 WHERE m.step > :step AND m.projectid = :projectid")
    void updateStepNumbersForProject(@Param("step") int step, @Param("projectid") String projectid);

    @Modifying
    @Transactional
    @Query("UPDATE MileStones m SET m.step = m.step - 1 WHERE m.step >= :start AND m.step <= :end AND m.projectid = :projectid")
    void incrementStepsForMoveUp(@Param("start") int start, @Param("end") int end, @Param("projectid") String projectid);

    @Modifying
    @Transactional
    @Query("UPDATE MileStones m SET m.step = m.step + 1 WHERE m.step >= :start AND m.step <= :end AND m.projectid = :projectid")
    void decrementStepsForMoveDown(@Param("start") int start, @Param("end") int end, @Param("projectid") String projectid);

    void deleteById(String milestoneid);

}
