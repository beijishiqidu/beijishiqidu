package personal.blog.controller.common;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.service.UserService;
import personal.blog.vo.User;

@Controller
public class RootPathController {

    private static final Logger LOGGER = Logger.getLogger(RootPathController.class);

    private static final Random rand = new Random();

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forwardHomePage(HttpServletRequest request) {
        ModelAndView mav;
        LOGGER.debug("PC端访问了主页");
        mav = new ModelAndView("index");
        mav.addObject("sessionId", request.getSession().getId());
        LOGGER.debug(request.getSession().getId());
        User user = userService.getUser("admin");
        mav.addObject("user",user);
        LOGGER.debug("测试热部署.....");
        mav.addObject("aa","xxxxx");

        LOGGER.debug(user.getPassword()+"======================");
        int imgNum =  rand.nextInt(2)+1;
        mav.addObject("backGroundImgPath","images/"+imgNum+".jpg");

        return mav;
    }
}






















