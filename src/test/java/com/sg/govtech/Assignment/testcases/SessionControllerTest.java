package com.sg.govtech.Assignment.testcases;

import com.sg.govtech.Assignment.controller.SessionController;
import com.sg.govtech.Assignment.entity.Session;
import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.service.RestaurantService;
import com.sg.govtech.Assignment.service.SessionService;
import com.sg.govtech.Assignment.service.UserService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SessionController.class)
public class SessionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SessionService sessionService;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void testCreateSession() throws Exception {
        // Mock user
        User mockUser = new User();
        mockUser.setUsername("pratheep");

        // Mock user service response
        Mockito.when(userService.findByUsername("pratheep")).thenReturn(mockUser);

        // Mock session creation
        Session mockSession = new Session();
        mockSession.setId(1);
        mockSession.setSessionName("Lunch KL security4");
        Mockito.when(sessionService.createSession("Lunch KL security4", mockUser)).thenReturn(mockSession);

        // Perform POST request to create session
        mockMvc.perform(post("/api/sessions/create")
                        .param("sessionName", "Lunch KL security4")
                        .param("username", "pratheep")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(1))) // Verify session ID
                .andExpect(jsonPath("$.sessionName", Matchers.is("Lunch KL security4"))); // Verify session name
    }
    @Test
    public void testCreateSession_MissingUsername() throws Exception {
        // Perform POST request without username parameter
        mockMvc.perform(post("/api/sessions/create")
                        .param("sessionName", "Lunch KL security4")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()); // Verify bad request status
    }

    @Test
    public void testCreateSession_InvalidUser() throws Exception {
        // Mock user service to return null
        Mockito.when(userService.findByUsername("invalidUser")).thenReturn(null);

        // Perform POST request with an invalid username
        mockMvc.perform(post("/api/sessions/create")
                        .param("sessionName", "Lunch KL security4")
                        .param("username", "invalidUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound()); // Verify not found status
    }


}
