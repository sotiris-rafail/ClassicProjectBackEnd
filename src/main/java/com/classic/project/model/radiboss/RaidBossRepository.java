package com.classic.project.model.radiboss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

public interface RaidBossRepository extends JpaRepository<RaidBoss, Integer> {

    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.timeOfDeath = ?2, raidBoss.unknown = false where raidBoss.raidBossId =?1")
    void updateDeathTimer(int raidId, Date timer);

    @Query("select raidboss from RaidBoss raidboss where raidboss.name = ?1")
    Optional<RaidBoss> findBossByName(String name);

    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.unknown = true where raidBoss.raidBossId =?1")
    void setToUnKnown(int raidId);
}
