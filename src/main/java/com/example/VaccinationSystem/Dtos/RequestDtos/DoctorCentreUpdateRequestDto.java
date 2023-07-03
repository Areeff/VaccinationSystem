package com.example.VaccinationSystem.Dtos.RequestDtos;

import lombok.Data;

@Data
public class DoctorCentreUpdateRequestDto {

    private Integer doctorId;
    private Integer newCentreId;
}
