package xyz.songlin.springlearn.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.stream.Collectors;

@Component
public class AppService {
    @Value("classpath:/logo.txt")
    private Resource resource;

    @Value("classpath:/app.properties")
    private Resource appProps;

    private String logo;

    private final Properties props = new Properties();

    @PostConstruct
    public void init() {
        try (BufferedReader resourceReader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
             InputStream propsInputStream = appProps.getInputStream()) {
            this.logo = resourceReader.lines().collect(Collectors.joining("\n"));

            props.load(propsInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(logo);
        System.out.println(props);
    }
}
