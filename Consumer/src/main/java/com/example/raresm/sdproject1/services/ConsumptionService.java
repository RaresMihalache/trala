package com.example.raresm.sdproject1.services;

import com.example.raresm.sdproject1.exceptions.DeviceNotFoundException;
import com.example.raresm.sdproject1.model.Backlog;
import com.example.raresm.sdproject1.model.Consumption;
import com.example.raresm.sdproject1.model.Device;
import com.example.raresm.sdproject1.repos.BacklogRepository;
import com.example.raresm.sdproject1.repos.ConsumptionRepository;
import com.example.raresm.sdproject1.repos.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConsumptionService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    private ConsumptionRepository consumptionRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    public Consumption addConsumption(Integer deviceId, Consumption consumption) {

        try {
            // all Consumptions to be added to a specific device, device != null => backlog exists
            Backlog backlog = backlogRepository.findByDevice_Id(deviceId);
            // set the backlog to Consumption
            consumption.setBacklog(backlog);

            return consumptionRepository.save(consumption);
        } catch (Exception e){
            throw new DeviceNotFoundException("Device Not Found");
        }
    }

    public List<Consumption> findBacklogById(Integer backlog_id) {
        Device device = deviceRepository.findByBacklog_Id(backlog_id);

        if(device == null){
            throw new DeviceNotFoundException("Device with ID: '" + backlog_id + "' does not exist");
        }

        return consumptionRepository.findByBacklog_Id(backlog_id);
    }

    public Optional<Consumption> findConsumptionById(Integer backlogId, Integer consumptionId){

        // make sure we are searching on an existing backlog (by id)
        Optional<Backlog> backlog = backlogRepository.findById(backlogId);
        if(!backlog.isPresent()){
            throw new DeviceNotFoundException("Device with ID: '" + backlogId + "' does not exist");
        }

        // make sure that our consumption exists (by id)
        Optional<Consumption> consumption = consumptionRepository.findById(consumptionId);
        if(!consumption.isPresent()){
            throw new DeviceNotFoundException("Consumption with ID: '" + consumptionId + "' does not exist");
        }

        // make sure that the backlog id in the path corresponds to the right consumption
        if(!consumption.get().getBacklog().getId().equals(backlogId)){
            throw new DeviceNotFoundException("Consumption with ID: '" + consumptionId + "' does not exist in device with ID: '" + backlogId + "'");
        }

        return consumption;
    }

    public Consumption updateConsumptionById(Consumption updatedConsumption, Integer backlog_id, Integer consumption_id) {
        // update Consumption

        // find existing Consumption
        Optional<Consumption> consumption = findConsumptionById(backlog_id, consumption_id);
        // replace it with updated Consumption
        if(consumption.isPresent())
            // save update
            return consumptionRepository.save(updatedConsumption);
        return null;
    }

    public void deleteConsumptionById(Integer backlog_id, Integer consumption_id) {
        Optional<Consumption> consumption = findConsumptionById(backlog_id, consumption_id);
        if (consumption.isPresent()) {
//            Backlog backlog = consumption.get().getBacklog();
//            List<Consumption> consumptions = backlog.getDeviceConsumptions();
//            consumptions.remove(consumption.get());
//            backlogRepository.save(backlog);

            consumptionRepository.delete(consumption.get());

        }
    }
}
