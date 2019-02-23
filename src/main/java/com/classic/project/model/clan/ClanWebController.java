package com.classic.project.model.clan;


import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/clan")
public class ClanWebController {

    private ClanService clanService;

    @Autowired
    public  ClanWebController(ClanService clanService){
        this.clanService = clanService;
    }

    @RequestMapping(value = "/allInfo", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ClanResponseEntity>> getAllClanInfo(){
        return clanService.getAllClanInfo();
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void getAllClanInfo(@RequestBody Clan registerClan){
	clanService.addNewClan(registerClan);
    }
}
