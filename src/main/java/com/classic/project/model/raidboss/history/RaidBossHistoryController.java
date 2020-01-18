package com.classic.project.model.raidboss.history;

import com.classic.project.model.raidboss.history.response.RaidBossHistoryInsert;
import com.classic.project.model.raidboss.history.response.RaidBossHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping(path = "/raidboss/history")
public class RaidBossHistoryController {

    @Autowired
    private RaidBossHistoryService raidBossHistoryService;

    @RequestMapping(path = "/addHistoryRecord", method = RequestMethod.POST, consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public void addNewRaidBoss(@RequestBody RaidBossHistoryInsert raidBossHistoryInsert){
	raidBossHistoryService.insertHistoryRecord(raidBossHistoryInsert);
    }

    @RequestMapping(path = "/getRaidBossHistory", method = RequestMethod.GET, produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RaidBossHistoryResponse>> addNewRaidBoss(){
	return raidBossHistoryService.getHistoryRecords();
    }
}
