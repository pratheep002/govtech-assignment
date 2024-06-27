package com.sg.govtech.Assignment.service;

import com.sg.govtech.Assignment.entity.Session;
import com.sg.govtech.Assignment.entity.SessionInvitation;
import com.sg.govtech.Assignment.entity.User;
import com.sg.govtech.Assignment.exception.ResourceNotFoundException;
import com.sg.govtech.Assignment.repository.SessionInvitationRepository;
import com.sg.govtech.Assignment.repository.SessionRepository;
import com.sg.govtech.Assignment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SessionService {
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SessionInvitationRepository sessionInvitationRepository;
    public Session createSession(String sessionName, User initiatedBy) {
        Session session = new Session();
        session.setSessionName(sessionName);
        session.setInitiatedBy(initiatedBy);
        return sessionRepository.save(session);
    }

    public List<Session> getActiveSessions() {
        return sessionRepository.findByIsActiveTrue();
    }

    public void endSession(Integer sessionId) {
        Session session = sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Session not found"));
        session.setIsActive(false);
        sessionRepository.save(session);
    }

    public Session getSessionById(Integer sessionId) {
        return sessionRepository.findById(sessionId).orElseThrow(() -> new EntityNotFoundException("Session not found"));
    }

    public void inviteUserToSession(Integer sessionId, String username) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Session not found"));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Create and save the invitation record
        SessionInvitation invitation = new SessionInvitation();
        invitation.setSession(session);
        invitation.setUser(user);
        invitation.setInvitationTime(LocalDateTime.now());

        sessionInvitationRepository.save(invitation);
    }

    public List<User> getInvitedUsers(Integer sessionId) {
        return sessionInvitationRepository.findInvitedUsersBySessionId(sessionId);
    }
}
