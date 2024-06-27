package com.sg.govtech.Assignment.service;

import com.sg.govtech.Assignment.entity.Restaurant;
import com.sg.govtech.Assignment.entity.Session;
import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.repository.RestaurantRepository;
import com.sg.govtech.Assignment.repository.SessionRepository;
import com.sg.govtech.Assignment.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class RestaurantService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestaurantService.class);
    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant submitRestaurant(String name, Session session, User submittedBy) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(name);
        restaurant.setSession(session);
        restaurant.setSubmittedBy(submittedBy);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getRestaurantsBySessionId(Integer sessionId) {
        return restaurantRepository.findBySessionId(sessionId);
    }

    public Restaurant pickRandomRestaurant(Integer sessionId) {
        List<Restaurant> restaurants = restaurantRepository.findBySessionId(sessionId);
        LOGGER.info("restarants>>>"+restaurants);
        if (restaurants.isEmpty()) {
            throw new NoSuchElementException("No restaurants found for session");
        }
        int randomIndex = new Random().nextInt(restaurants.size());
        LOGGER.info("randomIndex>>>"+restaurants.get(randomIndex));
        return restaurants.get(randomIndex);
    }
}