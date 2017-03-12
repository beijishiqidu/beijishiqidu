package personal.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import personal.blog.dao.UserDao;
import personal.blog.service.UserService;
import personal.blog.vo.User;

/**
 * Created by maobin(phone:15829581409,mail:mb19890630@126.com) on 2015/11/8 0008.
 */

@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String userID) {
        return userDao.getUser(userID);
    }

    @Override
    @Transactional
    public int insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    @Transactional
    public int updateUser(User user) {
        return 0;
    }

    @Override
    @Transactional
    public int deleteUser(String userID) {
        return 0;
    }
}
