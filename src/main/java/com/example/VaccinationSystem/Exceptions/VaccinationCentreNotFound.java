package com.example.VaccinationSystem.Exceptions;

public class VaccinationCentreNotFound extends RuntimeException{
    public VaccinationCentreNotFound() {
        super("Vaccination center not found");
    }
}
