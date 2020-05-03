package com.example.myshopper.validator;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.model.User;
import com.example.myshopper.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserValidator {

    UserRepository userRepository;

    @Autowired
    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validate(User user) {
        validatePassword(user.getPassword());
        validateNickname(user.getNickname());
        validateEmail(user.getEmail());
    }

    private void validatePassword(String password) {
        if (password.length() < 6) {
            throw new InputException("Your password is too short!");
        }
    }

    private void validateNickname(String nickname) {
        if (userRepository.getUserByNickname(nickname).isPresent()) {
            throw new InputException("User with that nickname already exists!");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.getUserByEmail(email).isPresent()) {
            throw new InputException("User with that email already exists!");
        }
    }
}
