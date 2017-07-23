package personal.blog.form;

import org.hibernate.validator.constraints.NotBlank;

public class PhotoForm {

    @NotBlank(message = "相册的标题不能为空")
    private String title;

    @NotBlank(message = "相册的分类不能为空")
    private String type;

    @NotBlank(message = "请上传相片")
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
