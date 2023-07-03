package com.example.VaccinationSystem.Tranformers;

import com.example.VaccinationSystem.Dtos.ResponceDto.VaccinationCentreDto;
import com.example.VaccinationSystem.Models.VaccinationCenter;

public class VaccinationCentreTransformer {

    public static VaccinationCentreDto vaccinationCentreToVaccinationCentreDto (VaccinationCenter vaccinationCentre) {
        VaccinationCentreDto vaccinationCentreDto = VaccinationCentreDto.builder()
                .name(vaccinationCentre.getCenterName())
                .address(vaccinationCentre.getAddress())
                .build();
        return vaccinationCentreDto;
    }
}
