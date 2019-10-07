package com.classic.project;

import com.classic.project.model.radiboss.scheduler.MessageListener;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PostConstruct;
import javax.security.auth.login.LoginException;
import java.util.Date;

@Configuration
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class DemoApplication {


    private static String botToken;

    @Value("${discord.token.bot}")
    private void setBotToken(String token) {
        botToken = token;
    }

    public static void main(String[] args) {
        JDA jda = null;
        //You can also add event listeners to the already built JDA instance
        // Note that some events may not be received if the listener is added after calling build()
        // This includes events such as the ReadyEvent
        ConfigurableApplicationContext ctc = SpringApplication.run(DemoApplication.class, args);
        try {
            jda = new JDABuilder(botToken).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
        jda.addEventListener(ctc.getBean(MessageListener.class));
    }

}

