package com.example.VaccinationSystem.Dtos.RequestDtos;

import lombok.Data;

import java.util.Date;

@Data
public class ChangeAppointmentDateRequestDtos {

    private Integer appointmentId;
    private Integer userId;
    private Date date;
}
