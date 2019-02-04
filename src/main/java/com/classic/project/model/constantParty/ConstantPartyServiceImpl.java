package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.response.ResponseConstantParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ConstantPartyServiceImpl implements ConstantPartyService {

    @Autowired
    private ConstantPartyRepository constantPartyRepository;

    @Override
    public ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findByLeaderId(userId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseConstantParty> getCp(int cpId) {
        ResponseConstantParty responseCP = ResponseConstantParty.convertForLeader(constantPartyRepository.findById(cpId));
        return new ResponseEntity<>(responseCP, HttpStatus.OK);
    }
}
