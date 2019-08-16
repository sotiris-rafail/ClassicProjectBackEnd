package com.classic.project.model.radiboss;


import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/raidboss")
public class RaidBossController {

    RaidBossService raidBossService;

    @Autowired
    public RaidBossController(RaidBossService raidBossService) {
        this.raidBossService = raidBossService;
    }


    @RequestMapping(path = "/updateDeathTimer", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseRaidBoss> updateDeathTimer(@RequestParam(value = "raidId")int raidId,
                                 @RequestParam(value = "timer") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)  Date timer){
        return raidBossService.updateDeathTimer(raidId, timer);
    }

    @RequestMapping(path = "/setToUnknown", method = RequestMethod.PUT, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void setToUnknown(@RequestParam(value = "raidId")int raidId){
        raidBossService.setToUnknown(raidId);
    }

    @RequestMapping(path = "/getInfo", method = RequestMethod.GET, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ResponseRaidBoss>> updateDeathTimer(){
        return raidBossService.getAllBosses();
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ResponseRaidBoss> addNewRaidBoss(@RequestBody RaidBoss raidBoss){
	    return raidBossService.addNewRaid(raidBoss);
    }

}
