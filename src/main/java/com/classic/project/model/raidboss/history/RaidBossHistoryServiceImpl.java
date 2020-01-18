package com.classic.project.model.raidboss.history;

import com.classic.project.model.raidboss.history.response.RaidBossHistoryInsert;
import com.classic.project.model.raidboss.history.response.RaidBossHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RaidBossHistoryServiceImpl implements RaidBossHistoryService {

    @Autowired
    private RaidBossHistoryRepository raidBossHistoryRepository;

    private static Logger logger = LoggerFactory.getLogger(RaidBossHistoryServiceImpl.class);
    @Override
    public void insertHistoryRecord(RaidBossHistoryInsert raidBossHistoryInsert) {
        logger.info("{} record is going to be inserted", raidBossHistoryInsert.toString());
	RaidBossHistory raidBossHistory = raidBossHistoryRepository.save(new RaidBossHistory(raidBossHistoryInsert.getRaidbossName(), raidBossHistoryInsert.getDeathTimer(), raidBossHistoryInsert.getWindowTimer(), raidBossHistoryInsert.getAliveTimer()));
	logger.info("{} record inserted successfully", raidBossHistory.toString());
    }

    @Override
    public ResponseEntity<List<RaidBossHistoryResponse>> getHistoryRecords() {
        List<RaidBossHistory> allHistoryRecords = raidBossHistoryRepository.findAll();
        List<RaidBossHistoryResponse> allHistoryRecordsResponce = new ArrayList<>();
        allHistoryRecords.forEach(record -> allHistoryRecordsResponce.add(RaidBossHistoryResponse.raidBossHistoryResponse(record)));
	return new ResponseEntity<>(allHistoryRecordsResponce, HttpStatus.OK);
    }
}
