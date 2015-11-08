package personal.blog.service.business;

import org.springframework.transaction.annotation.Transactional;
import personal.blog.vo.User;

/**
 * Created by maobin(phone:15829581409,mail:mb19890630@126.com) on 2015/11/8 0008.
 */
public interface UserService {
    User getUser(String userID);

    @Transactional
    int insertUser(User user);

    @Transactional
    int updateUser(User user);

    @Transactional
    int deleteUser(String userID);
}
