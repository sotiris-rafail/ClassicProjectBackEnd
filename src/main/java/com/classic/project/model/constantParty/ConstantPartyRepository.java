package com.classic.project.model.constantParty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ConstantPartyRepository extends JpaRepository<ConstantParty, Integer> {

    @Query("select cp from ConstantParty cp, User user where user.cp.cpId = cp.cpId and user.userId = ?1 and user.typeOfUser = 0")
    Optional<ConstantParty> findByLeaderId(int userId);

    @Modifying
    @Transactional
    @Query("update ConstantParty cp set cp.numberOfActivePlayers =?1 , cp.numberOfBoxes =?2 where cp.cpId = ?3")
    void addUsersTpCP(int activePlayers, int numberOfBoxes, int cpId);
}
