package com.classic.project.model.radiboss;

import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface RaidBossService {
    void updateDeathTimer(int raidId, Date timer);

    ResponseEntity<List<ResponseRaidBoss>> getAllBosses();

    void addNewRaid(RaidBoss raidBoss);
}
