package personal.blog.dao.impl;

import org.springframework.stereotype.Repository;

import personal.blog.dao.UserDao;
import personal.blog.vo.User;

@Repository
public class UserDaoImpl extends GenericDaoImpl implements UserDao {

    @Override
    public User getUser(String userName) {
        return getUniqueObject(User.class, "from User user where user.userName=?", userName);
    }

    @Override
    public int insertUser(User user) {
        return 0;
    }


}
