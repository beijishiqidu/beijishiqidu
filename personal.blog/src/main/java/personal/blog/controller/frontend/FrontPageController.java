package personal.blog.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class FrontPageController {

    @RequestMapping(value = "/article/type", method = RequestMethod.GET)
    public ModelAndView forwardArticleTypePage() {
        return new ModelAndView("/article/type");
    }

    @RequestMapping(value = "/article/detail", method = RequestMethod.GET)
    public ModelAndView forwardArticleDetailPage(String articleId, String typeId) {
        return new ModelAndView("/article/detail");
    }
    
    @RequestMapping(value = "/photo/home", method = RequestMethod.GET)
    public ModelAndView forwardPhotosHomePage(){
        return new ModelAndView("/photo/home");
    }
    
    @RequestMapping(value = "/photo/list", method = RequestMethod.GET)
    public ModelAndView forwardPhotosListPage(){
        return new ModelAndView("/photo/list");
    }
    
    @RequestMapping(value = "/photo/detail", method = RequestMethod.GET)
    public ModelAndView forwardPhotosDetailPage(){
        return new ModelAndView("/photo/detail");
    }
    
    @RequestMapping(value = "/about/me", method = RequestMethod.GET)
    public ModelAndView forwardAboutMePage(){
        return new ModelAndView("/about/about_me");
    }
}
