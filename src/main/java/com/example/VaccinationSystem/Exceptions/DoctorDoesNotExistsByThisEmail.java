package com.example.VaccinationSystem.Exceptions;

public class DoctorDoesNotExistsByThisEmail extends Exception {
    public DoctorDoesNotExistsByThisEmail() {
        super("Doctor does not exits with given emailId");
    }
}
