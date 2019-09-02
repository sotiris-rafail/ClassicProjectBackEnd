package com.classic.project.model.constantParty;

import com.classic.project.model.character.CharacterRepository;
import com.classic.project.model.character.TypeOfCharacter;
import com.classic.project.model.constantParty.exception.ConstantPartyExistException;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserRepository;
import com.classic.project.security.UserAuthConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@CacheConfig(cacheNames = {"membersDashBoard", "clanMembers"})
public class ConstantPartyServiceImpl implements ConstantPartyService {

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthConfirm userAuthConfirm;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    private Environment environment;

    private Path rootLocation;

    @Override
    public ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findByLeaderId(userId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId) {
        userAuthConfirm.isTheAuthUser(userRepository.findById(userId).get());
        if (isMemberOfTheCP(cpId, userId)) {
            ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findById(cpId));
            return new ResponseEntity<>(responseCP, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    private Boolean isMemberOfTheCP(int cpId, int userId) {
        return userRepository.isUserMemberOfCP(cpId, userId).isPresent();
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteMember(int characterId) {
        User member = characterRepository.findById(characterId).get().getUser();
        userRepository.deleteMemberByCharacterIdId(member.getUserId());
        int boxes = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfBoxes() - characterRepository.countByTypeOfCharacterAndAndUser(TypeOfCharacter.BOX, member);
        int actives = constantPartyRepository.findById(member.getCp().getCpId()).get().getNumberOfActivePlayers() - 1;
        constantPartyRepository.addUsersTpCP(actives, boxes, member.getCp().getCpId());
    }

    @Override
    public void addNewCP(ConstantParty newCp) {
        if (constantPartyRepository.findByCpNameContaining(newCp.getCpName()).isPresent()) {
            throw new ConstantPartyExistException(newCp.getCpName());
        }
        newCp.setMembers(new ArrayList<>());
        newCp.setAqPoints(0);
        newCp.setCorePoints(0);
        newCp.setOrfenPoints(0);
        newCp.setNumberOfActivePlayers(0);
        newCp.setNumberOfBoxes(0);
        constantPartyRepository.save(newCp);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getCPIdName() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertForAddSingleUser(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getCPNumbers() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertNumbersForDashboard(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<ResponseConstantParty>> getEpicPoints() {
        List<ConstantParty> cpsFromDb = constantPartyRepository.findAll();
        List<ResponseConstantParty> response = new ArrayList<>();
        cpsFromDb.forEach(cp -> response.add(ResponseConstantParty.convertPointsForDashboard(cp)));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
