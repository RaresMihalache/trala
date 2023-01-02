package com.example.raresm.sdproject1.repos;

import com.example.raresm.sdproject1.model.Backlog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepository extends CrudRepository<Backlog, Integer> {
    Backlog findByDevice_Id(Integer deviceId);
}
