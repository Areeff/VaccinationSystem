package com.example.VaccinationSystem.Dtos.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReqDto {
    private int doctorId;
    private int userId;
    private Date appointmentDate;
    private LocalTime appointmentTime;

}
