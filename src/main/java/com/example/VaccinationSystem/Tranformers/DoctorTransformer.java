package com.example.VaccinationSystem.Tranformers;

import com.example.VaccinationSystem.Dtos.RequestDtos.AddDoctorDto;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDto;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDtoForCentre;
import com.example.VaccinationSystem.Models.Doctor;

public class DoctorTransformer {

    public static DoctorDto doctorToDoctorDto(Doctor doctor) {
        DoctorDto doctorDto = DoctorDto.builder()
                .name(doctor.getName())
                .gender(doctor.getGender())
                .age(doctor.getAge())
                .vaccinationCentre(VaccinationCentreTransformer.vaccinationCentreToVaccinationCentreDto(doctor.getVaccinationCenter()))
                .build();
        return doctorDto;
    }

    public static DoctorDtoForCentre doctorToDoctorDtoForCentre(Doctor doctor) {
        DoctorDtoForCentre doctorDto = DoctorDtoForCentre.builder()
                .name(doctor.getName())
                .gender(doctor.getGender())
                .age(doctor.getAge())
                .build();
        return doctorDto;
    }

    public static Doctor doctorDtoToDoctor(AddDoctorDto doctorDto) {
        Doctor doctor = Doctor.builder()
                .name(doctorDto.getName())
                .age(doctorDto.getAge())
                .emailId(doctorDto.getEmailId())
                .gender(doctorDto.getGender())
                .build();
        return doctor;
    }
}
