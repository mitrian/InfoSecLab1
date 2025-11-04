package com.mitrian.infobezlab.controllers;

import com.mitrian.infobezlab.dto.UserRequestDTO;
import com.mitrian.infobezlab.services.UserService;
import com.mitrian.infobezlab.services.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequestDTO userRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(String.format("You have successfully registered, your username: %s",
                        userService.register(
                                userRequestDTO.username(),
                                userRequestDTO.password()).username()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody UserRequestDTO userRequestDTO) {
        String token=userService.login(userRequestDTO.username(), userRequestDTO.password());
        return ResponseEntity.ok(token);
    }
}
