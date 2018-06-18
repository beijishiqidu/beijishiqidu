package personal.blog.controller.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.form.ArticleForm;
import personal.blog.form.FormAlert;
import personal.blog.service.ArticleService;
import personal.blog.util.HtmlFilterUtil;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;
import personal.blog.vo.ExecResult;

import com.google.gson.Gson;

@Controller
@RequestMapping(value = "admin/article")
public class ArticleMgtController {

    private static final Logger LOGGER = Logger.getLogger(ArticleMgtController.class);

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public ModelAndView forwardAdminHomePage(Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/article_add");
        mav.addObject("articleTypeList", articleService.getArticleTypeList());
        return mav;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView forwardNewsListPage() {
        ModelAndView mav = new ModelAndView("admin/article_list");
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(0, 10, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public ModelAndView saveNewsInfo(String articleId, String title, String content, String type, HttpServletRequest request) {

        ArticleForm articleForm = new ArticleForm();
        articleForm.setTitle(title);
        articleForm.setContent(content);
        articleForm.setType(type);

        List<FormAlert> resultList = articleService.validateArticleForm(articleForm);

        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (!resultList.isEmpty()) {
            returnMap.put("result", false);
            returnMap.put("msg", resultList);
        } else {
            try {
                Long id = articleService.saveArticleInfo(articleId, title, content, type);
                returnMap.put("result", true);
                returnMap.put("msg", "保存成功");
                returnMap.put("articleId", id);
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
                returnMap.put("result", false);
                returnMap.put("dbSave", true);
                returnMap.put("msg", "保存失败");
            }
        }
        Gson gson = new Gson();
        String result = gson.toJson(returnMap);

        request.setAttribute("callback", "App.submitArticleInfoCallback");
        request.setAttribute("returnValue", result);

        ModelAndView mav = new ModelAndView("upload");

        return mav;
    }

    @RequestMapping(value = "/deleteArticle", method = RequestMethod.POST)
    public ModelAndView deleteNews(Long articleId, Integer firstResult, Integer maxResults) {
        articleService.deleteArticleById(articleId);
        ModelAndView mav = new ModelAndView("admin/ajax_article_list");
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(firstResult, maxResults, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }
    
    @RequestMapping(value = "/delete-type", method = RequestMethod.POST)
    @ResponseBody
    public String deleteArticleType(String typeId) {

        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            ExecResult result = articleService.deleteArticleTypeById(typeId);
            returnMap.put("result", result.isResult());
            returnMap.put("msg", result.getMessage());
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            returnMap.put("result", false);
            returnMap.put("msg", e.getMessage());
        }
        Gson gson = new Gson();
        String result = gson.toJson(returnMap);
        return result;
    }

    @RequestMapping(value = "/editArticle.html", method = RequestMethod.GET)
    public ModelAndView forwardEditNewsPage(Long articleId) {
        Article article = articleService.getArticleById(articleId);
        article.setContent(HtmlFilterUtil.fileterLine(article.getContent()));
        ModelAndView mav = new ModelAndView("admin/article_add");
        mav.addObject("articleObj", article);
        mav.addObject("articleTypeList", articleService.getArticleTypeList());
        return mav;
    }

    @RequestMapping(value = "/refreshAdminArticleList.html", method = RequestMethod.GET)
    public ModelAndView ajaxRefreshNewsListPage(Integer firstResult, Integer maxResults) {
        ModelAndView mav = new ModelAndView("admin/ajax_article_list");
        PageSplitUtil<Article> psu = articleService.getArticleListForPage(firstResult, maxResults, StringUtils.EMPTY);
        mav.addObject("pagination", psu);
        return mav;
    }
}
