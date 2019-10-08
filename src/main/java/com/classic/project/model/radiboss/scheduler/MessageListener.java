package com.classic.project.model.radiboss.scheduler;

import com.classic.project.model.item.unSold.UnSoldItemService;
import com.classic.project.model.item.unSold.response.ResponseUnSoldItem;
import com.classic.project.model.radiboss.RaidBossService;
import com.classic.project.model.radiboss.TypeOfRaidBoss;
import com.classic.project.model.radiboss.response.ResponseRaidBoss;
import net.dv8tion.jda.api.entities.EmbedType;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.entities.DataMessage;
import org.apache.http.util.Args;
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

	@Override
	public void onGuildJoin(GuildJoinEvent event) {
		TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getGuild().getTextChannels().get(0).getName(), true).get(0);
		textChannel.sendMessage("Hello. I am glad i joined your server. Type !help to start.").queue();
	}

	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(!event.getAuthor().isBot()) {
			Args args = determineTheArgs(event.getMessage().getContentRaw());
			if(isCorrectChannel(args, event)) {
				TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
				if(event.getGuild().getTextChannelsByName("command_channel", true).size() != 0) {
					textChannel.sendMessage("Wrong channel. Please switch to <#" + event.getGuild().getTextChannelsByName("command_channel", true).get(0).getId() + ">").queue();
				} else {
					textChannel.sendMessage("Missing command_channel chanel. Please create it.").queue();
				}
				return;
			}
			logger.info("LOGGING I RECEIVED THE FOLLOW {} {} {}: {}", event.getGuild().getName(), event.getTextChannel().getName(), Objects.requireNonNull(event.getMember()).getEffectiveName(), event.getMessage().getContentDisplay());
			if (args.getCommand().equals("raidboss")) {
				try {
					if(TypeOfRaidBoss.getType(args.getType()) != null || args.getType() == null) {
						TextChannel textChannel = event.getGuild().getTextChannelsByName("raid_boss_spam", true).get(0);
						textChannel.sendMessage(buildBossesOutput(sendRaidBossReply(args))).queue();
					} else {
						TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getTextChannel().getName(), true).get(0);
						textChannel.sendMessage(String.format("-type argument can not be '%s'. mini, simple or epic are allowed" , args.getType())).queue();
					}
				} catch (Exception e) {
					TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
					textChannel.sendMessage(e.getLocalizedMessage()).queue();
				}
			} else if (args.getCommand().equals("sales")) {
				try {
					TextChannel textChannel = event.getGuild().getTextChannelsByName("sales_spam", true).get(0);
					textChannel.sendMessage(buildUnsoldItemsOutput(Objects.requireNonNull(unSoldItemService.getUnSoldItems().getBody()))).queue();
				} catch (Exception e) {
					TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
					textChannel.sendMessage(e.getLocalizedMessage()).queue();
				}
			} else if (args.getCommand().equalsIgnoreCase("help")) {
				TextChannel textChannel = event.getGuild().getTextChannelsByName(event.getChannel().getName(), true).get(0);
				textChannel.sendMessage(buildHelpMessage()).queue();
			}
		}
	}

	private static boolean isCorrectChannel(Args args, MessageReceivedEvent event) {
		return (args.getCommand().equalsIgnoreCase("raidboss") || args.getCommand().equalsIgnoreCase("sales")) && !event.getChannel().getName().equalsIgnoreCase("command_channel");
	}

	private List<ResponseRaidBoss> sendRaidBossReply(Args args) {
		if(args.getType() == null && args.getName() == null) {
			return raidBossService.getAllBosses().getBody();
		} else if(args.getType() == null) {
			return raidBossService.getBossByNameForDiscord(args.getName());
		} else if(args.getName() == null) {
			return raidBossService.getBossByTypeForDiscord(TypeOfRaidBoss.getType(args.getType()));
		} else if(args.getType() != null && args.getName() != null) {
			return raidBossService.getBossByNameAndTypeForDiscord(args.getName(), TypeOfRaidBoss.getType(args.getType()));
		} else {
			return new ArrayList<>();
		}
	}

	private Args determineTheArgs(String contentRaw) {
		Args args = new Args();
		if(contentRaw.startsWith("!raidboss")) {
			args.setCommand("raidboss");
			if (contentRaw.contains("-type") && contentRaw.contains("-name")) {
				String[] split = contentRaw.split("-name");
				if(split[1].contains("-type")) {
					args.setName(split[1].split("-type")[0].trim());
					args.setType(split[1].split("-type")[1].trim());
				} else {
					args.setName(split[1].trim());
					args.setType(split[0].split("-type")[1].trim());
				}
			} else if (contentRaw.contains("-type")) {
				args.setType(contentRaw.split("-type")[1].trim());
			} else if (contentRaw.contains("-name")) {
				args.setName(contentRaw.split("-name")[1].trim());
			}
		} else if(contentRaw.startsWith("!sales")){
			args.setCommand("sales");
		} else if(contentRaw.startsWith("!help")) {
			args.setCommand("help");
		}
		return args;
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

	private MessageEmbed buildHelpMessage() {
		List<MessageEmbed.Field> text = new ArrayList<>();
		text.add((new MessageEmbed.Field("Raid Boss Command", "!raidboss \nReturn information about bosses based on optional type and name arguments. In case of zero provided arguments information about all bosses will be displayed" +
				"\nOptions: \n-type <type_of_raid_boss> \n-name <name_of_raid_boss> \ntype_of_boss can be either 'simple', 'mini' or 'epic' \n(eg !raidboss -type mini -name decar)", false)));
		text.add((new MessageEmbed.Field("Sales Command", "!sales \nYou will get all the items which are on sale right now", false)));
		text.add((new MessageEmbed.Field("Usage", "In order to use the bot three channels are required.\ncommand_channel is used for commanding the bot\nraid_boss_spam is used for displaying the boss results\nsales_spam is used to display all the items on sale.", false)));
		return new MessageEmbed("", "Help Information", "", EmbedType.RICH, OffsetDateTime.now(),
				Color.WHITE.getRGB(), null, null, new MessageEmbed.AuthorInfo("ClassicBot", "", "", ""), null, null, null, text);
	}

	public class Args {
		private String command;
		private String name;
		private String type;

		public Args() {
		}

		public String getCommand() {
			return command;
		}

		public void setCommand(String command) {
			this.command = command;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	}
}