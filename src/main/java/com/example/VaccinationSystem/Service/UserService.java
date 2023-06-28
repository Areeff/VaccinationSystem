package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateEmailIdDto;
import com.example.VaccinationSystem.Models.Dose;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User addUser(User user) {
        user=userRepository.save(user);
        return user;
    }

    public Date getVaccionationDate(Integer userId) {
        User user=userRepository.findById(userId).get();
        Dose dose=user.getDose();
        return dose.getVaccinationDate();
    }

    public String updateEmailId(UpdateEmailIdDto updateEmailIdDto) {
        int userId=updateEmailIdDto.getUserId();
        User user=userRepository.findById(userId).get();
        user.setEmailId(updateEmailIdDto.getNewEmailId());
        userRepository.save(user);
        return "Email updated successfully";
    }

    public User getByEmailId(String emailId) {
        User user=userRepository.findByEmailId(emailId);
        return user;
    }
}
