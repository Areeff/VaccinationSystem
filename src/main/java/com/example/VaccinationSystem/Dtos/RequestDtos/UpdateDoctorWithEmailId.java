package com.example.VaccinationSystem.Dtos.RequestDtos;

import com.example.VaccinationSystem.Enums.Gender;
import lombok.Data;

@Data
public class UpdateDoctorWithEmailId {

    private String name;
    private Integer age;
    private Gender gender;
}
