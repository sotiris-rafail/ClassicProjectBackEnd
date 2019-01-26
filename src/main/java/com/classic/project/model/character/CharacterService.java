package com.classic.project.model.character;

import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface CharacterService {
    ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId);
}
