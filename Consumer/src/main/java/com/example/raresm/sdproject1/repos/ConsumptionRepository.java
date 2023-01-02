package com.example.raresm.sdproject1.repos;

import com.example.raresm.sdproject1.model.Consumption;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConsumptionRepository extends CrudRepository<Consumption, Integer> {
    List<Consumption> findByBacklog_Id(Integer backlogId);

    Optional<Consumption> findById(Integer consumptionId);


    void deleteByBacklog_IdAndId(Integer backlogId, Integer consumptionId);
}
