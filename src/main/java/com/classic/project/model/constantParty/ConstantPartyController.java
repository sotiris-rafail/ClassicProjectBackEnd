package com.classic.project.model.constantParty;

import com.classic.project.model.constantParty.response.ResponseConstantParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/cp")
public class ConstantPartyController {

    private ConstantPartyService constantPartyService;

    @Autowired
    public ConstantPartyController(ConstantPartyService constantPartyService) {
        this.constantPartyService = constantPartyService;
    }

    @RequestMapping(value = "/{cpId}/{userId}", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseConstantParty> getCpById(@PathVariable(name = "cpId") int cpId, @PathVariable(name = "userId") int userId) {
        return constantPartyService.getCp(cpId, userId);
    }
}
