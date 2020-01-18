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

    @Query("select user from User user where user.emailLowerCase = ?1")
    User findUserByEmailLowerCase(String email);

    @Query("select user from User user where user.cp.cpId IS NULL")
    List<User> findUsersWithoutCP();

    @Modifying
    @Transactional
    @Query("update User user set user.cp.cpId = ?1 where user.userId=?2")
    void addUsersToCP(int cpId, int userId);

    @Query("select user from User user where user.cp.cpId =?1 and user.userId = ?2")
    Optional<User> isUserMemberOfCP(int cpId, int userId);

    @Query("select user from User user where user.cp.cpId is not null and user.userId = ?1")
    Optional<User> isCpMember(int cpId);

    @Query("select user.typeOfUser from User user where user.userId = ?1")
    TypeOfUser getTypeOfUser(int userId);

    @Query("select user.userId from User user, Character char where char.characterId = ?1 and char.user = user.userId")
    int getUserByCharacterId(int characterId);

    @Modifying
    @Transactional
    @Query("update User user set user.typeOfUser = ?2 where user.userId = ?1")
    void updateUserRole(int userId, TypeOfUser typeOfUser);


    @Modifying
    @Transactional
    @Query("delete from User where userId = ?1")
    void deleteUserByUserId(int userId);

    @Modifying
    @Transactional
    @Query("update User user set user.cp = null where user.userId = ?1")
    void deleteMemberByCharacterIdId(int characterId);

    @Query("select user.email from User user")
    List<String> getAllEmails();

    @Query("select user.email from User user where user.typeOfUser = ?1")
    List<String> findUsersByTypeOfUser(TypeOfUser typeOfUser);

    @Query("select user.email from User user, Option options where user.userId = options.userOption.userId and options.newItemOption = true")
    List<String> getUsersEmailWithSendNewItemOptionEnable();

    @Query("select user.email from User user, Option options where user.userId = options.userOption.userId and options.bossesOption = true")
    List<String> getUsersWithSendRaidBossOptionEnable();
}
