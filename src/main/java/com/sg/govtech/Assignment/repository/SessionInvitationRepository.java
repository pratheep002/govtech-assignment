package com.sg.govtech.Assignment.repository;

import com.sg.govtech.Assignment.entity.SessionInvitation;
import com.sg.govtech.Assignment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionInvitationRepository extends JpaRepository<SessionInvitation, Long> {
    @Query("SELECT DISTINCT si.user FROM SessionInvitation si WHERE si.session.id = :sessionId")
    List<User> findInvitedUsersBySessionId(@Param("sessionId") Integer sessionId);

}
