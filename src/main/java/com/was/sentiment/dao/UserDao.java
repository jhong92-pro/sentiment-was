package com.was.sentiment.dao;

import com.was.sentiment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Integer>, QueryByExampleExecutor<User> {

    Optional<User> findByEmail(String email);
}
