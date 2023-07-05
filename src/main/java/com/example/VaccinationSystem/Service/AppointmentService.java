package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.AppointmentReqDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.CancelAppointmentRequestDto;
import com.example.VaccinationSystem.Dtos.RequestDtos.ChangeAppointmentDateRequestDtos;
import com.example.VaccinationSystem.Dtos.ResponceDto.DoctorDtoForCentre;
import com.example.VaccinationSystem.Enums.Gender;
import com.example.VaccinationSystem.Exceptions.*;
import com.example.VaccinationSystem.Models.Appointment;
import com.example.VaccinationSystem.Models.Doctor;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Models.VaccinationCenter;
import com.example.VaccinationSystem.Repository.AppointmentRepository;
import com.example.VaccinationSystem.Repository.DoctorRepository;
import com.example.VaccinationSystem.Repository.UserRepository;
import com.example.VaccinationSystem.Repository.VaccinationCenterRepository;
import com.example.VaccinationSystem.Tranformers.DoctorTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private VaccinationCenterRepository vaccinationCenterRepository;
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

    public String changeDateByBookingId(ChangeAppointmentDateRequestDtos changeAppointmentDateRequestDtos) throws BookingAppointmentIsNotPresent, UserNotFoundException, UserDoNotHaveAppointmentId{
        Integer appointmentId = changeAppointmentDateRequestDtos.getAppointmentId();
        Integer userId = changeAppointmentDateRequestDtos.getUserId();
        Date date = changeAppointmentDateRequestDtos.getDate();
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isEmpty()) {
            throw new BookingAppointmentIsNotPresent();
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        User user = userOpt.get();
        Appointment appointment = appointmentOpt.get();
        if(!appointment.getUser().equals(user)) {
            throw new UserDoNotHaveAppointmentId();
        }
        appointment.setAppointmentDate(date);
        appointmentRepository.save(appointment);
        return "Your new appointment is "+date;
    }

    public String deleteAppointmentById(CancelAppointmentRequestDto cancelAppointmentRequestDto) throws BookingAppointmentIsNotPresent, UserDoNotHaveAppointmentId, UserNotFoundException{
        Integer appointmentId = cancelAppointmentRequestDto.getAppointmentId();
        Integer userId = cancelAppointmentRequestDto.getUserId();
        Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
        if(appointmentOpt.isEmpty()) {
            throw new BookingAppointmentIsNotPresent();
        }
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        if(!appointmentOpt.get().getUser().equals(userOpt.get())) {
            throw new UserDoNotHaveAppointmentId();
        }
        Appointment appointment = appointmentOpt.get();
        Doctor doctor = appointment.getDoctor();
        User user = appointment.getUser();
        doctor.getAppointmentList().remove(appointment);
        user.getAppointmentList().remove(appointment);
        appointmentRepository.delete(appointment);

        return "Your appointmentId: "+appointmentId+" has been Deleted successfully";
    }




}
