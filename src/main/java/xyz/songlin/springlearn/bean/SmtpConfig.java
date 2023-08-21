package xyz.songlin.springlearn.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SmtpConfig {
    @Value("${smtp.host}")
    private String host;
    @Value("${smtp.port}")
    private int port;

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
