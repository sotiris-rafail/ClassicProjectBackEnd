package com.classic.project.model.constantParty;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConstantPartyRepository extends JpaRepository<ConstantParty, Integer> {

    @Query("select cp from ConstantParty cp, User user where user.cp = cp.cpId and user.userId = ?1")
    Optional<ConstantParty> findByLeaderId(int userId);
}
