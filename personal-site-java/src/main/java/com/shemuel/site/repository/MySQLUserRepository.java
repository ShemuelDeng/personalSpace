package com.shemuel.site.repository;

import com.shemuel.site.entity.User;
import com.shemuel.site.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author dengsx
 * @create 2025/03/12
 **/
@Repository("mysqlUserRepository")
@ConditionalOnProperty(name = "db.type", havingValue = "mysql")
public class MySQLUserRepository implements UserRepository {

    @Resource
    private UserMapper userMapper;

    @Override
    public User save(User user) {

        return user;
    }

    @Override
    public Optional<User> findByEmail(String email) {
      return Optional.ofNullable(null);
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(String userId) {

    }
// 其他方法实现...
}
