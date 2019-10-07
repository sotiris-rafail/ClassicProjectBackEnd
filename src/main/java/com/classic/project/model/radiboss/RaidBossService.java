package com.classic.project.model.radiboss;

import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface RaidBossService {
    ResponseEntity<ResponseRaidBoss> updateDeathTimer(int raidId, Date timer);

    ResponseEntity<List<ResponseRaidBoss>> getAllBosses();

    ResponseEntity<ResponseRaidBoss> addNewRaid(RaidBoss raidBoss);

    void setToUnknown(int raidId);

    List<ResponseRaidBoss> getEpicsAndMinisForDiscord();

    ResponseRaidBoss getBossByNameForDiscord(String name);
}
