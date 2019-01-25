package com.classic.project.model.clan;

import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClanService {
    ResponseEntity<List<ClanResponseEntity>> getAllClanInfo();
}
