package com.example.chiefSync.controller;

import com.example.chiefSync.dto.LoginRequestDto;
import com.example.chiefSync.dto.LoginResponseDto;
import com.example.chiefSync.dto.RegisterRequestDto;
import com.example.chiefSync.dto.RegisterResponseDto;
import com.example.chiefSync.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> userRegister(@RequestBody RegisterRequestDto requestDto) {
        RegisterResponseDto response = authService.userRegister(requestDto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> userLogin(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = authService.login(requestDto);
        return ResponseEntity.ok(responseDto);
    }
}
