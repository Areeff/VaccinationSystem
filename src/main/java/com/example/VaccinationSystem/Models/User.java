package com.example.VaccinationSystem.Models;

import com.example.VaccinationSystem.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Generated;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="User")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_name")
    private String name;

    private int age;

    @Column(unique = true)
    private String emailId;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(unique = true)
    private String mobileNo;

    @JsonIgnore
    @OneToMany(mappedBy ="user",cascade = CascadeType.ALL)
    private List<Appointment> appointmentList=new ArrayList<>();

    @JsonIgnore
    @OneToOne(mappedBy = "user",cascade =CascadeType.ALL)
    private Dose dose;


}

