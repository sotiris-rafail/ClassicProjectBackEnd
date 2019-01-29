package com.classic.project.model.character;

import com.classic.project.model.clan.Clan;
import com.classic.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Character character set character.user.userId = ?1 , character.clan.clanId = ?2 where character.characterId = ?3")
    void setUserIdClanIdToRegisterChar(User userId, Clan clanId, int charId);

    @Query("select character from Character character where character.user.userId = ?1")
    List<Character> findByUserId(int userId);
}
