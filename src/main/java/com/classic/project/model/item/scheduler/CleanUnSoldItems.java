package com.classic.project.model.item.scheduler;

import com.classic.project.model.item.SaleState;
import com.classic.project.model.item.StateOfItem;
import com.classic.project.model.item.sold.SoldItem;
import com.classic.project.model.item.sold.SoldItemService;
import com.classic.project.model.item.unSold.UnSoldItem;
import com.classic.project.model.item.unSold.UnSoldItemService;
import com.classic.project.model.user.User;
import com.classic.project.model.user.UserService;
import com.classic.project.model.user.verification.VerificationStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CleanUnSoldItems {

	private static Logger logger = LoggerFactory.getLogger(CleanUnSoldItems.class);

	@Autowired
	private UnSoldItemService unSoldItemService;

	@Autowired
	private SoldItemService soldItemService;

	@Autowired
	private UserService userService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${send.email.sold.items}")
	private boolean notifyOnSoldItem;

	@Value("${auction.link}")
	private String auctionLink;

	@Scheduled(cron = "0 0 * * * *") //the top of every hour of every day.
	public void cleanUpTheSoldUnsoldItem() {
		List<UnSoldItem> unSoldItems = unSoldItemService.getSoldUnSoldItemsByStateOfItem();
		List<SoldItem> soldItems = new ArrayList<>();
		for (UnSoldItem unSoldItem : unSoldItems) {
			logger.info(unSoldItems.toString());
			soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
			deleteUnSoldItems(unSoldItem.getItemId());
		}
		sendMail(soldItems.stream().filter(soldItem -> !soldItem.getWhoBoughtIt().equals("")).collect(Collectors.toList()));
	}

	@Scheduled(cron = "0 0 15 * * *") //every day at 15:00
	public void cleanUpExpiredUnSoldItems() {
		List<UnSoldItem> unSoldItems = unSoldItemService.getUnSoldItemsByStateOfItem();
		List<SoldItem> soldItems = new ArrayList<>();
		for (UnSoldItem unSoldItem : unSoldItems) {
			logger.info(unSoldItems.toString());
			if (unSoldItem.getExpirationDate().before(new Date())) {
				unSoldItem.setStateOfItem(StateOfItem.SOLD);
				soldItems.add(registerUnSoldItemsAsSoldItems(unSoldItem));
				deleteUnSoldItems(unSoldItem.getItemId());
			}
		}
		sendMail(soldItems.stream().filter(soldItem -> !soldItem.getWhoBoughtIt().equals("")).collect(Collectors.toList()));
	}

	private void sendMail(List<SoldItem> soldItems) {
		if (notifyOnSoldItem && !soldItems.isEmpty()) {
			notifyForSoldItems(soldItems);
			notifyUserForSoldItems(soldItems);
		}
	}

	private void notifyForSoldItems(List<SoldItem> soldItems) {
		try {
			MimeMessage message = javaMailSender.createMimeMessage();
			MimeMessageHelper simpleMailMessage = new MimeMessageHelper(message, true);
			simpleMailMessage.setFrom("inquisitionalliance@gmail.com");
			simpleMailMessage.setTo(superUserEmails());
			simpleMailMessage.setSubject("Item(s) to be delivered");
			simpleMailMessage.setText(getText(soldItems, false), true);
			simpleMailMessage.setSentDate(new Date());
			javaMailSender.send(simpleMailMessage.getMimeMessage());
		} catch (MessagingException e) {
			logger.error("EMAIL FOR ADMINISTRATORS FAILED. DID NOT SEND");
			logger.error(e.getMessage());
		}
	}

	private void notifyUserForSoldItems(List<SoldItem> soldItems) {
		List<String> buyers = getDistinctBuyers(soldItems);
		for (String buyer : buyers) {
			try {
				MimeMessage message = javaMailSender.createMimeMessage();
				MimeMessageHelper simpleMailMessage = new MimeMessageHelper(message, true);
				simpleMailMessage.setFrom("inquisitionalliance@gmail.com");
				simpleMailMessage.setTo(userService.findEmailByCharacterName(buyer));
				simpleMailMessage.setSubject("Item(s) bought from auction");
				simpleMailMessage.setText(getText(soldItems.stream().filter(soldItem -> soldItem.getWhoBoughtIt().equals(buyer)).collect(Collectors.toList()), true), true);
				simpleMailMessage.setSentDate(new Date());
				javaMailSender.send(simpleMailMessage.getMimeMessage());
			} catch (MessagingException e) {
				logger.error(String.format("EMAIL FOR %s FAILED. DID NOT SEND", buyer));
				logger.error(e.getMessage());
			}
		}
	}

	private String getText(List<SoldItem> soldItems, boolean singleMail) {
		int x = 0;
		String simpleTD = "<td class=\"td\">";
		String coloredTDFirst = "<td class=\"td color first\">";
		String coloredTD = "<td class=\"td color\">";
		String coloredTDLast = "<td class=\"td color last\">";
		StringBuilder text = new StringBuilder();
		text.append("<html>");
		text.append("<head><style>");
		text.append(".table{font-family: arial, sans-serif; border-collapse: collapse; width: 100%}");
		text.append(".td, .th, .span{text-align: left; padding: 10px;}");
		text.append(".color {background-color: lightblue;}");
		text.append(".color-th{ background-color: DodgerBlue;}");
		text.append(".a, .span {font-family: arial, sans-serif; font-weight:bold;text-decoration:none;}");
		text.append(".first {border-radius: 12px 0 0 12px;}");
		text.append(".last {border-radius: 0 12px 12px 0;}");
		text.append("</style></head>");
		text.append("<body><table style=\"margin-bottom:2em; font-family: arial, sans-serif; border-collapse: collapse; width: 100%\">");
		if(singleMail) {
			text.append("<tr class=\"tr\"><th class=\"th color-th first\">Item</th><th class=\"th color-th last\">Price</th>");
		} else {
			text.append("<tr class=\"tr\"><th class=\"th color-th first\">Item</th><th class=\"th color-th\">Price</th>");
			text.append("<th class=\"th color-th last\">Buyer</th>");
		}
		text.append("</tr>");
		for (SoldItem soldItem : soldItems) {
			text.append("<tr class=\"tr\">");
			if(singleMail) {
				if (x % 2 != 0) {
					text.append(coloredTDFirst).append(soldItem.getItemName()).append("</td>").append(coloredTDLast).append(NumberFormat.getIntegerInstance().format(soldItem.getBoughtPrice())).append("</td>");
				} else {
					text.append(simpleTD).append(soldItem.getItemName()).append("</td>").append(simpleTD).append(NumberFormat.getIntegerInstance().format(soldItem.getBoughtPrice())).append("</td>");
				}
			} else {
				if (x % 2 != 0) {
					text.append(coloredTDFirst).append(soldItem.getItemName()).append("</td>").append(coloredTD).append(NumberFormat.getIntegerInstance().format(soldItem.getBoughtPrice())).append("</td>");
					text.append(coloredTDLast).append(soldItem.getWhoBoughtIt()).append("</td>");
				} else {
					text.append(simpleTD).append(soldItem.getItemName()).append("</td>").append(simpleTD).append(NumberFormat.getIntegerInstance().format(soldItem.getBoughtPrice())).append("</td>");
					text.append(simpleTD).append(soldItem.getWhoBoughtIt()).append("</td>");
				}
			}
			text.append("</tr>");
			x++;
		}
		text.append("</table>");
		text.append("<span class=\"span\">More information can be found in the <a class=\"a\" href=").append(auctionLink).append(">auction</a> system</span>");
		text.append("</body></html>");
		return text.toString();
	}

	private SoldItem registerUnSoldItemsAsSoldItems(UnSoldItem unSoldItem) {
		SoldItem soldItem = new SoldItem(unSoldItem.getGrade(),
				unSoldItem.getItemType(), unSoldItem.getPhotoPath(),
				unSoldItem.getItemName(), unSoldItem.getStateOfItem(),
				unSoldItem.getMaxPrice(), unSoldItem.getCurrentValue(),
				unSoldItem.getLastBidder(), false,
				unSoldItem.getItemId(), unSoldItem.getBidStep(),
				unSoldItem.getDaysToStayUnSold(), SaleState.DONE);
		soldItem.setRegisterDate(new Date());
		logger.info("The Unsold item " + unSoldItem.toString());
		logger.info("The sold item " + soldItem.toString());
		return soldItemService.save(soldItem);
	}

	private void deleteUnSoldItems(int itemId) {
		logger.info("UN SOLD ITEM WITH ID " + itemId + "IS DELETED");
		unSoldItemService.deleteCleanUpUnSoldItems(itemId);
	}

	private String[] superUserEmails() {
		return userService.getSuperUsersEmail().toArray(new String[0]);
	}

	private List<String> getDistinctBuyers(List<SoldItem> soldItems) {
		List<String> buyers = new ArrayList<>();
		if (soldItems.size() == 1) {
			buyers.add(soldItems.get(0).getWhoBoughtIt());
		} else {
			for (SoldItem item : soldItems) {
				if (!buyers.contains(item.getWhoBoughtIt())) {
					buyers.add(item.getWhoBoughtIt());
				}
			}
		}
		buyers = findBuyersWithNotificationForSoldItems(buyers);
		return buyers;
	}

	private List<String> findBuyersWithNotificationForSoldItems(List<String> buyers) {
		List<String> finalBuyers = new ArrayList<>();
		for (String buyer : buyers) {
			User user = userService.findUserByCharacterName(buyer);
			if (user.getVerification().getStatus().equals(VerificationStatus.VERIFIED)
					&& user.getOptions().isSoldItemOption()) {
				finalBuyers.add(buyer);
				logger.info("FINAL BUYER " + buyer);
			}
		}
		return finalBuyers;
	}
}
