package personal.blog.dao.business;

import personal.blog.vo.User;

/**
 * Created by maobin(phone:15829581409,mail:mb19890630@126.com) on 2015/11/8 0008.
 */
public interface IUserDao {

    public User getUser(String userID);
    
    //git gui提交

    public int insertUser(User user);
}
