package com.classic.project.model.radiboss;

import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class RaidBossServiceImpl implements RaidBossService {

    @Autowired
    private RaidBossRepository raidBossRepository;


    @Override
    public void updateDeathTimer(int raidId, Date timer) {
        if(timer.after(new Date())){
            throw new DateTimeException("Date is on future");
        }
        raidBossRepository.updateDeathTimer(raidId, timer);
    }

    @Override
    public ResponseEntity<List<ResponseRaidBoss>> getAllBosses() {
        List<ResponseRaidBoss> response = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        List<RaidBoss> all = raidBossRepository.findAll();
        for (RaidBoss rb : all) {
            calendar.setTime(rb.getTimeOfDeath());
            if(rb.getWindowStarts().equals(rb.getWindowEnds())){
                Date windowStarts = getWindowStarts(calendar, rb.getWindowStarts().split(":"));
                response.add(ResponseRaidBoss.convertForRaidBossTable(rb, windowStarts, windowStarts));
            } else {
                Date windowStarts = getWindowStarts(calendar, rb.getWindowStarts().split(":"));
                Date windowEnds = getWindowEnds(calendar, rb.getWindowEnds().split(":"));
                response.add(ResponseRaidBoss.convertForRaidBossTable(rb, windowStarts, windowEnds));
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private static Date getWindowStarts(Calendar calendar, String[] windowStartsTime) {
        calendar.add(Calendar.DATE, Integer.parseInt(windowStartsTime[0]));
        calendar.add(Calendar.HOUR, Integer.parseInt(windowStartsTime[1]));
        return calendar.getTime();
    }

    private static Date getWindowEnds(Calendar calendar, String[] windowEndsTime) {
        calendar.add(Calendar.DATE, Integer.parseInt(windowEndsTime[0]));
        calendar.add(Calendar.HOUR, Integer.parseInt(windowEndsTime[1]));
        return calendar.getTime();
    }
}
