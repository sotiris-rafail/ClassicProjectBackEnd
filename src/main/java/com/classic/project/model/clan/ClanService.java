package com.classic.project.model.clan;

import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface ClanService {
    ResponseEntity<List<ClanResponseEntity>> getAllClanInfo();

    void addNewClan(Clan registerClan);

    List<Clan> findAll();

    Optional<Clan> findById(int clanId);
}
