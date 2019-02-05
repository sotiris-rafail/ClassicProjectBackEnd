package com.classic.project.model.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select user from User user where user.email = ?1")
    User findUserByEmail(String email);

    @Query("select user from User user where user.cp.cpId IS NULL")
    List<User> findUsersWithoutCP();

    @Modifying
    @Transactional
    @Query("update User user set user.cp.cpId = ?1 where user.userId=?2")
    void addUsersToCP(int cpId, int userId);

    @Query("select user from User user where user.cp.cpId =?1 and user.userId = ?2")
    Optional<User> isUserMemberOfCP(int cpId, int userId);
}
