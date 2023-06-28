package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.AppointmentReqDto;
import com.example.VaccinationSystem.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/book")
    public String bookAppointment(@RequestBody AppointmentReqDto appointmentReqDto){
        try {
            String responce=appointmentService.bookAppointment(appointmentReqDto);
            return responce;
        }catch (Exception e){
            return e.getMessage();
        }
    }
}
