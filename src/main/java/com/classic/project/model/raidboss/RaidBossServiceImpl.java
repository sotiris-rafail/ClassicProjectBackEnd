package com.classic.project.model.raidboss;

import com.classic.project.model.raidboss.exception.RaidBossExistException;
import com.classic.project.model.raidboss.exception.RaidBossNotFoundException;
import com.classic.project.model.raidboss.response.ResponseRaidBoss;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.util.*;

@Component
public class RaidBossServiceImpl implements RaidBossService {

    @Autowired
    private RaidBossRepository raidBossRepository;

    private static Logger logger = LoggerFactory.getLogger(RaidBossServiceImpl.class);

    @Override
    public ResponseEntity<ResponseRaidBoss> updateDeathTimer(int raidId, Date timer) {
        if(timer.after(new Date())){
            logger.error("time is set to " + timer);
            throw new DateTimeException("Date is on future");
        }
        raidBossRepository.updateDeathTimer(raidId, timer);
        return new ResponseEntity<>(getResponseRaidBoss(raidBossRepository.findById(raidId).get()), HttpStatus.OK);
    }

    private Calendar calendar2 = Calendar.getInstance();
    @Override
    public ResponseEntity<List<ResponseRaidBoss>> getAllBosses() {
        List<ResponseRaidBoss> response = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        List<RaidBoss> all = raidBossRepository.findAll();
        for (RaidBoss rb : all) {
            calendar.setTime(rb.getTimeOfDeath());
            calendar2.setTime(rb.getTimeOfDeath());
            if(rb.getWindowStarts().equals(rb.getWindowEnds())){
                Date windowStarts = getWindowStarts(calendar, rb.getWindowStarts().split(":")).getTime();
                response.add(ResponseRaidBoss.convertForRaidBossTable(rb, windowStarts, windowStarts, rb.isUnknown()));
            } else {
                Date windowStarts = getWindowStarts(calendar, rb.getWindowStarts().split(":")).getTime();
                Date windowEnds = getWindowEnds(calendar2, rb.getWindowEnds().split(":")).getTime();
                response.add(ResponseRaidBoss.convertForRaidBossTable(rb, windowStarts, windowEnds, rb.isUnknown()));
            }
        }
        response.sort(Comparator.comparing(ResponseRaidBoss::getRaidBossState).reversed().thenComparing(ResponseRaidBoss::getWindowStarts));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ResponseRaidBoss> addNewRaid(RaidBoss raidBoss) {
        Optional<RaidBoss> raidFromDB = raidBossRepository.findBossByNameLowerCase(raidBoss.getName().toLowerCase());
        if(raidFromDB.isPresent()) {
            throw new RaidBossExistException(raidFromDB.get().getName());
        }
        raidBoss.setNameLowerCase(raidBoss.getName().toLowerCase());
        raidBoss.setEpicBossPoints(0);
        raidBoss.setTimeOfDeath(new Date());
        raidBoss.setUnknown(false);
        raidBoss.setNotified(false);
	    RaidBoss rb = raidBossRepository.save(raidBoss);
	    return new ResponseEntity<>(getResponseRaidBoss(rb), HttpStatus.CREATED);
    }

    private ResponseRaidBoss getResponseRaidBoss(RaidBoss raidBoss) {
        if(raidBoss.getWindowStarts().equals(raidBoss.getWindowEnds())) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(raidBoss.getTimeOfDeath());
            Date windowStarts = getWindowStarts(calendar, raidBoss.getWindowStarts().split(":")).getTime();
            return ResponseRaidBoss.convertForRaidBossTable(raidBoss, windowStarts, windowStarts, raidBoss.isUnknown());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(raidBoss.getTimeOfDeath());
            calendar2.setTime(raidBoss.getTimeOfDeath());
            Date windowStarts = getWindowStarts(calendar, raidBoss.getWindowStarts().split(":")).getTime();
            Date windowEnds = getWindowEnds(calendar2, raidBoss.getWindowEnds().split(":")).getTime();
            return ResponseRaidBoss.convertForRaidBossTable(raidBoss, windowStarts, windowEnds, raidBoss.isUnknown());
        }
    }

    @Override
    public void setToUnknown(int raidId) {
        Optional<RaidBoss> raidFromDb = raidBossRepository.findById(raidId);
        if(!raidFromDb.isPresent()){
            throw new RaidBossNotFoundException(raidId);
        }
        raidBossRepository.setToUnKnown(raidId);
    }

    @Override
    public List<ResponseRaidBoss> getEpicsAndMinisForDiscord() {
        List<ResponseRaidBoss> responseRaidBosses = new ArrayList<>();
        raidBossRepository.findAllByTypeOfRaidBossAndTypeOfRaidBoss(TypeOfRaidBoss.EPIC, TypeOfRaidBoss.MINI).forEach(boss -> responseRaidBosses.add(getResponseRaidBoss(boss)));
        return responseRaidBosses ;
    }

    @Override
    public List<ResponseRaidBoss> getBossByNameForDiscord(String name) {
	return raidBossRepository.findByNameLowerCase(name.toLowerCase()).isPresent() ? Collections.singletonList(getResponseRaidBoss(raidBossRepository.findByNameLowerCase(name.toLowerCase()).get().get(0))) : null;
    }

    @Override
    public List<ResponseRaidBoss> getBossByNameAndTypeForDiscord(String name, TypeOfRaidBoss typeOfRaidBoss) {
        return Collections.singletonList(getResponseRaidBoss(raidBossRepository.findByNameLowerCaseAndTypeOfRaidBoss(name.toLowerCase(), typeOfRaidBoss).get(0)));
    }

    @Override
    public List<ResponseRaidBoss> getBossByTypeForDiscord(TypeOfRaidBoss typeOfRaidBoss) {
        List<ResponseRaidBoss> responseRaidBosses = new ArrayList<>();
        raidBossRepository.findAllByTypeOfRaidBoss(typeOfRaidBoss).forEach(boss -> responseRaidBosses.add(getResponseRaidBoss(boss)));
        return responseRaidBosses;
    }

    public static Calendar getWindowStarts(Calendar calendar, String[] windowStartsTime) {
        calendar.add(Calendar.DATE, Integer.parseInt(windowStartsTime[0]));
        calendar.add(Calendar.HOUR, Integer.parseInt(windowStartsTime[1]));
        return calendar;
    }

    private static Calendar getWindowEnds(Calendar calendar, String[] windowEndsTime) {
        calendar.add(Calendar.DATE, Integer.parseInt(windowEndsTime[0]));
        calendar.add(Calendar.HOUR, Integer.parseInt(windowEndsTime[1]));
        return calendar;
    }

    private static Date getKoreanwindowStart(Date windowStart) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(windowStart);
        calendar.add(Calendar.HOUR, 9);
        return calendar.getTime();
    }

    private static Date getKoreanwindowEnd(Date windowEnds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(windowEnds);
        calendar.add(Calendar.HOUR, 9);
        return calendar.getTime();
    }
}
