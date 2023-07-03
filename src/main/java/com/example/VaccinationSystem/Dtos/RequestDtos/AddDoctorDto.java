package com.example.VaccinationSystem.Dtos.RequestDtos;

import com.example.VaccinationSystem.Enums.Gender;
import lombok.Data;

@Data
public class AddDoctorDto {
    private String name;
    private Integer age;
    private Gender gender;
    private String emailId;
}
