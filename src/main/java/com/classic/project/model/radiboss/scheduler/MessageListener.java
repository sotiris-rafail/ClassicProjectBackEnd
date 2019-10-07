package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.radiboss.RaidBoss;
import com.classic.project.model.radiboss.RaidBossService;
import com.classic.project.model.radiboss.RaidBossServiceImpl;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.util.List;

@Component
public class MessageListener extends ListenerAdapter implements EventListener {

    @Autowired
    private RaidBossService raidBossService = new RaidBossServiceImpl();

    //@Scheduled(cron = "* * * * * *") //the top of every hour of every day.
    //@SubscribeEvent
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith("!raidboss")) {
            TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
            textChannel.sendMessage(buildBossesOutput(raidBossService.getEpicsAndMinisForDiscord())).queue();
            //raidBossService.
        }
    }

    private static String buildBossesOutput(List<RaidBoss> bosses) {
        StringBuilder text = new StringBuilder();
        if(!bosses.isEmpty()) {
            text.append("State of bosses is : \n");
            bosses.forEach(boss -> text.append(boss.toString()).append("\n"));
        }
        return text.length() == 0 ? "404 Not found" : text.toString();
    }
}
