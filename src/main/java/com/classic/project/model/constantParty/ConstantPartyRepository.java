package com.classic.project.model.constantParty;

import com.classic.project.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ConstantPartyRepository extends JpaRepository<ConstantParty, Integer> {

    @Query("select cp from ConstantParty cp, User user where user.cp.cpId = cp.cpId and user.userId = ?1 and user.typeOfUser = 0")
    Optional<ConstantParty> findByLeaderId(int userId);
}
