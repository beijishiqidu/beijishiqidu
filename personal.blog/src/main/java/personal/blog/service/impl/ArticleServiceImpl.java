package personal.blog.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import personal.blog.vo.ExecResult;

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
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getArticleTypeList'")
    public List<ArticleType> getArticleTypeList() {
        return genericDao.listWithCache(ArticleType.class);
    }

    @Override
    public ArticleType getArticleTypeById(String id) {
        return genericDao.getObject(ArticleType.class, id);
    }

    @Override
    @Cacheable(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getArticleTypeCount'")
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
    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    @CacheEvict(value = "org.hibernate.cache.internal.StandardQueryCache", key = "'getArticleTypeList'", allEntries = true)
    public ExecResult saveArticleTypeInfo(String typeId, String typeName) {

        ExecResult er = new ExecResult();
        if (StringUtils.isEmpty(typeName)) {
            er.setMessage("文章类型不能为空");
            return er;
        }

        if (StringUtils.isEmpty(typeId)) {
            List<ArticleType> list = getArticleTypeList();
            for (ArticleType at : list) {
                if (at.getTypeName().equals(typeName)) {
                    er.setMessage("该类型名称已经存在");
                    return er;
                }
            }
        }

        ArticleType articleType = null;
        if (StringUtils.isEmpty(typeId)) {
            articleType = new ArticleType();
        } else {
            articleType = genericDao.getObject(ArticleType.class, typeId);
        }
        articleType.setTypeName(typeName);
        genericDao.saveOrUpdateObject(articleType);

        er.setResult(true);
        er.setMessage("类型保存成功");
        er.getAppend().put("id", articleType.getId());

        return er;
    }

    @Transactional(rollbackFor = DataAccessException.class, propagation = Propagation.REQUIRED)
    @Override
    public ExecResult deleteArticleTypeById(String typeId) {
        ExecResult er = new ExecResult();

        // 查询有没有相册关联到此类型中，如果有，则提示不能删除.
        DetachedCriteria dc = DetachedCriteria.forClass(Article.class);

        if (StringUtils.isNotEmpty(typeId)) {
            dc.createAlias("type", "type");
            dc.add(Restrictions.eq("type.id", Long.valueOf(typeId)));
        }
        Long totalCount = genericDao.findCountByCriteria(dc);

        if (totalCount.intValue() > 0) {
            er.setResult(false);
            er.setMessage("本文章类型下还有文章");
        } else {
            genericDao.deleteObject(ArticleType.class, typeId);
            er.setResult(true);
        }

        return er;
    }
}
