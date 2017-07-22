package personal.blog.form;

import org.hibernate.validator.constraints.NotBlank;

public class PhotoForm {

    @NotBlank(message = "相册的标题不能为空")
    private String title;

    @NotBlank(message = "相册的分类不能为空")
    private String type;

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
}
