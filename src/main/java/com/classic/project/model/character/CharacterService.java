package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.character.responce.UpdateCharacter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CharacterService {
    ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId);

    ResponseEntity<ResponseCharacter> registerCharacter(RegisterCharacter character);

    void updateCharacter(UpdateCharacter character);

    void deleteCharacter(int characterId);
}
