package personal.blog.controller.common;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
public class RootPathController {

    private static final Logger LOGGER = Logger.getLogger(RootPathController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView forwardHomePage(HttpServletRequest request) {
        ModelAndView mav;
        LOGGER.debug("PC端访问了主页");
        mav = new ModelAndView("index");

        // 暂时从数据库中查询文章的分类
        mav.addObject("articleTypeCount", articleService.getArticleTypeCount());
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(0, 3, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }
}
