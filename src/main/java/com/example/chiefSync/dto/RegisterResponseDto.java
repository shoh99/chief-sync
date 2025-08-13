package com.example.chiefSync.dto;

import com.example.chiefSync.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RegisterResponseDto {
    private String name;
    private String email;
    private Role role;
}
