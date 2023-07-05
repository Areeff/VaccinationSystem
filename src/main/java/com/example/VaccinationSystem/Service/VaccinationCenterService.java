package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDtoForCentre;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Exceptions.VaccinationCentreNotFound;
import com.example.VaccinationSystem.Exceptions.VaccionationAdressNotFoundException;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import com.example.VaccinationSystem.Tranformers.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public List<String> getAllVaccinationCentres() {
        List<VaccinationCenter> vaccinationCentres = vaccinationCenterRepository.findAll();
        List<String> centresName = new ArrayList<>();
        for(VaccinationCenter vaccinationCentre : vaccinationCentres) {
            centresName.add("name: "+vaccinationCentre.getCenterName()+", Address: "+vaccinationCentre.getAddress());
        }
        return centresName;
    }

    public String deleteVaccinationCentreById(Integer vaccinationCentreId) throws VaccinationCentreNotFound{
        Optional<VaccinationCenter> vaccinationCentreOpt = vaccinationCenterRepository.findById(vaccinationCentreId);
        if(vaccinationCentreOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        VaccinationCenter vaccinationCentre = vaccinationCentreOpt.get();
        vaccinationCenterRepository.delete(vaccinationCentre);
        return vaccinationCentre.getCenterName()+ " Vaccination Centre is deleted Successfully";
    }

    public List<DoctorDtoForCentre> getAllDoctorsByCenterId(Integer centerId) throws VaccinationCentreNotFound{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if(centerOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        List<Doctor> doctors = centerOpt.get().getDoctorList();
        List<DoctorDtoForCentre> doctorList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            doctorList.add(DoctorTransformer.doctorToDoctorDtoForCentre(doctor));
        }
        return doctorList;
    }

    public List<DoctorDtoForCentre> getAllMaleDoctorsByCenterId(Integer centerId) throws VaccinationCentreNotFound{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if(centerOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        List<Doctor> doctors = centerOpt.get().getDoctorList();
        List<DoctorDtoForCentre> doctorList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if(doctor.getGender().equals(Gender.MALE)) {
                doctorList.add(DoctorTransformer.doctorToDoctorDtoForCentre(doctor));
            }
        }
        return doctorList;
    }

    public List<DoctorDtoForCentre> getAllFemaleDoctorsByCenterId(Integer centerId) throws VaccinationCentreNotFound{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if(centerOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        List<Doctor> doctors = centerOpt.get().getDoctorList();
        List<DoctorDtoForCentre> doctorList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if(doctor.getGender().equals(Gender.FEMALE)) {
                doctorList.add(DoctorTransformer.doctorToDoctorDtoForCentre(doctor));
            }
        }
        return doctorList;
    }

    public List<DoctorDtoForCentre> getAllDoctorsBasedOnAgeAndGenderByCenterId(Integer centerId, Integer greaterThenAge, Gender gender) throws VaccinationCentreNotFound{
        Optional<VaccinationCenter> centerOpt = vaccinationCenterRepository.findById(centerId);
        if(centerOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        List<Doctor> doctors = centerOpt.get().getDoctorList();
        List<DoctorDtoForCentre> doctorList = new ArrayList<>();
        for (Doctor doctor : doctors) {
            if(doctor.getAge() > greaterThenAge && doctor.getGender().equals(gender)) {
                doctorList.add(DoctorTransformer.doctorToDoctorDtoForCentre(doctor));
            }
        }
        return doctorList;
    }
}
