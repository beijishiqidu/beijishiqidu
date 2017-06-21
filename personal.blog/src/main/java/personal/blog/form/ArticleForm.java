package personal.blog.form;

import org.hibernate.validator.constraints.NotBlank;

public class ArticleForm {

    @NotBlank(message = "文章的标题不能为空")
    private String title;

    @NotBlank(message = "文章的分类不能为空")
    private String type;

    @NotBlank(message = "文章的内容不能为空")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
