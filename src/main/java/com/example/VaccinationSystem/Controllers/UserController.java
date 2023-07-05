package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateEmailIdDto;
import com.example.VaccinationSystem.Exceptions.UserNotFoundException;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/add")
    public User addUser(@RequestBody User user){

        return userService.addUser(user);
    }


    @GetMapping("/getById/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user.getName(), HttpStatus.FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/getVaccinationDate")
    public Date getVaccinationDate(@RequestParam Integer userId){

        return userService.getVaccionationDate(userId);
    }

    @PutMapping("/updateEmailId")
    public String updateEmailId(@RequestBody UpdateEmailIdDto updateEmailIdDto){
        return userService.updateEmailId(updateEmailIdDto);
    }

    @GetMapping("/getByEmail/{emailId}")
    public ResponseEntity<User> getByEmailId(@PathVariable String emailId){

        User user;
        try {
            user=userService.getByEmailId(emailId);
            return new ResponseEntity<>(user,HttpStatus.FOUND);
        }catch (UserNotFoundException e){
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }

    }
}
