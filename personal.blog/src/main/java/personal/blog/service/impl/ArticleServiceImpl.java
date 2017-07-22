package personal.blog.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import personal.blog.cache.TypeCount;
import personal.blog.dao.GenericDao;
import personal.blog.form.ArticleForm;
import personal.blog.form.FormAlert;
import personal.blog.service.ArticleService;
import personal.blog.util.FormValidateUtil;
import personal.blog.util.HtmlFilterUtil;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;
import personal.blog.vo.ArticleType;

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger LOGGER = Logger.getLogger(ArticleServiceImpl.class);

    @Autowired
    private GenericDao genericDao;

    @Override
    public PageSplitUtil<Article> getArticleListForPage(Integer firstResult, Integer maxResults, String typeId) {

        LOGGER.info("Query param typeId=" + typeId);

        firstResult = firstResult == null ? 0 : firstResult;
        maxResults = maxResults == null ? 6 : maxResults;

        DetachedCriteria dc = DetachedCriteria.forClass(Article.class);

        if (StringUtils.isNotEmpty(typeId)) {
            ArticleType type = genericDao.getObject(ArticleType.class, typeId);
            dc.add(Restrictions.eq("type", type));
        }
        Long totalCount = genericDao.findCountByCriteria(dc);

        dc.addOrder(Order.desc("createDate"));
        List<Article> list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);

        if (list.isEmpty()) {
            if (firstResult >= maxResults) {
                firstResult = firstResult - maxResults;
                list = genericDao.findRowsByCriteria(dc, firstResult, maxResults);
            }
        }

        for (Article article : list) {
            if (StringUtils.isNotEmpty(article.getContent())) {
                String str = HtmlFilterUtil.filterHtml(article.getContent());
                if (str.length() > 300) {
                    article.setContentSummary(str.substring(0, 300) + "......");
                } else {
                    article.setContentSummary(str);
                }

            }
        }

        PageSplitUtil<Article> ps = new PageSplitUtil<Article>(list, firstResult, maxResults, totalCount.longValue());

        return ps;
    }

    @Override
    public Article getArticleById(Long articleId) {
        return genericDao.getObject(Article.class, articleId);
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void updateArticle(Article article) {
        genericDao.updateObject(article);
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public void deleteArticleById(Long articleId) {
        genericDao.deleteObject(Article.class, articleId);
    }

    @Override
    public List<FormAlert> validateArticleForm(ArticleForm articleForm) {
        return FormValidateUtil.validate(articleForm);
    }

    @Override
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    public Long saveArticleInfo(String articleId, String title, String content, String type) {
        Article article = null;

        if (StringUtils.isEmpty(articleId)) {
            article = new Article();
            article.setScanTimes(0L);
            article.setCreateDate(Calendar.getInstance());
        } else {
            article = genericDao.getObject(Article.class, articleId);
        }

        article.setContent(HtmlFilterUtil.fileterLine(content));
        article.setTitle(title);
        article.setUpdateDate(Calendar.getInstance());
        ArticleType at = genericDao.getObject(ArticleType.class, type);
        article.setType(at);

        genericDao.saveOrUpdateObject(article);

        return article.getId();
    }

    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache")
    public List<ArticleType> getArticleTypeList() {
        return genericDao.listWithCache(ArticleType.class);
    }

    @Override
    // @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache")
    public List<TypeCount> getArticleTypeCount() {
        List<TypeCount> list =
                genericDao.getEntityObjectListByFullSql("select a.type typeId,count(a.id) as typeCount from tbl_article a group by a.type",
                        TypeCount.class);
        List<ArticleType> typeList = getArticleTypeList();
        for (ArticleType at : typeList) {

            // 查询出某些类型下没有文章的列表.
            boolean find = false;
            for (TypeCount tc : list) {
                if (at.getId().longValue() == tc.getTypeId().longValue()) {
                    tc.setTypeName(at.getTypeName());
                    find = true;
                }
            }
            if (!find) {
                TypeCount tc = new TypeCount();
                tc.setTypeCount("0");
                tc.setTypeId(at.getId().longValue());
                tc.setTypeName(at.getTypeName());
                list.add(tc);
            }
        }
        return list;
    }

    @Override
    public Long saveArticleTypeInfo(String typeId, String typeName) {
        ArticleType articleType = null;
        if (StringUtils.isEmpty(typeId)) {
            articleType = new ArticleType();
        } else {
            articleType = genericDao.getObject(ArticleType.class, typeId);
        }
        articleType.setTypeName(typeName);
        genericDao.saveOrUpdateObject(articleType);
        return articleType.getId();
    }
}
