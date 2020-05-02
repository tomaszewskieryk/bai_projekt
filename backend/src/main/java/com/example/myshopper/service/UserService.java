package com.example.myshopper.service;

import com.example.myshopper.model.User;
import com.example.myshopper.repository.UserRepository;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, FridgeStateService fridgeStateService, UserValidator userValidator) {
        this.userRepository = userRepository;
        this.fridgeStateService = fridgeStateService;
        this.userValidator = userValidator;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
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
}
