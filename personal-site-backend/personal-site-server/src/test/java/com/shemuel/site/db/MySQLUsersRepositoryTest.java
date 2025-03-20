//package com.shemuel.site.db;
//
//import com.shemuel.site.repository.MySQLUserRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//
//@SpringBootTest
//public class MySQLUsersRepositoryTest {
//
//    @Autowired
//    private MySQLUserRepository repository;
//
//    @Test
//    @Transactional  // 测试后自动回滚
//    @Rollback(false) // 若需要保留测试数据可添加 false
//    public void testSave() {
//        // 创建一个用户对象
//        UserProfile users = new UserProfile();
//        users.setUsername("test");
//        users.setEmail("test@exampl1e.com");
//        users.setPasswordHash("password");
//        users.setPhone("12345678901");
//        users.setFailedAttempts(0);
//        users.setLockedUntil(null);
//        users.setCreatedAt(new Date());
//        users.setLastLogin(new Date());
//
//
//        // 调用 save 方法
//        UserProfile savedUsers = repository.save(users);
//        System.out.println(savedUsers);
//    }
//}