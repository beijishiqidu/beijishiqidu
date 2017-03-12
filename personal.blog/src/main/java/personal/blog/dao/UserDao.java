package personal.blog.dao;

import personal.blog.vo.User;

public interface UserDao {
    
    User getUser(String userName);
    
    int insertUser(User user);
}
