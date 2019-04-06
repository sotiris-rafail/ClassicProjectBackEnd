package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.radiboss.*;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import com.classic.project.model.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class RaidBossScheduler {

    @Autowired
    private RaidBossRepository raidBossRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "*/60 * * * * *") //the top of every hour of every day.
    public void NotifyOnWindow(){
	List<RaidBoss> bossesOnWindow = new ArrayList<>();
	List<RaidBoss> epicBosses = raidBossRepository.findAllByTypeOfRaidBoss(TypeOfRaidBoss.EPIC);
	for(RaidBoss raidBoss : epicBosses) {
	    Calendar deathTimer = Calendar.getInstance();
	    deathTimer.setTime(raidBoss.getTimeOfDeath());
	    Date windowStarts = RaidBossServiceImpl.getWindowStarts(deathTimer, raidBoss.getWindowStarts().split(":")).getTime();
	    Date windowEnds = RaidBossServiceImpl.getWindowStarts(deathTimer, raidBoss.getWindowEnds().split(":")).getTime();
	    if(!raidBoss.isNotified() && !raidBoss.isUnknown()){
		if(ResponseRaidBoss.raidBossState(windowStarts, windowEnds, false).equals(RaidBossState.ONWINDOW.name())){
		    bossesOnWindow.add(raidBoss);
		}
	    }
	}
	if(!bossesOnWindow.isEmpty()) {
	    sendMail(bossesOnWindow);
	    for(RaidBoss raidBoss : bossesOnWindow){
		raidBossRepository.updateDeathTimerNotification(raidBoss.getRaidBossId());
	    }
	}
    }

    private void sendMail(List<RaidBoss> bossesOnWindow) {
	String[] recipients = getRecipients(userRepository.getAllEmails());
	if(recipients.length > 10) {
	    sendMoreThanEmails(bossesOnWindow, recipients);
	} else {
	    send(bossesOnWindow, recipients);
	}
    }

    private void sendMoreThanEmails(List<RaidBoss> bossesOnWindow, String[] recipients) {
	for(int i =0; i < recipients.length; i=i+100){
	    String[] subRecipients = new String[100];
	    if(i >= 100) {
		for (int x = 0; x <= i-100; x++) {
		    subRecipients[x] = recipients[x + 100];
		    System.out.println(subRecipients[x]);
		}
	    } else {
		for (int x = 0; x < 100; x++) {
		    subRecipients[x] = recipients[x];
		    System.out.println(subRecipients[x]);
		}
	    }
	    send(bossesOnWindow, subRecipients);
	}
    }

    private void send(List<RaidBoss> bossesOnWindow, String[] recipients){
	SimpleMailMessage mail = new SimpleMailMessage();
	mail.setTo(recipients);
	mail.setText(getText(bossesOnWindow));
	mail.setSubject("Bosses On Window");
	mail.setFrom("inquisitionAlliance@gmail.com");
	mail.setSentDate(new Date());
	javaMailSender.send(mail);
    }

    private static String[] getRecipients(List<String> allEmails) {
	int i = 0;
	String[] recipients = new String[allEmails.size()];
	for(String email : allEmails) {
	    recipients[i++] = email;
	}
	return recipients;
    }


    private String getText(List<RaidBoss> bossesOnWindow) {
	StringBuilder text = new StringBuilder();
	for(RaidBoss raidBoss : bossesOnWindow) {
	    text.append(raidBoss.getName()).append(" is on window now.").append(" Please log your spy.").append(" State changed at ").append(new Date()).append("\n");
	}
	return text.toString();
    }
}
