package xyz.songlin.springlearn.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class MailService {
    @Autowired
    private ZoneId zoneId = ZoneId.systemDefault();

    @Value("#{smtpConfig.host}")
    private String smtpHost;
    @Value("#{smtpConfig.port}")
    private int smtpPort;

    @PostConstruct
    private void init() {
        System.out.println("Init mail service with");
        System.out.println("ZoneId: " + zoneId);
        System.out.println("SmtpHost: " + smtpHost);
        System.out.println("SmtpPort: " + smtpPort);
    }

    @PreDestroy
    private void shutdown() {
        System.out.println("Shutdown mail service");
    }

    public void setZoneId(ZoneId zoneId) {
        this.zoneId = zoneId;
    }

    public String getTime() {
        return ZonedDateTime.now(this.zoneId).format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public void sendLoginMail(User user) {
        System.err.printf("Hi, %s! You are logged in at %s%n", user.getName(), getTime());
    }

    public void sendRegistrationMail(User user) {
        System.err.printf("Welcome, %s%n", user.getName());
    }
}
