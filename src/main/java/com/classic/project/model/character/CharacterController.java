package com.classic.project.model.character;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = "/character")
public class CharacterController {

    private CharacterService characterService;

    @Autowired
    public CharacterController(CharacterService characterService) {
	this.characterService = characterService;
    }

    @RequestMapping(value = "/getInfoForRegister/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object[]>> getInfoForRegister(@PathVariable(name = "userId") int userId){
        return characterService.getInfoForRegister(userId);
    }
}
