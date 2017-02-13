package personal.blog.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import personal.blog.service.ArticleService;
import personal.blog.util.HtmlFilterUtil;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Override
    public PageSplitUtil<Article> getArticleListForPage(Integer firstResult, Integer maxResults) {

        firstResult = firstResult == null ? 0 : firstResult;
        maxResults = maxResults == null ? 6 : maxResults;

        
        List<Article> list = new ArrayList<Article>();
        Long totalCount = 0L;
        /*Long totalCount = genericDao.findCountByCriteria(dc);

        dc.addOrder(Order.desc("releaseDate"));
        List<Article> list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);

        if (list.isEmpty()) {
            if (firstResult >= maxResults) {
                firstResult = firstResult - maxResults;
                list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);
            }
        }*/

        for (Article article : list) {
            if (StringUtils.isNotEmpty(article.getContent())) {
                String str = HtmlFilterUtil.filterHtml(article.getContent());
                article.setContent(str);
            }
        }

        PageSplitUtil<Article> ps = new PageSplitUtil<Article>(list, firstResult, maxResults, totalCount.longValue());

        return ps;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public Long saveArticleInfo(Long articleId, String title, String content, String typeStr) {

        return 0L;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void updateArticle(Article article) {
        //genericDao.updateObject(article);
    }

    @Override
    public void deleteArticleById(Long articleId) {
        
    }
}
