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

//�������ļ���Ҫ����
@Transactional
//�������ļ���һ��Service
@Service
public class UserServiceImpl implements UserService {

    // ���������Spring������ע�롣Ҳ����˵��������дIUserDao userDao = new IUserDao();,Spring�������newһ����
    // MyBatis�����ǹ���xml�����ӳ�估Dao����������ֱ����@Autowired����ע��Ϳ�����
    @Autowired
    private IUserDao iUserDao;

    @Override
    public User getUser(String userID) {
        return iUserDao.getUser(userID);
    }

    //�����÷�����Ҫ����
    @Override
    @Transactional
    public int insertUser(User user) {
    /*����Ϊ��֤�������ӵ�
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
