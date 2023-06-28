package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/VaccinationCenter")
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add")
    public ResponseEntity<String> addCenter(@RequestBody VaccinationCenter vaccinationCenter){
        try{
            String responce=vaccinationCenterService.addCenter(vaccinationCenter);
            return new ResponseEntity<>(responce, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
