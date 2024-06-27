package com.sg.govtech.Assignment.controller;

import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.model.UsersDto;
import com.sg.govtech.Assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UsersDto> registerUser(@RequestBody UsersDto usersDto) {
        LOGGER.info("registerUser Enter..");
        UsersDto registeredUser = userService.registerUser(usersDto);
        LOGGER.info("registeredUser object:"+registeredUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
    }
}