package com.example.myshopper.service;

import com.example.myshopper.exception.InputException;
import com.example.myshopper.model.User;
import com.example.myshopper.repository.UserRepository;
import com.example.myshopper.security.LoginRequest;
import com.example.myshopper.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;
    private final FridgeStateService fridgeStateService;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserService(UserRepository userRepository, FridgeStateService fridgeStateService, UserValidator userValidator, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.fridgeStateService = fridgeStateService;
        this.userValidator = userValidator;
        this.passwordEncoder = passwordEncoder;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email)
                .orElseThrow(() -> new InputException("Could not find user with email=" + email));
    }

    public User getUserByID(int userID) {
        return userRepository.getUserByID(userID);
    }

    public void createUser(User user) {
        userValidator.validate(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        int userID = userRepository.createUser(user);
        log.info("Created user with id = " + userID);

        fridgeStateService.createFridgeStateForNewUser(userID);
    }

    public User getExampleUser() {
        User userMati = getUserByEmail("mati@test.com");
        userMati.getFridgeStateList().add(fridgeStateService.getFridgeStateByID(1));
        return userMati;
    }

    public User getSimpleCurrentUser(String email) {
        return getUserByEmail(email);
    }

    public int login(LoginRequest credentials) {
        User dbUser = getUserByEmail(credentials.getUsername());
        if (passwordEncoder.matches(credentials.getPassword(), dbUser.getPassword())) {
            return dbUser.getUserID();
        } else {
            throw new InputException("Incorrect password");
        }
    }
}
