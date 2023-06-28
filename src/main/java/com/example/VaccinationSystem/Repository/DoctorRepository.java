package com.example.VaccinationSystem.Repository;

import com.example.VaccinationSystem.Models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Integer> {
     public Doctor findByEmailId(String emailId);
}
