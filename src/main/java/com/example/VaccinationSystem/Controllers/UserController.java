package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Dtos.RequestDtos.UpdateEmailIdDto;
import com.example.VaccinationSystem.Models.User;
import com.example.VaccinationSystem.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("/getVaccinationDate")
    public Date getVaccinationDate(@RequestParam Integer userId){
        return userService.getVaccionationDate(userId);
    }

    @PutMapping("/updateEmailId")
    public String updateEmailId(@RequestBody UpdateEmailIdDto updateEmailIdDto){
        return userService.updateEmailId(updateEmailIdDto);
    }

    @GetMapping("/getByEmail/{emailId}")
    public User getByEmailId(@PathVariable String emailId){
        return userService.getByEmailId(emailId);
    }
}
