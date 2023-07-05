package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.AssociateDoctorDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateDoctorWithEmailId;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDto;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<String> associateWithCenter(@RequestBody AssociateDoctorDto associateDoctorDto){
        try{
            String response=doctorService.associateDoctor(associateDoctorDto);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/basedOnAgeAndGender")
    public ResponseEntity<List<DoctorDto>> getAllDoctorsBasedOnAgeAndGender(@RequestParam Integer greaterThenAge, @RequestParam Gender gender) {
        try {
            List<DoctorDto> list = doctorService.getAllDoctorsBasedOnAgeAndGender(greaterThenAge, gender);
            return new ResponseEntity<>(list, HttpStatus.FOUND);
        } catch (Exception re) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/doctorsRatioOfMaleAndFemale")
    public ResponseEntity<String> getDoctorsRatioOfMaleAndFemale() {
        try {
            String ratio = doctorService.getDoctorsRatioOfMaleAndFemale();
            return new ResponseEntity<>(ratio, HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateDoctorDetailsByEmailId")
    public ResponseEntity<String> updateDoctorDetails(@RequestParam String oldEmailId, @RequestBody UpdateDoctorWithEmailId updateDoctorWithEmailId) {
        try {
            String result = doctorService.updateDoctor(oldEmailId,updateDoctorWithEmailId);
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteById")
    public ResponseEntity<String> deleteDoctorById(@RequestParam String emilId) {
        try {
            String result = doctorService.deleteDoctorById(emilId);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (Exception re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/appointmentsGreaterThan10")
    public List<Doctor> appointmentsGreaterThanTen(){
        return doctorService.appointmentsGreaterThanTen();
    }
}
