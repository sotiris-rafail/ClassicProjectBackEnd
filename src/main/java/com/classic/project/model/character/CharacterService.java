package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.character.responce.UpdateCharacter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CharacterService {
    ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId);

    void registerCharacter(RegisterCharacter character);

    void updateCharacter(UpdateCharacter character);

    void deleteCharacter(int characterId);

    void removeCharacterFromClan(int characterId);
}
