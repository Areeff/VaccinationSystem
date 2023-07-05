package com.example.VaccinationSystem.Dtos.RequestDtos;

import lombok.Data;

@Data
public class CancelAppointmentRequestDto {

    private Integer appointmentId;
    private Integer userId;
}
