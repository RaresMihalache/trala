package com.example.raresm.sdproject1.controllers;

import com.example.raresm.sdproject1.model.Consumption;
import com.example.raresm.sdproject1.services.ConsumptionService;
import com.example.raresm.sdproject1.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    @Autowired
    private ConsumptionService consumptionService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addConsumptionToBacklog(@Valid @RequestBody Consumption consumption,
                                                     BindingResult result, @PathVariable Integer backlog_id){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Consumption consumption1 = consumptionService.addConsumption(backlog_id, consumption);

        return new ResponseEntity<Consumption>(consumption1, HttpStatus.CREATED);
    }

    @GetMapping("/{backlog_id}")
    public ResponseEntity<List<Consumption>> getDeviceBacklog(@PathVariable Integer backlog_id){
        return new ResponseEntity<List<Consumption>>(consumptionService.findBacklogById(backlog_id), HttpStatus.OK);
    }

    @GetMapping("/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> getConsumption(@PathVariable Integer backlog_id, @PathVariable Integer consumption_id){
        Optional<Consumption> consumption = consumptionService.findConsumptionById(backlog_id, consumption_id);

        return new ResponseEntity<Optional<Consumption>>(consumption, HttpStatus.OK);
    }

    @PatchMapping("/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> updateConsumption(@Valid @RequestBody Consumption consumption, BindingResult result,
                                               @PathVariable Integer backlog_id, @PathVariable Integer consumption_id){

        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null){
            return errorMap;
        }

        Consumption updatedConsumption = consumptionService.updateConsumptionById(consumption, backlog_id, consumption_id);

        return new ResponseEntity<Consumption>(updatedConsumption, HttpStatus.OK);
    }

    @DeleteMapping("/{backlog_id}/{consumption_id}")
    public ResponseEntity<?> deleteConsumption(@PathVariable Integer backlog_id, @PathVariable Integer consumption_id){
        consumptionService.deleteConsumptionById(backlog_id, consumption_id);

        return new ResponseEntity<String>("Consumption '" + consumption_id +"' was deleted successfully", HttpStatus.OK);
    }
}
