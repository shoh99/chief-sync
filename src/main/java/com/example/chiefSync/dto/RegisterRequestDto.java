package com.example.chiefSync.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterRequestDto {

    private String name;
    private String email;
    private String password;
}
