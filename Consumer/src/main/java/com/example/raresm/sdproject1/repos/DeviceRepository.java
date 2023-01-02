package com.example.raresm.sdproject1.repos;

import com.example.raresm.sdproject1.model.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends CrudRepository<Device, Integer> {

    @Override
    Iterable<Device> findAllById(Iterable<Integer> id);

    Device findDeviceById(Integer id);

    Device findByBacklog_Id(Integer id);


    @Override
    Iterable<Device> findAll();
}
