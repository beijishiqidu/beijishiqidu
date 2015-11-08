package personal.blog.service.business.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import personal.blog.dao.business.IUserDao;
import personal.blog.service.business.UserService;
import personal.blog.vo.User;

/**
 * Created by maobin(phone:15829581409,mail:mb19890630@126.com) on 2015/11/8 0008.
 */

//表明该文件需要事务
@Transactional
//表明该文件是一个Service
@Service
public class UserServiceImpl implements UserService {

    // 这个属性由Spring帮我们注入。也就是说我们无需写IUserDao userDao = new IUserDao();,Spring会帮我们new一个的
    // MyBatis帮我们管理xml与类的映射及Dao，所以我们直接用@Autowired进行注入就可以了
    @Autowired
    private IUserDao iUserDao;

    @Override
    public User getUser(String userID) {
        return iUserDao.getUser(userID);
    }

    //表明该方法需要事务
    @Override
    @Transactional
    public int insertUser(User user) {
    /*以下为验证事务而添加的
    UserEntity user1 = new UserEntity();
    user1.setUserID("10");
    user1.setUserPWD("1");
    user1.setUserName("asd");
    user1.setUserBirthday("20120228");
    user1.setUserSalary("15000.26");
    userDao.insertUser(user1);*/
        return iUserDao.insertUser(user);
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
