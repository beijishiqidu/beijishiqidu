package personal.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import personal.blog.dao.business.IUserDao;
import personal.blog.service.LoginService;
import personal.blog.util.EncryptUtil;
import personal.blog.vo.User;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private IUserDao iUserDao;

    @Override
    public boolean checkUserNameAndPassWord(String userName, String password) {
        User user = iUserDao.getUser(userName);
        if (null == user) {
            return false;
        }
        return user.getPassword().equals(EncryptUtil.encode(password));
    }
}
