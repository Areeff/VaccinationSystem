package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.AssociateDoctorDto;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Service.DoctorService;
import com.example.VaccinationSystem.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping("/add")
    public String addDoctor(@RequestBody Doctor doctor){
        try{
            String responce=doctorService.addDoctor(doctor);
            return responce;
        }catch (Exception e){
            return e.getMessage();
        }
    }

    @PostMapping("/associateWithCenter")
    public ResponseEntity<String> associteDoctor(@RequestBody AssociateDoctorDto associateDoctorDto){
        try{
            String responce=doctorService.associateDoctor(associateDoctorDto);
            return new ResponseEntity<>(responce, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
