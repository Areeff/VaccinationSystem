package com.example.VaccinationSystem.Service;

import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateEmailIdDto;
import com.example.VaccinationSystem.Exceptions.UserNotFoundException;
import com.example.VaccinationSystem.Models.Dose;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public User addUser(User user) {
        user=userRepository.save(user);
        return user;
    }

    public User getUserById(Integer userId) throws UserNotFoundException {
        Optional<User> userOpt = userRepository.findById(userId);
        if(userOpt.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return userOpt.get();
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

    public User getByEmailId(String emailId) throws UserNotFoundException {
        Optional<User>userOptional=userRepository.findByEmailId(emailId);
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");
        }
        return userOptional.get();
    }
}
