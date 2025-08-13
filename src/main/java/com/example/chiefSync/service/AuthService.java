package com.example.chiefSync.service;


import com.example.chiefSync.dto.LoginRequestDto;
import com.example.chiefSync.dto.LoginResponseDto;
import com.example.chiefSync.dto.RegisterRequestDto;
import com.example.chiefSync.dto.RegisterResponseDto;
import com.example.chiefSync.model.Role;
import com.example.chiefSync.model.User;
import com.example.chiefSync.repostiroy.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public RegisterResponseDto userRegister(RegisterRequestDto userRequestDto) {
         if(userRepository.findUserByEmail(userRequestDto.getEmail()).isPresent()) {
             throw new IllegalArgumentException("Email already in use.");
        }

         User user = new User();
         user.setName(userRequestDto.getName());
         user.setEmail(userRequestDto.getEmail());
         user.setRole(Role.ROLE_STAFF);
         user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));

         User newUser = userRepository.save(user);

         RegisterResponseDto response = new RegisterResponseDto();
         response.setEmail(newUser.getEmail());
         response.setName(newUser.getName());
         response.setRole(newUser.getRole());

         return response;
    }

    public LoginResponseDto login(LoginRequestDto request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password"));
        String jwtToken = jwtService.generateToken(user);

        return new LoginResponseDto(jwtToken);
    }


}
