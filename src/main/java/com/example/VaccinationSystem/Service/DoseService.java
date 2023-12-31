package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Models.Dose;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Repository.DoseRepository;
import com.example.VaccinationSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoseService {

    @Autowired
    private DoseRepository doseRepository;

    @Autowired
    private UserRepository userRepository;
    public String giveDose(String doseId, Integer userId) {
        User user=userRepository.findById(userId).get();

        Dose dose=new Dose();
        dose.setDoseId(doseId);
        dose.setUser(user);
        user.setDose(dose);
        userRepository.save(user);
        return "Dose given to user sucessfully";
    }

    public Integer countOfAllGivenDoses() {
        return doseRepository.findAll().size();
    }
}
