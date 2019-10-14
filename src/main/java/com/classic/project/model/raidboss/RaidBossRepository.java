package com.classic.project.model.raidboss;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RaidBossRepository extends JpaRepository<RaidBoss, Integer> {

    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.timeOfDeath = ?2, raidBoss.isUnknown = false, raidBoss.isNotified = false where raidBoss.raidBossId =?1")
    void updateDeathTimer(int raidId, Date timer);

    @Query("select raidboss from RaidBoss raidboss where raidboss.nameLowerCase = ?1")
    Optional<RaidBoss> findBossByNameLowerCase(String name);

    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.isUnknown = true where raidBoss.raidBossId =?1")
    void setToUnKnown(int raidId);


    @Modifying
    @Transactional
    @Query("update RaidBoss raidBoss set raidBoss.isNotified = true where raidBoss.raidBossId =?1")
    void updateDeathTimerNotification(int raidId);


    List<RaidBoss> findAllByTypeOfRaidBoss(TypeOfRaidBoss typeOfRaidBoss);

    List<RaidBoss> findByNameLowerCaseAndTypeOfRaidBoss(String name, TypeOfRaidBoss typeOfRaidBoss);

    Optional<List<RaidBoss>> findByNameLowerCase(String name);

    @Query("select raidboss from RaidBoss raidboss where raidboss.typeOfRaidBoss = ?1 or raidboss.typeOfRaidBoss = ?2")
    List<RaidBoss> findAllByTypeOfRaidBossAndTypeOfRaidBoss(TypeOfRaidBoss epics, TypeOfRaidBoss minis);
}
