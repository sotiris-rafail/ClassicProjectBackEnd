package com.classic.project.model.character;

import com.classic.project.model.clan.Clan;
import com.classic.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<Character, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Character character set character.user.userId = ?1 , character.clan.clanId = ?2 where character.characterId = ?3")
    void setUserIdClanIdToRegisterChar(User userId, Clan clanId, int charId);

    @Query("select character from Character character where character.user.userId = ?1")
    List<Character> findByUserId(int userId);

    @Modifying
    @Transactional
    @Query("UPDATE Character character set character.inGameName = ?2 , character.level = ?3, character.clan.clanId = ?4, character.classOfCharacter = ?5, character.typeOfCharacter = ?6 where character.characterId = ?1")
    void updateCharacter(int charId, String inGameName, int level, int clanId, ClassOfCharacter classOfCharacter, TypeOfCharacter typeOfCharacter);

    @Modifying
    @Transactional
    @Query("delete from Character character where character.characterId = ?1")
    void deleteByCharacterId(int charId);

    Optional<Character> findByInGameNameContaining(String inGameName);

    Optional<Character> findCharacterByInGameNameLowerCase(String inGameName);

    int countByTypeOfCharacterAndAndUser(TypeOfCharacter typeOfCharacter, User user);

    Optional<Character> findCharacterByInGameName(String inGameName);
}
