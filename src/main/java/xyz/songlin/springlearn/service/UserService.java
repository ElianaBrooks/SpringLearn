package xyz.songlin.springlearn.service;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;

@Component
public class UserService {
    @Autowired
    private MailService mailService;

    @Autowired
    private HikariDataSource dataSource;

    // 注入了一个MailService
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    public void setDataSource(HikariDataSource dataSource) {
        this.dataSource = dataSource;
    }


    public User login(String email, String password) {

        try (Connection connection = dataSource.getConnection()) {
            String sql = "select id, name from users where email = ? and password = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                System.out.println("login success");
                return new User(resultSet.getLong("id"), email, password, resultSet.getString("name"));
            } else {
                throw new RuntimeException("login failed.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUser(long id) {
        try (Connection connection = dataSource.getConnection()) {
            String sql = "select * from users where id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                return new User(id, email, password, name);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User register(String email, String password, String name) {
        try (Connection connection = dataSource.getConnection()) {
            String selectSql = "select id from users where email=?";
            PreparedStatement pstmt = connection.prepareStatement(selectSql);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                throw new RuntimeException("账号已经存在，注册失败");
            }

            String insertSql = "insert into users(name, password, email) values (?,?,?)";
            pstmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            int row = pstmt.executeUpdate();
            if (row < 1) {
                throw new RuntimeException("数据库插入失败," + email);
            }
            ResultSet keys = pstmt.getGeneratedKeys();
            long key = 0;
            while (keys.next()) {
                key = keys.getLong(1);
            }
            User user = new User(key, email, password, name);

            mailService.sendRegistrationMail(user);
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
