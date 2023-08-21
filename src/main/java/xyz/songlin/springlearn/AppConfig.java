package xyz.songlin.springlearn;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.*;
import xyz.songlin.springlearn.service.User;
import xyz.songlin.springlearn.service.UserService;

import java.time.ZoneId;

@Configuration
@ComponentScan
@PropertySource("app.properties")
public class AppConfig {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        UserService userService = context.getBean(UserService.class);

        // 登陆成功
        User user = userService.login("test@example.com", "123456");
        System.out.println(user);

        // 查询user
        User userNo2 = userService.getUser(2);
        System.out.println(userNo2);

        User songlin = userService.register("songlin@example.com", "123455", "songlin");
        System.out.println(songlin);
    }

    @Bean
    public HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://rm-cn-lbj3cp9sp000kt9o.rwlb.cn-chengdu.rds.aliyuncs.com:3306/spring-learn");
        config.setUsername("learn");
        config.setPassword("Aliyun@2023");
        config.addDataSourceProperty("connectionTimeout", "10000"); // 连接超时：1秒
        config.addDataSourceProperty("idleTimeout", "60000"); // 空闲超时：60秒
        config.addDataSourceProperty("maximumPoolSize", "10"); // 最大连接数：10
        config.addDataSourceProperty("autoCommit", "true"); // 最大连接数：10
        HikariDataSource ds = new HikariDataSource(config);
        return ds;
    }

    @Bean
    // 非测试环境返回系统默认时区
    @Profile("!test")
    @Primary
    ZoneId createZoneId() {
        return ZoneId.systemDefault();
    }

    @Bean
    // 测试环境返回纽约时区
    @Profile("test")
    ZoneId createZoneIdForTest() {
        return ZoneId.of("America/New_York");
    }
}
