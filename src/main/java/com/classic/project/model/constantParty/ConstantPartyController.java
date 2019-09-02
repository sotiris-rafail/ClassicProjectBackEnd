package com.classic.project.model.constantParty;

import com.classic.project.model.clan.Clan;
import com.classic.project.model.constantParty.file.CpFile;
import com.classic.project.model.constantParty.response.ResponseConstantParty;
import com.classic.project.model.constantParty.response.file.AddNewFile;
import com.classic.project.model.constantParty.response.file.RootFolderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
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

    @RequestMapping(value = "/getCPNumbers", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseConstantParty>> getCPNumbers() {
        return constantPartyService.getCPNumbers();
    }

    @RequestMapping(value = "/getEpicPoints", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseConstantParty>> getEpicPoints() {
        return constantPartyService.getEpicPoints();
    }

//    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "application/json")
//    @ResponseStatus(HttpStatus.OK)
//    public boolean uploadEpicPhoto(@RequestParam(name = "photo") MultipartFile photo, @RequestParam(name = "cpId") int cpId,  @RequestParam(name = "cpName") String cpName){
//        return constantPartyService.uploadEpicPhoto(photo, cpId, cpName);
//    }
//
//    @RequestMapping(value = "/{cpId}/{userId}/photos", method = RequestMethod.GET, produces = "application/json")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<RootFolderResponse> getCpPhotos(@PathVariable(name = "cpId") int cpId, @PathVariable(name = "userId") int userId) {
//        return constantPartyService.getCpPhotos(cpId, userId);
//    }
//
//    @RequestMapping(value = "/addFolder", method = RequestMethod.POST, consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void addNewFolder(@RequestBody AddNewFile cpFile, @RequestParam(name = "cpId") int cpId) throws GeneralSecurityException, IOException {
//        constantPartyService.addNewFolder(cpId, cpFile);
//    }
//
//    @RequestMapping(value = "/addFolder", method = RequestMethod.DELETE, consumes = "application/json")
//    @ResponseStatus(HttpStatus.CREATED)
//    public void deleteFile(@RequestParam(name = "fileId") String fileId) throws GeneralSecurityException, IOException {
//        constantPartyService.deleteFile(fileId);
//    }
}
