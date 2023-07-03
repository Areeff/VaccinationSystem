package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.AssociateDoctorDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.DoctorCentreUpdateRequestDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateDoctorWithEmailId;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDto;
import com.example.VaccinationSystem.Enums.AppointmentStatus;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Exceptions.*;
import com.example.VaccinationSystem.Models.Appointment;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Repository.DoctorRepository;
import com.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import com.example.VaccinationSystem.Tranformers.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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

    public String changeAssociateHospital(DoctorCentreUpdateRequestDto doctorCentreUpdateRequestDto) throws DoctorNotFoundException, VaccinationCentreNotFound, YouHavePendingAppointmentOnCentre{
        Integer doctorId = doctorCentreUpdateRequestDto.getDoctorId();
        Integer newCentreId = doctorCentreUpdateRequestDto.getNewCentreId();

        Optional<Doctor> doctorOpt = doctorRepository.findById(doctorId);
        Optional<VaccinationCenter> vaccinationCentreOpt = vaccinationCenterRepository.findById(newCentreId);
        if(doctorOpt.isEmpty()) {
            throw new DoctorNotFoundException("Doctor not found");
        }
        if(vaccinationCentreOpt.isEmpty()) {
            throw new VaccinationCentreNotFound();
        }
        Doctor doctor = doctorOpt.get();
        Boolean isNotPendingApp = pendingApp(doctor.getAppointmentList());
        if(!isNotPendingApp) {
            throw new YouHavePendingAppointmentOnCentre();
        }
        VaccinationCenter vaccinationCentre = vaccinationCentreOpt.get();

        doctor.setVaccinationCenter(vaccinationCentre);

        vaccinationCentre.getDoctorList().add(doctor);
        vaccinationCenterRepository.save(vaccinationCentre);
        return "Doctor "+doctor.getName()+" has been Associated to: "+vaccinationCentre.getCenterName();
    }

    private Boolean pendingApp(List<Appointment> appointmentList) {
        for(Appointment appointment : appointmentList) {
            if(appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
                return false;
            }
        }
        return true;
    }

    public List<DoctorDto> doctorBasedOnAppointment(Integer appointmentCount) {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorDto> doctors = new ArrayList<>();
        for(Doctor doctor : doctorList) {
            List<Appointment> appointments = doctor.getAppointmentList();
            int appCount = 0;
            for(Appointment appointment : appointments) {
                if(appointment.getAppointmentStatus().equals(AppointmentStatus.PENDING)) {
                    appCount++;
                }
            }
            if(appCount >= appointmentCount) {
                doctors.add(DoctorTransformer.doctorToDoctorDto(doctor));
            }
        }

        return doctors;
    }

    public List<DoctorDto> getAllDoctorsBasedOnAgeAndGenderByCenterId(Integer greaterThenAge, Gender gender) {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorDto> doctors = new ArrayList<>();
        for (Doctor doctor : doctorList) {
            if(doctor.getAge() > greaterThenAge && doctor.getGender().equals(gender)) {
                doctors.add(DoctorTransformer.doctorToDoctorDto(doctor));
            }
        }
        return doctors;
    }

    public String getDoctorsRatioOfMaleAndFemale() {
        List<Doctor> doctorList = doctorRepository.findAll();
        int male = 0;
        int female = 0;

        for(Doctor doctor : doctorList) {
            if(doctor.getGender().equals(Gender.MALE)) {
                male++;
            } else if(doctor.getGender().equals(Gender.FEMALE)) {
                female++;
            }
        }

        int GCD = 1;
        for(int i = 1; i <= male && i <= female; i++){
            if(male%i==0 && female%i==0) {
                GCD = i;
            }
        }
        int maleR = male/GCD;
        int femaleR = female/GCD;
        return "Male Doctor: "+male+"\n"+
                "Female Doctor: "+female+"\n"+
                "Ratio Of Doctors(Male:Female): "+Integer.toString(maleR)+" : "+Integer.toString(femaleR);
    }

    public String deleteDoctorById(String emailId) throws DoctorDoesNotExistsByThisEmail{
        Doctor doctor = doctorRepository.findByEmailId(emailId);
        if(doctor == null) {
            throw new DoctorDoesNotExistsByThisEmail();
        }

        doctorRepository.delete(doctor);
        return doctor.getName()+" has been removed from Data";
    }

    public String updateDoctor(String oldEmailId, UpdateDoctorWithEmailId updateDoctorWithEmailId) throws DoctorDoesNotExistsByThisEmail{
        Doctor doctor = doctorRepository.findByEmailId(oldEmailId);
        if(doctor == null) {
            throw new DoctorDoesNotExistsByThisEmail();
        }
        doctor.setName(updateDoctorWithEmailId.getName());
        doctor.setAge(updateDoctorWithEmailId.getAge());
        doctor.setGender(updateDoctorWithEmailId.getGender());
        doctorRepository.save(doctor);
        return "Doctor "+doctor.getName()+" has been updated successfully";
    }
}
