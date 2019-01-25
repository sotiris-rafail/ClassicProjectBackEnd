package com.classic.project.model.clan;


import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
