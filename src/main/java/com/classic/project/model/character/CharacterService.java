package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CharacterService {
    ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId);

    void registerCharacter(RegisterCharacter character);
}
