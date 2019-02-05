package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.response.ResponseConstantParty;
import org.springframework.http.ResponseEntity;

public interface ConstantPartyService {
    ResponseEntity<ResponseConstantParty> getCpByLeaderId(int userId);

    ResponseEntity<ResponseConstantParty> getCp(int cpId, int userId);
}
