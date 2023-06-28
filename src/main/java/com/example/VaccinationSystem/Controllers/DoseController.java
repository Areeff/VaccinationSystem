package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    private DoseService doseService;

    @PostMapping("/giveDose")
    public String giveDose(@RequestParam String doseId,@RequestParam Integer userId){
        return doseService.giveDose(doseId,userId);
    }
}
