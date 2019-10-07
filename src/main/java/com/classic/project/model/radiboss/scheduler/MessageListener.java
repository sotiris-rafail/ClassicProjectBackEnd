package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.item.unSold.UnSoldItemService;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
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
public class MessageListener extends ListenerAdapter {

    @Autowired
    private RaidBossService raidBossService;

    @Autowired
    private UnSoldItemService unSoldItemService;

    private static Logger logger = LoggerFactory.getLogger(MessageListener.class);

    //@Scheduled(cron = "* * * * * *") //the top of every hour of every day.
    //@SubscribeEvent
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
	if(!event.getAuthor().isBot()) {
	    logger.info("LOGGING I RECEIVED THE FOLLOW {} {} {}: {}", event.getGuild().getName(), event.getTextChannel().getName(), Objects.requireNonNull(event.getMember()).getEffectiveName(), event.getMessage().getContentDisplay());
	    if (event.getMessage().getContentRaw().startsWith("!raidboss") && event.getChannel().getName().equals("general")) {
		try {
		    TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
		    textChannel.sendMessage(buildBossesOutput(raidBossService.getEpicsAndMinisForDiscord())).queue();
		} catch (Exception e) {
		    TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
		    textChannel.sendMessage("raid_boss_spam channel is missing").queue();
		}
	    } else if (event.getMessage().getContentRaw().startsWith("!sales") && event.getChannel().getName().equals("general")) {
		try {
		    TextChannel textChannel = event.getGuild().getTextChannelsByName("sales_spam", true).get(0);
		    textChannel.sendMessage(buildUnsoldItemsOutput(Objects.requireNonNull(unSoldItemService.getUnSoldItems().getBody()))).queue();
		} catch (Exception e) {
		    TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
		    textChannel.sendMessage("sales_spam channel is missing").queue();
		}
	    } else if (event.getMessage().getContentRaw().startsWith("!boss") && event.getChannel().getName().equals("general")) {
		String[] messages = event.getMessage().getContentRaw().split(" ");
		if(messages.length < 2) {
		    TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
		    textChannel.sendMessage("Raid name is missing").queue();
		} else {
		    List<ResponseRaidBoss> bosses = new ArrayList<>();
		    if (raidBossService.getBossByNameForDiscord(messages[1]) == null ) {
			TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
			textChannel.sendMessage(messages[1] + "does not exist").queue();
		    } else {
			bosses.add(raidBossService.getBossByNameForDiscord(messages[1]));
			TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
			textChannel.sendMessage(buildBossesOutput(bosses)).queue();
		    }
		}
	    }
	}
    }
    private MessageEmbed buildBossesOutput(List<ResponseRaidBoss> bosses) {
	List<MessageEmbed.Field> text = new ArrayList<>();
	if(!bosses.isEmpty()) {
	    bosses.forEach(boss -> text.add(new MessageEmbed.Field(boss.getName(), boss.toString(), true)));
	}
	return new MessageEmbed("http://83.212.102.61:4200/raidboss", "Information Of Epic/Mini Bosses", "", EmbedType.UNKNOWN, OffsetDateTime.now(),
	    Color.DARK_GRAY.getRGB(), null , null, new MessageEmbed.AuthorInfo("ClassicBot", "", "", ""), null, null, null, text);
    }

    private MessageEmbed buildUnsoldItemsOutput(List<ResponseUnSoldItem> unSoldItems) {
	List<MessageEmbed.Field> text = new ArrayList<>();
	if(!unSoldItems.isEmpty()) {
	    unSoldItems.forEach(unSoldItem -> text.add(new MessageEmbed.Field(unSoldItem.getName(), unSoldItem.toString(), true)));
	}
	return new MessageEmbed("http://83.212.102.61:4200/auction", "Information Of Auction", "", EmbedType.RICH, OffsetDateTime.now(),
	    Color.DARK_GRAY.getRGB(), null, null, new MessageEmbed.AuthorInfo("ClassicBot", "", "", ""), null, null, null, text);
    }
}