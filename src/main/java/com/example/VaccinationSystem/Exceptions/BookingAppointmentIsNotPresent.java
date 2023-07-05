package com.example.VaccinationSystem.Exceptions;

public class BookingAppointmentIsNotPresent extends RuntimeException{

    public BookingAppointmentIsNotPresent() {

        super("Booking dose not exists");
    }
}
