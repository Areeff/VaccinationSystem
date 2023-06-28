package com.example.VaccinationSystem.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "Dose")
@Data
public class Dose {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true)
    private String doseId;

    @CreationTimestamp
    private Date VaccinationDate;

    @JsonIgnore
    @OneToOne
    @JoinColumn
    private User user;

}
