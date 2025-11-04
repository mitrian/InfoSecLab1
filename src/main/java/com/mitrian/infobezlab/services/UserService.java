package com.mitrian.infobezlab.services;

import com.mitrian.infobezlab.dto.UserRegisterResponseDTO;
import com.mitrian.infobezlab.dto.UserRequestDTO;

public interface UserService {
    UserRegisterResponseDTO register(String username, String password);
    String login(String username, String password);
}
