package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Exceptions.VaccionationAdressNotFoundException;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VaccinationCenterService {

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
    public String addCenter(VaccinationCenter vaccinationCenter) throws VaccionationAdressNotFoundException {
        if(vaccinationCenter.getAddress()==null){
            throw new VaccionationAdressNotFoundException("Vaccination Address is Empty");
        }
        vaccinationCenterRepository.save(vaccinationCenter);
        return "center added successfully";
    }
}
