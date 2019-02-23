package com.classic.project.model.clan;

import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ClanServiceImpl implements ClanService {

    @Autowired
    private ClanRepository clanRepository;


    @Override
    public ResponseEntity<List<ClanResponseEntity>> getAllClanInfo() {
        List<ClanResponseEntity> responseMembers = ClanResponseEntity.convertAll(clanRepository.findAll());
        return new ResponseEntity<>(responseMembers, HttpStatus.OK);
    }

    @Override
    public void addNewClan(Clan registerClan) {
        registerClan.setClanMembers(new ArrayList<>());
	clanRepository.save(registerClan);
    }
}
