package com.example.VaccinationSystem.Controllers;

import com.example.VaccinationSystem.Service.DoseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dose")
public class DoseController {

    @Autowired
    private DoseService doseService;

    @PostMapping("/giveDose")
    public String giveDose(@RequestParam String doseId,@RequestParam Integer userId){
        return doseService.giveDose(doseId,userId);
    }

    @GetMapping("/totalDoseGiven")
    public ResponseEntity<String> countOfAllGivenDoses() {
        try {
            Integer result = doseService.countOfAllGivenDoses();
            return new ResponseEntity<>(Integer.toString(result), HttpStatus.CREATED);
        } catch (Exception re) {
            return new ResponseEntity<>(re.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
