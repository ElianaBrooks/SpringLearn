package xyz.songlin.springlearn;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import xyz.songlin.springlearn.service.User;
import xyz.songlin.springlearn.service.UserService;

public class Main {
    public static void main(String[] args) {
        // 使用Spring装配所有Bean
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        UserService userService = context.getBean(UserService.class);

        // 登陆成功
        User user = userService.login("test@example.com", "123456");
        System.out.println(user);

        // 查询user
        User userNo2 = userService.getUser(2);
        System.out.println(userNo2);
    }
}
