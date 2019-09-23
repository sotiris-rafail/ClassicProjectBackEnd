package com.classic.project.model.clan;

import com.classic.project.model.character.responce.ResponseCharacter;
import com.classic.project.model.clan.exception.ClanExistException;
import com.classic.project.model.clan.responce.ClanResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
@CacheConfig(cacheNames = "clanMembers")
public class ClanServiceImpl implements ClanService {

    @Autowired
    private ClanRepository clanRepository;


    @Override
    @Cacheable
    public ResponseEntity<List<ClanResponseEntity>> getAllClanInfo() {
        List<ClanResponseEntity> responseMembers = ClanResponseEntity.convertAll(clanRepository.findAll());
        for (ClanResponseEntity clan: responseMembers) {
            clan.getMembers().sort(Comparator.comparing(ResponseCharacter::getTypeOfUser).reversed().thenComparing(ResponseCharacter::getTypeOfCharacter).reversed().thenComparing(ResponseCharacter::getLevel));
        }
        return new ResponseEntity<>(responseMembers, HttpStatus.OK);
    }

    @Override
    public void addNewClan(Clan registerClan) {
        if(clanRepository.findByNameLowerCase(registerClan.getName().toLowerCase()).isPresent()){
            throw new ClanExistException(registerClan.getName());
        }
        registerClan.setNameLowerCase(registerClan.getName().toLowerCase());
        registerClan.setClanMembers(new ArrayList<>());
	    clanRepository.save(registerClan);
    }

    @Override
    public List<Clan> findAll() {
        return clanRepository.findAll();
    }

    @Override
    public Optional<Clan> findById(int clanId) {
        return clanRepository.findById(clanId);
    }
}
