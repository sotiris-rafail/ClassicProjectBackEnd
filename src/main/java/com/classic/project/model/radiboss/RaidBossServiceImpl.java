package com.classic.project.model.radiboss;

import com.classic.project.model.radiboss.exception.RaidBossExistException;
import com.classic.project.model.radiboss.exception.RaidBossNotFoundException;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
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


    @Override
    public void updateDeathTimer(int raidId, Date timer) {
        if(timer.after(Calendar.getInstance().getTime())){
            throw new DateTimeException("Date is on future");
        }
        raidBossRepository.updateDeathTimer(raidId, timer);
    }

    @Override
    public ResponseEntity<List<ResponseRaidBoss>> getAllBosses() {
        List<ResponseRaidBoss> response = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
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
    public void addNewRaid(RaidBoss raidBoss) {
        Optional<RaidBoss> raidFromDB = raidBossRepository.findBossByName(raidBoss.getName());
        if(raidFromDB.isPresent()) {
            throw new RaidBossExistException(raidFromDB.get().getName());
        }
        raidBoss.setEpicBossPoints(0);
        raidBoss.setTimeOfDeath(new Date());
        raidBoss.setUnknown(false);
        raidBoss.setNotified(false);
	    raidBossRepository.save(raidBoss);
    }

    @Override
    public void setToUnknown(int raidId) {
        Optional<RaidBoss> raidFromDb = raidBossRepository.findById(raidId);
        if(!raidFromDb.isPresent()){
            throw new RaidBossNotFoundException(raidId);
        }
        raidBossRepository.setToUnKnown(raidId);
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
