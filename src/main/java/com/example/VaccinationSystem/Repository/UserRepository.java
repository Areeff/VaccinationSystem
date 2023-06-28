package com.example.VaccinationSystem.Repository;

import com.example.VaccinationSystem.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
     User findByEmailId(String emailId);
}