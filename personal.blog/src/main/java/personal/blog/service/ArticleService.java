package personal.blog.service;

import personal.blog.util.PageSplitUtil;
import personal.blog.vo.Article;

public interface ArticleService {

    PageSplitUtil<Article> getArticleListForPage(Integer firstResult, Integer maxResults);

    Long saveArticleInfo(Long articleId, String title, String content, String typeStr);

    Article getArticleById(Long articleId);

    void deleteArticleById(Long articleId);

    void updateArticle(Article article);

}
