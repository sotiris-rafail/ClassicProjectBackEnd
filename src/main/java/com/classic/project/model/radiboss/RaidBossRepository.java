package com.classic.project.model.radiboss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

public interface RaidBossRepository extends JpaRepository<RaidBoss, Integer> {

    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.timeOfDeath = ?2 where raidBoss.raidBossId =?1")
    void updateDeathTimer(int raidId, Date timer);
}
