package com.example.VaccinationSystem.Dtos.RequestDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmailIdDto {
    private int userId;
    private String newEmailId;
}
