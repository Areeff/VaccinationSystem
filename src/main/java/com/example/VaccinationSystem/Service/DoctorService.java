package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.AssociateDoctorDto;
import com.example.VaccinationSystem.Exceptions.CenterNotFoundException;
import com.example.VaccinationSystem.Exceptions.DoctorAlreadyExistsException;
import com.example.VaccinationSystem.Exceptions.DoctorNotFoundException;
import com.example.VaccinationSystem.Exceptions.EmailIdEmptyException;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Repository.DoctorRepository;
import com.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
    public String addDoctor(Doctor doctor) throws DoctorAlreadyExistsException,EmailIdEmptyException {
        if(doctor.getEmailId()==null){
            throw new EmailIdEmptyException("Email field is empty");
        }
        if(doctorRepository.findByEmailId(doctor.getEmailId())!=null){
            throw new DoctorAlreadyExistsException("Doctor already exists with emailId");
        }
        doctorRepository.save(doctor);
        return "Doctor Added Successfully";

    }

    public String associateDoctor(AssociateDoctorDto associateDoctorDto) throws DoctorNotFoundException, CenterNotFoundException {
        Integer docId=associateDoctorDto.getDoctorId();
        Optional<Doctor> doctorOptional=doctorRepository.findById(docId);
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException("Doctor not found with given id");
        }

        Integer centerId=associateDoctorDto.getCenterId();
        Optional<VaccinationCenter>vaccinationCenterOptional=vaccinationCenterRepository.findById(centerId);
        if(vaccinationCenterOptional.isEmpty()){
            throw new CenterNotFoundException("Center not found with given Id");
        }

        Doctor doctor=doctorOptional.get();
        VaccinationCenter vaccinationCenter=vaccinationCenterOptional.get();
        doctor.setVaccinationCenter(vaccinationCenter);
        vaccinationCenter.getDoctorList().add(doctor);
        vaccinationCenterRepository.save(vaccinationCenter);
        return "Doctor has been associated with center";
    }
}
