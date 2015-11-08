package personal.blog.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import personal.blog.service.business.UserService;
import personal.blog.vo.User;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RootPathController {

    private static final Logger LOGGER = Logger.getLogger(RootPathController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forwardHomePage(HttpServletRequest request) {
        ModelAndView mav;
        LOGGER.debug("PC端访问了主页");
        mav = new ModelAndView("index");
        mav.addObject("sessionId", request.getSession().getId());
        LOGGER.debug(request.getSession().getId());

        User user = userService.getUser("1");
        mav.addObject("user",user);
        LOGGER.debug("测试热部署.....");

        mav.addObject("aa","xxxxx");
        return mav;
    }
}






















