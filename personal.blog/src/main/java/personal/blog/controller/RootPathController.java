package personal.blog.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RootPathController {

    private static final Logger LOGGER = Logger.getLogger(RootPathController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forwardHomePage(HttpServletRequest request) {
        ModelAndView mav = null;
        LOGGER.debug("端访问了主页");
        mav = new ModelAndView("index");

        mav.addObject("sessionId", request.getSession().getId());

        LOGGER.debug(request.getSession().getId());

        LOGGER.debug("测试source tree");
        return mav;
    }
}
