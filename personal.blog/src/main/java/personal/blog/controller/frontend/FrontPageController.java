package personal.blog.controller.frontend;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.bean.LocationIndex;
import personal.blog.service.ArticleService;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;

@Controller
public class FrontPageController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/article/type", method = RequestMethod.GET)
    public ModelAndView forwardArticleTypePage(String id) {
        ModelAndView mav = new ModelAndView("/article/type");
        mav.addObject("id", id);

        // 暂时从数据库中查询文章的分类
        mav.addObject("articleTypeCount", articleService.getArticleTypeCount());
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(0, 10);
        mav.addObject("pagination", psu);
        return mav;
    }

    @RequestMapping(value = "/article/detail", method = RequestMethod.GET)
    public ModelAndView forwardArticleDetailPage(Long articleId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/article/detail");
        Article article = articleService.getArticleById(articleId);
        mav.addObject("articleObj", article);

        LocationIndex index = new LocationIndex();
        index.setHref(request.getContextPath() + "/");
        index.setIndexName("主页");

        LocationIndex subIndex = new LocationIndex();
        subIndex.setHref(request.getContextPath() + "/article/type?id=" + article.getType().getId());
        subIndex.setIndexName(article.getType().getTypeName());
        index.setNextNodex(subIndex);

        LocationIndex subIndex1 = new LocationIndex();
        subIndex1.setIndexName(article.getTitle());
        subIndex.setNextNodex(subIndex1);

        mav.addObject("index", index);

        return mav;
    }

    @RequestMapping(value = "/photo/home", method = RequestMethod.GET)
    public ModelAndView forwardPhotosHomePage() {
        return new ModelAndView("/photo/home");
    }

    @RequestMapping(value = "/photo/list", method = RequestMethod.GET)
    public ModelAndView forwardPhotosListPage() {
        return new ModelAndView("/photo/list");
    }

    @RequestMapping(value = "/photo/detail", method = RequestMethod.GET)
    public ModelAndView forwardPhotosDetailPage() {
        return new ModelAndView("/photo/detail");
    }

    @RequestMapping(value = "/about/me", method = RequestMethod.GET)
    public ModelAndView forwardAboutMePage() {
        return new ModelAndView("/about/about_me");
    }
}
