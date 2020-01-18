package com.classic.project.model.raidboss.history;

import com.classic.project.model.raidboss.history.response.RaidBossHistoryInsert;
import com.classic.project.model.raidboss.history.response.RaidBossHistoryResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;


public interface RaidBossHistoryService {

    void insertHistoryRecord(RaidBossHistoryInsert raidBossHistoryInsert);

    ResponseEntity<List<RaidBossHistoryResponse>> getHistoryRecords();
}
