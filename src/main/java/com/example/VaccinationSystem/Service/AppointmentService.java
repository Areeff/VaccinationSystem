package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.AppointmentReqDto;
import com.example.VaccinationSystem.Exceptions.DoctorNotFoundException;
import com.example.VaccinationSystem.Exceptions.UserNotFoundException;
import com.example.VaccinationSystem.Models.Appointment;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Repository.AppointmentRepository;
import com.example.VaccinationSystem.Repository.DoctorRepository;
import com.example.VaccinationSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender javaMailSender;
    public String bookAppointment(AppointmentReqDto appointmentReqDto) throws DoctorNotFoundException, UserNotFoundException {
        int docId=appointmentReqDto.getDoctorId();
        Optional<Doctor>doctorOptional=doctorRepository.findById(docId);
        if(doctorOptional.isEmpty()){
            throw new DoctorNotFoundException("doctor not found");
        }

        int userId=appointmentReqDto.getUserId();
        Optional<User>userOptional=userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("user not found");
        }
        Doctor doctor=doctorOptional.get();
        User user=userOptional.get();


        Appointment appointment=new Appointment();
        appointment.setAppointmentDate(appointmentReqDto.getAppointmentDate());
        appointment.setAppointmentTime(appointmentReqDto.getAppointmentTime());
        appointment.setDoctor(doctor);
        appointment.setUser(user);

        appointment=appointmentRepository.save(appointment);

        doctor.getAppointmentList().add(appointment);
        user.getAppointmentList().add(appointment);

        doctorRepository.save(doctor);
        userRepository.save(user);

        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();

        String Body="Hi !"+user.getName()+"\n"+
                "You have successfully booked an appointment on"+appointment.getAppointmentDate()+"at"+appointment.getAppointmentTime()+"\n"+
                "Your doctor is"+doctor.getName()+"\n"+
                "Please reach at"+doctor.getVaccinationCenter().getAddress()+"\n"+
                "Mask is Mandatory";
        simpleMailMessage.setFrom("areeffpathan786@gmail.com");
        simpleMailMessage.setTo(user.getEmailId());
        simpleMailMessage.setSubject("Appointment confirmed");
        simpleMailMessage.setText(Body);
        javaMailSender.send(simpleMailMessage);
        return "appointment booked successfully";

    }
}
