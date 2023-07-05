package com.example.VaccinationSystem.Exceptions;

public class AppointmentCanNotDelete extends RuntimeException {

    public AppointmentCanNotDelete() {
        super("You Can not delete your Appointment");
    }
}
