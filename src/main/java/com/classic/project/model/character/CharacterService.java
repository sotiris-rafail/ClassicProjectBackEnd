package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.character.responce.UpdateCharacter;
import com.classic.project.model.user.User;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CharacterService {
    ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId);

    ResponseEntity<ResponseCharacter> registerCharacter(RegisterCharacter character);

    void updateCharacter(UpdateCharacter character);

    void deleteCharacter(int characterId);

    Optional<Character> findById(int characterId);

    int countByTypeOfCharacterAndAndUser(TypeOfCharacter box, User member);

    List<Character> findByUserId(int userId);

    Character findByCharacterName(String characterName);
}
