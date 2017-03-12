package personal.blog.controller.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "article")
public class FrontPageController {

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    public ModelAndView forwardArticleTypePage() {
        return new ModelAndView("/article/type");
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ModelAndView forwardArticleDetailPage(String articleId, String typeId) {

        return new ModelAndView("/article/detail");
    }
}
