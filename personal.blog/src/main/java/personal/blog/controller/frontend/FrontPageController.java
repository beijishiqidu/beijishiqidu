package personal.blog.controller.frontend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.bean.LocationIndex;
import personal.blog.constant.ApplicationConstant;
import personal.blog.service.ArticleService;
import personal.blog.service.PhotoService;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;
import personal.blog.vo.Photo;

@Controller
public class FrontPageController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PhotoService photoService;

    @RequestMapping(value = "/article/type", method = RequestMethod.GET)
    public ModelAndView forwardArticleTypePage(String id) {
        ModelAndView mav = new ModelAndView("/article/type");
        mav.addObject("id", id);

        // 暂时从数据库中查询文章的分类
        mav.addObject("articleTypeCount", articleService.getArticleTypeCount());
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(0, 10, id);
        mav.addObject("pagination", psu);
        return mav;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/article/detail", method = RequestMethod.GET)
    public ModelAndView forwardArticleDetailPage(Long articleId, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("/article/detail");
        Article article = articleService.getArticleById(articleId);
        mav.addObject("articleObj", article);

        Object scanTimePool = request.getSession().getAttribute(ApplicationConstant.SCAN_TIMES_POOL);
        if (null == scanTimePool) {
            Map<Long, Boolean> scanMap = new HashMap<Long, Boolean>();
            scanMap.put(articleId, Boolean.TRUE);
            request.getSession().setAttribute(ApplicationConstant.SCAN_TIMES_POOL, scanMap);
            article.setScanTimes(article.getScanTimes() + 1);
            articleService.updateArticle(article);
        } else if (!((Map<Long, Boolean>) scanTimePool).containsKey(articleId)) {
            ((Map<Long, Boolean>) scanTimePool).put(articleId, Boolean.TRUE);
            article.setScanTimes(article.getScanTimes() + 1);
            articleService.updateArticle(article);
        }

        LocationIndex index = new LocationIndex();
        index.setHref(request.getContextPath() + "/");
        index.setIndexName("主页");

        LocationIndex subIndex = new LocationIndex();
        subIndex.setHref(request.getContextPath() + "/article/type/" + article.getType().getId());
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
    public ModelAndView forwardPhotosListPage(String id) {

        ModelAndView mav = new ModelAndView("/photo/list");
        mav.addObject("id", id);

        // 暂时从数据库中查询文章的分类
        mav.addObject("photoTypeCount", photoService.getPhotoTypeCount());

        // 获取每个相册分类下得相册  
        List<Photo> photoList = photoService.getPhotoAlnumFaceList(id);
        mav.addObject("photoList", photoList);
        mav.addObject("listSize", photoList.size());
        
        return mav;
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
