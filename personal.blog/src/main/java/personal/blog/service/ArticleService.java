package personal.blog.service;

import java.util.List;

import personal.blog.cache.TypeCount;
import personal.blog.form.ArticleForm;
import personal.blog.form.FormAlert;
import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;
import personal.blog.vo.ArticleType;
import personal.blog.vo.ExecResult;

public interface ArticleService {

    PageSplitUtil<Article> getArticleListForPage(Integer firstResult, Integer maxResults, String typeId);

    Article getArticleById(Long articleId);

    void deleteArticleById(Long articleId);

    void updateArticle(Article article);

    List<FormAlert> validateArticleForm(ArticleForm articleForm);

    Long saveArticleInfo(String articleId, String title, String content, String type);

    List<ArticleType> getArticleTypeList();

    List<TypeCount> getArticleTypeCount();

    ExecResult saveArticleTypeInfo(String typeId, String typeName);

    ArticleType getArticleTypeById(String id);

    ExecResult deleteArticleTypeById(String typeId);

}
