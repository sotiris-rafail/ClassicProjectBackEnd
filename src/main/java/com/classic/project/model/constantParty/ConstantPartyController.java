package com.classic.project.model.constantParty;

import com.classic.project.model.clan.Clan;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCharacter(@RequestParam(name = "characterId")int characterId){
        constantPartyService.deleteMember(characterId);
    }

    @RequestMapping(value = "/updateEpicPoints", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void updateEpicPoints(@RequestParam(name = "rbName")String rbName, @RequestParam(name = "pointsToAdd")int pointsToAdd, @RequestParam(name = "cpId")int cpId ){
	constantPartyService.updateEpicPoints(rbName, pointsToAdd, cpId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addNewCP(@RequestBody ConstantParty newCp){
        constantPartyService.addNewCP(newCp);
    }

    @RequestMapping(value = "/getCPIdName", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseConstantParty>> getCPIdName() {
	return constantPartyService.getCPIdName();
    }
}
