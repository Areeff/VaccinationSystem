package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.AppointmentReqDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.CancelAppointmentRequestDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.ChangeAppointmentDateRequestDtos;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDtoForCentre;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public String bookAppointment(@RequestBody AppointmentReqDto appointmentReqDto){
        try {
            String response=appointmentService.bookAppointment(appointmentReqDto);
            return response;
        }catch (Exception e){
            return e.getMessage();
        }
    }



    @PutMapping("/changeDate")
    public ResponseEntity<String> changeDateByBookingId(@RequestBody ChangeAppointmentDateRequestDtos changeAppointmentDateRequestDtos){
        try {
            String result = appointmentService.changeDateByBookingId(changeAppointmentDateRequestDtos);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (Exception re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteAppointment")
    public ResponseEntity<String> deleteAppointmentById(@RequestBody CancelAppointmentRequestDto cancelAppointmentRequestDto) {
        try {
            String result = appointmentService.deleteAppointmentById(cancelAppointmentRequestDto);
            return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
