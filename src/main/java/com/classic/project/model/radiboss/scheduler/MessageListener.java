package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.radiboss.RaidBoss;
import com.classic.project.model.radiboss.RaidBossService;
import com.classic.project.model.radiboss.RaidBossServiceImpl;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.hooks.SubscribeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.security.auth.login.LoginException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class MessageListener extends ListenerAdapter implements EventListener {

    @Autowired
    private RaidBossService raidBossService;

    //@Scheduled(cron = "* * * * * *") //the top of every hour of every day.
    //@SubscribeEvent
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith("!raidboss")) {
            TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
            textChannel.sendMessage(buildBossesOutput(raidBossService.getEpicsAndMinisForDiscord())).queue();
        }
    }

    private MessageEmbed buildBossesOutput(List<ResponseRaidBoss> bosses) {
        StringBuilder text = new StringBuilder();
        if(!bosses.isEmpty()) {
            text.append("State of bosses is : \n");
            bosses.forEach(boss -> {
                if(boss.getRaidBossState().equals("ONWINDOW"))
                    text.append(boss.toString()).append("\n");
            });
        }
        return new MessageEmbed("", "BOSSES", text.toString(), EmbedType.RICH, OffsetDateTime.now(),
                1, new MessageEmbed.Thumbnail("", "", 0, 0), new MessageEmbed.Provider("", ""),
                new MessageEmbed.AuthorInfo("ClassicBot", "", "", ""), null, null, null, new ArrayList<>());
    }
}
/*String url, String title, String description, EmbedType type, OffsetDateTime timestamp,
        int color, Thumbnail thumbnail, Provider siteProvider, AuthorInfo author,
        VideoInfo videoInfo, Footer footer, ImageInfo image, List<Field> fields*/