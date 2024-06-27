package com.sg.govtech.Assignment.controller;

import com.sg.govtech.Assignment.AssignmentApplication;
import com.sg.govtech.Assignment.entity.Restaurant;
import com.sg.govtech.Assignment.entity.Session;
import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.service.RestaurantService;
import com.sg.govtech.Assignment.service.SessionService;
import com.sg.govtech.Assignment.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionController.class);
    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<Session> createSession(@RequestParam String sessionName, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(sessionService.createSession(sessionName, user));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Session>> getActiveSessions() {
        return ResponseEntity.ok(sessionService.getActiveSessions());
    }

    @PostMapping("/{sessionId}/end")
    public ResponseEntity<Void> endSession(@PathVariable Integer sessionId) {
        sessionService.endSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{sessionId}/invite")
    public ResponseEntity<Void> inviteUserToSession(@PathVariable Integer sessionId, @RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        sessionService.inviteUserToSession(sessionId, username);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{sessionId}/invited-users")
    public ResponseEntity<List<User>> getInvitedUsers(@PathVariable Integer sessionId) {
        List<User> invitedUsers = sessionService.getInvitedUsers(sessionId);
        return ResponseEntity.ok(invitedUsers);
    }
    @PostMapping("/{sessionId}/restaurants")
    public ResponseEntity<Restaurant> submitRestaurant(@PathVariable Integer sessionId, @RequestParam String restaurantName, @RequestParam String username) {
        Session session = sessionService.getSessionById(sessionId);
        if (!session.getIsActive()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        User user = userService.findByUsername(username);
        return ResponseEntity.ok(restaurantService.submitRestaurant(restaurantName, session, user));
    }

    @GetMapping("/{sessionId}/restaurants")
    public ResponseEntity<List<Restaurant>> getRestaurants(@PathVariable Integer sessionId) {
        return ResponseEntity.ok(restaurantService.getRestaurantsBySessionId(sessionId));
    }

    @GetMapping("/{sessionId}/pick")
    public ResponseEntity<Restaurant> pickRandomRestaurant(@PathVariable Integer sessionId) {
        Session session = sessionService.getSessionById(sessionId);
        LOGGER.info("pickRandomRestaurant session:"+session.getIsActive());
        if (!session.getIsActive()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        LOGGER.info("crossing here...");
        return ResponseEntity.ok(restaurantService.pickRandomRestaurant(sessionId));
    }
}