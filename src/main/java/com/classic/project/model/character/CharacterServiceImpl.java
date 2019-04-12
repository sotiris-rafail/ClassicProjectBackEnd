package com.classic.project.model.character;

import com.classic.project.model.character.exception.CharacterExistException;
import com.classic.project.model.character.responce.RegisterCharacter;
import com.classic.project.model.character.responce.UpdateCharacter;
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
	List<Character> characterFromDb = characterRepository.findByUserId(userId);
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
    	if(characterRepository.findCharacterByInGameName(registerCharacter.getInGameName()).isPresent()){
    		throw new CharacterExistException(registerCharacter.getInGameName());
		}
        Character character = RegisterCharacter.convertToDBObject(registerCharacter);
	Optional<Clan> clan = clanRepository.findById(registerCharacter.getClanId());
	Optional<User> user = userRepository.findById(registerCharacter.getUserId());
	character.setUser(user.get());
	character.setClan(clan.get());
	characterRepository.save(character);
    }

	@Override
	public void updateCharacter(UpdateCharacter character) {
		Optional<Character> characterFromDB = characterRepository.findById(character.getCharId());
		String inGameName = (character.getInGameName() == null || character.getInGameName().equals("")) ? characterFromDB.get().getInGameName() : character.getInGameName();
		int level = character.getLevel() == -1 ? characterFromDB.get().getLevel() : character.getLevel();
		int clanId = character.getClanId() == -1 ? characterFromDB.get().getClan().getClanId() : character.getClanId();
		ClassOfCharacter classOfCharacter = character.getClassOfCharacter() == -1 ? characterFromDB.get().getClassOfCharacter() : ClassOfCharacter.values()[character.getClassOfCharacter()];
		TypeOfCharacter typeOfCharacter = character.getTypeOfCharacter() == -1 ? characterFromDB.get().getTypeOfCharacter() : TypeOfCharacter.values()[character.getTypeOfCharacter()];
		characterRepository.updateCharacter(character.getCharId(),inGameName, level, clanId, classOfCharacter, typeOfCharacter);
	}

	@Override
	public void deleteCharacter(int characterId) {
		characterRepository.deleteByCharacterId(characterId);
	}


	private static String[] getAllNames(){
	String[] names = new String[ClassOfCharacter.values().length];
	for (int i = 0; i < ClassOfCharacter.values().length; i++) {
	    names[i] = ClassOfCharacter.values()[i].getName();
	}
	return names;
    }
}
