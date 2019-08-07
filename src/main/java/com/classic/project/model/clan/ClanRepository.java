package com.classic.project.model.clan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClanRepository extends JpaRepository<Clan, Integer> {

    Optional<Clan> findByNameLowerCase(String clanName);
}
