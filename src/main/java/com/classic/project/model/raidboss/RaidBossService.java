package com.classic.project.model.raidboss;

import com.classic.project.model.raidboss.response.ResponseRaidBoss;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface RaidBossService {
    ResponseEntity<ResponseRaidBoss> updateDeathTimer(int raidId, Date timer);

    ResponseEntity<List<ResponseRaidBoss>> getAllBosses();

    ResponseEntity<ResponseRaidBoss> addNewRaid(RaidBoss raidBoss);

    void setToUnknown(int raidId);

    List<ResponseRaidBoss> getEpicsAndMinisForDiscord();

    List<ResponseRaidBoss> getBossByNameForDiscord(String name);

    List<ResponseRaidBoss> getBossByNameAndTypeForDiscord(String name, TypeOfRaidBoss typeOfRaidBoss);

    List<ResponseRaidBoss> getBossByTypeForDiscord(TypeOfRaidBoss typeOfRaidBoss);
}
