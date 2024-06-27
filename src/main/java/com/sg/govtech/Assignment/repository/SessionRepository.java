package com.sg.govtech.Assignment.repository;

import com.sg.govtech.Assignment.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {
    List<Session> findByIsActiveTrue();
}