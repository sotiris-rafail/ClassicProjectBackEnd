package com.classic.project.model.constantParty;

        import com.classic.project.model.character.CharacterRepository;
	import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.user.UserRepository;
import com.classic.project.security.UserAuthConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

	import java.util.Optional;

@Component
public class ConstantPartyServiceImpl implements ConstantPartyService {

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthConfirm userAuthConfirm;

    @Autowired
    private CharacterRepository characterRepository;

    @Override
    public ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findByLeaderId(userId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId) {
        userAuthConfirm.isTheAuthUser(userRepository.findById(userId).get());
        if(isMemberOfTheCP(cpId, userId)) {
            ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findById(cpId));
            return new ResponseEntity<>(responseCP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @Override
    public void deleteMember(int characterId) {
        int memberId = characterRepository.findById(characterId).get().getUser().getUserId();
        userRepository.deleteMemberByCharacterIdId(memberId);
    }

    @Override
    public void updateEpicPoints(String rbName, int pointsToAdd, int cpId) {
	Optional<ConstantParty> cpFromDb = constantPartyRepository.findById(cpId);
	if(rbName.equals("Orfen")){
	    constantPartyRepository.updateOrfenPoints(cpId, cpFromDb.get().getOrfenPoints() + pointsToAdd);
	} else if(rbName.equals("Core")){
	    constantPartyRepository.updateCorePoints(cpId, cpFromDb.get().getCorePoints() + pointsToAdd);
	} else if(rbName.equals("Queen Ant")) {
	    constantPartyRepository.updateAQPoints(cpId, cpFromDb.get().getAqPoints() + pointsToAdd);
	}
    }

    private Boolean isMemberOfTheCP(int cpId, int userId) {
        return  userRepository.isUserMemberOfCP(cpId, userId).isPresent();
    }
}
