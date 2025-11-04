package com.mitrian.infobezlab.services.impl;

import com.mitrian.infobezlab.data.entities.User;
import com.mitrian.infobezlab.dto.UserRegisterResponseDTO;
import com.mitrian.infobezlab.exceptions.UserAbsenceException;
import com.mitrian.infobezlab.exceptions.UserCredentialsException;
import com.mitrian.infobezlab.exceptions.UserValidateException;
import com.mitrian.infobezlab.repositories.UserRepository;
import com.mitrian.infobezlab.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }
    @Override
    public UserRegisterResponseDTO register(String username, String password) {
        if (password == null || password.isBlank()) {
            throw new UserValidateException("Password cannot be empty");
        }

        if (username == null || username.isBlank()) {
            throw new UserValidateException("Username cannot be empty");
        }

        if (userRepository.findByUsername(username).isPresent()) {
            throw new UserAbsenceException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));


        return new UserRegisterResponseDTO(
                userRepository.save(user).getUsername()
        );
    }

    @Override
    public String login(String username, String password) {
        if (!checkCredentials(username, password)){
            throw new UserCredentialsException("Wrong credentials");
        }

        return userRepository.findByUsername(username)
                .map(jwtService::generateToken)
                .orElseThrow(() -> new UsernameNotFoundException("Username or password is incorrect"));

    }

    public boolean checkCredentials(String username, String password) {
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(password,user.getPassword()))
                .orElse(false);
    }
}
