package com.sg.govtech.Assignment.service;

import com.sg.govtech.Assignment.AssignmentApplication;
import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.exception.UserAlreadyExistsException;
import com.sg.govtech.Assignment.exception.UserNotFoundException;
import com.sg.govtech.Assignment.model.UsersDto;
import com.sg.govtech.Assignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UsersDto registerUser(UsersDto usersDto) {
        if (userRepository.existsByUsername(usersDto.getUsername())) {
            throw new UserAlreadyExistsException("Username is already taken");
        }

        User user = new User();
        user.setUsername(usersDto.getUsername());
        user.setPassword(usersDto.getPassword());
        user.setEmail(usersDto.getEmail());

        User savedUser = userRepository.save(user);

        UsersDto registeredUser = new UsersDto();
        registeredUser.setId(savedUser.getId());
        registeredUser.setUsername(savedUser.getUsername());
        registeredUser.setEmail(savedUser.getEmail());

        return registeredUser;
    }

    public User findByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UserNotFoundException("User not found with username: " + username);
        }
    }
}