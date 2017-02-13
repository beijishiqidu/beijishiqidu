package personal.blog.controller.backend;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.service.ArticleService;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;

@Controller
@RequestMapping(value = "admin/article")
public class ArticleMgtController {

    private static final Logger LOGGER = Logger.getLogger(ArticleMgtController.class);
    

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET, value = "/home")
    public ModelAndView backendIndex() {
        LOGGER.error("进入了文章管理页面...");
        return new ModelAndView("/admin/article_home");
    }
    
    @RequestMapping(value = "/add.html", method = RequestMethod.GET)
    public ModelAndView forwardAdminHomePage(Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/article_add");
        return mav;
    }
    
    @RequestMapping(value = "/list.html", method = RequestMethod.GET)
    public ModelAndView forwardNewsListPage() {
        ModelAndView mav = new ModelAndView("admin/article_list");
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(0, 10);
        mav.addObject("pagination", psu);
        return mav;
    }
}
