package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDtoForCentre;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Service.VaccinationCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/VaccinationCenter")
public class VaccinationCenterController {

    @Autowired
    private VaccinationCenterService vaccinationCenterService;

    @PostMapping("/add")
    public ResponseEntity<String> addCenter(@RequestBody VaccinationCenter vaccinationCenter){
        try{
            String response=vaccinationCenterService.addCenter(vaccinationCenter);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allNameAndAddress")
    public ResponseEntity<List<String>> getAllVaccinationCentres() {
        try {
            List<String> result = vaccinationCenterService.getAllVaccinationCentres();
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteVaccinationCentreById(@RequestParam Integer vaccinationCentreId) {
        try {
            String result = vaccinationCenterService.deleteVaccinationCentreById(vaccinationCentreId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (Exception re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCentre>> getAllDoctorsByCenterId(@PathVariable Integer centerId) {
        try {
            List<DoctorDtoForCentre> list = vaccinationCenterService.getAllDoctorsByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allMaleDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCentre>> getAllMaleDoctorsByCenterId(@PathVariable Integer centerId) {
        try {
            List<DoctorDtoForCentre> list = vaccinationCenterService.getAllMaleDoctorsByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allFemaleDoctors/{centerId}")
    public ResponseEntity<List<DoctorDtoForCentre>> getAllFemaleDoctorsByCenterId(@PathVariable Integer centerId) {
        try {
            List<DoctorDtoForCentre> list = vaccinationCenterService.getAllFemaleDoctorsByCenterId(centerId);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/doctorsBasedOnAgeAndGender/{centerId}")
    public ResponseEntity<List<DoctorDtoForCentre>> getAllDoctorsBasedOnAgeAndGenderByCenterId(@PathVariable Integer centerId, @RequestParam Integer greaterThenAge, @RequestParam Gender gender) {
        try {
            List<DoctorDtoForCentre> list = vaccinationCenterService.getAllDoctorsBasedOnAgeAndGenderByCenterId(centerId, greaterThenAge, gender);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
