package com.classic.project.model.constantParty;

        import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.user.UserRepository;
import com.classic.project.security.UserAuthConfirm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConstantPartyServiceImpl implements ConstantPartyService {

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthConfirm userAuthConfirm;

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
        userRepository.deleteMemberById(characterId);
    }


    private Boolean isMemberOfTheCP(int cpId, int userId) {
        return  userRepository.isUserMemberOfCP(cpId, userId).isPresent();
    }
}
