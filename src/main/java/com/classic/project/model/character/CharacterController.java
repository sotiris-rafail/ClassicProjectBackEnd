package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.character.responce.UpdateCharacter;
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

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCharacter(@RequestBody RegisterCharacter character){
        characterService.registerCharacter(character);
    }

    @RequestMapping(value = "/getInfoForRegister/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Map<String, Object[]>> getInfoForRegister(@PathVariable(name = "userId") int userId){
        return characterService.getInfoForRegister(userId);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateCharacter(@RequestBody UpdateCharacter character){
        characterService.updateCharacter(character);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCharacter(@RequestParam(name = "characterId")int characterId){
        characterService.deleteCharacter(characterId);
    }

    @RequestMapping(value = "/removeCharFromClan", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void removeCharacterFromClan(@RequestParam(name = "characterId") int characterId) {
        characterService.removeCharacterFromClan(characterId);
    }
}
