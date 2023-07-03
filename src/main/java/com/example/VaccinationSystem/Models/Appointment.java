package com.example.VaccinationSystem.Models;

import com.example.VaccinationSystem.Enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;

@Entity
@Table(name = "Appointment")
@Data
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Date appointmentDate;

    private LocalTime appointmentTime;


    @Column(name = "Status")
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus appointmentStatus = AppointmentStatus.PENDING;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn
    @JsonIgnore
    private Doctor doctor;


}
