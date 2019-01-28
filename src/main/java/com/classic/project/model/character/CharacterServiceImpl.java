package com.classic.project.model.character;

import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.clan.Clan;
import com.classic.project.model.clan.ClanRepository;
import com.classic.project.model.clan.responce.ClanResponseEntity;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private ClanRepository clanRepository;

    @Autowired UserRepository userRepository;

    @Override
    public ResponseEntity<Map<String, Object[]>> getInfoForRegister(int userId) {
	Map<String, Object[]> response = new HashMap<>();
	List<Clan> clanRepositoryAll = clanRepository.findAll();
	response.put("Clan", ClanResponseEntity.convertForCharRegister(clanRepositoryAll).toArray());
	List<Character> characterFromDb = characterRepository.findAllById(Collections.singleton(userId));
	if (characterFromDb.isEmpty()) {
	    response.put("HasMain", TypeOfCharacter.values());
	} else {
	    characterFromDb.forEach(character -> {
	        if (character.getTypeOfCharacter().name().equals(TypeOfCharacter.MAIN.name())) {
	            if(response.get("HasMain") == null) {
			TypeOfCharacter[] types = new TypeOfCharacter[1];
			types[0] = TypeOfCharacter.BOX;
			response.put("HasMain", types);
		    }
		}
	    });
	    if(response.get("HasMain") == null) {
		response.put("HasMain", TypeOfCharacter.values());
	    }
	}
	response.put("Classes", getAllNames());
 	return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public void registerCharacter(RegisterCharacter registerCharacter) {
        Character character = RegisterCharacter.convertToDBObject(registerCharacter);
	Optional<Clan> clan = clanRepository.findById(registerCharacter.getClanId());
	Optional<User> user = userRepository.findById(registerCharacter.getUserId());
	character.setUser(user.get());
	character.setClan(clan.get());
	characterRepository.save(character);
    }

    private static String[] getAllNames(){
	String[] names = new String[ClassOfCharacter.values().length];
	for (int i = 0; i < ClassOfCharacter.values().length; i++) {
	    names[i] = ClassOfCharacter.values()[i].getName();
	}
	return names;
    }
}
