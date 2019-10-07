package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.radiboss.RaidBossService;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class MessageListener extends ListenerAdapter implements EventListener {

    @Autowired
    private RaidBossService raidBossService;

    private static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    //@Scheduled(cron = "* * * * * *") //the top of every hour of every day.
    //@SubscribeEvent
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        logger.info("LOGGING I RECEIVED THE FOLLOW {} {} {}: {}", event.getGuild().getName(), event.getTextChannel().getName(), Objects.requireNonNull(event.getMember()).getEffectiveName(), event.getMessage().getContentDisplay());
        if(event.getMessage().getContentRaw().startsWith("!raidboss") && event.getChannel().getName().equals("general")) {
            TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
            textChannel.sendMessage(buildBossesOutput(raidBossService.getEpicsAndMinisForDiscord())).queue();
        }
    }

    private MessageEmbed buildBossesOutput(List<ResponseRaidBoss> bosses) {
        List<MessageEmbed.Field> text = new ArrayList<>();
        if(!bosses.isEmpty()) {
            bosses.forEach(boss -> text.add(new MessageEmbed.Field(boss.getName(), boss.toString(), true)));
        }
        return new MessageEmbed("", "Information Of Epic/Mini Bosses", "", EmbedType.UNKNOWN, OffsetDateTime.now(),
                Color.DARK_GRAY.getRGB(), new MessageEmbed.Thumbnail("", "", 0, 0), new MessageEmbed.Provider("", ""),
                new MessageEmbed.AuthorInfo("ClassicBot", "", "", ""), null, null, null, text);
    }
}