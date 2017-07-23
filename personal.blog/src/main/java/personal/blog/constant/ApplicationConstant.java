package personal.blog.constant;

public final class ApplicationConstant {

    public static final String META_TITLE = "META_TITLE";

    public static final String KEY_WORDS = "KEY_WORDS";

    public static final String DESCRIPTIONS = "DESCRIPTIONS";

    /**
     * 保存在application作用域的参数的key值.
     */
    public static final String GLOBAL_PARAM = "GLOBAL_PARAM";

    /**
     * 企业入驻时装修公司的Id, 与basic_data中的ENTERPRISE_TYPE(13)装修公司Id一致.
     */
    public static final String BUILD_COMPANY = "82";

    /**
     * 企业入驻时装修公司的Id.
     */
    public static final Integer BUILD_COMPANY_INT = Integer.valueOf(82);

    /**
     * 系统默认陕西省.
     */
    public static final String PROVINCE_CODE = "610000";

    /**
     * 系统默认西安市.
     */
    public static final String CITY_CODE = "610100";

    /**
     * 通知页面中的红色字体.
     */
    public static final String RED_SPAN_START = "<span class=\"color-ff5d2c\">";

    /**
     * span的结束标记.
     */
    public static final String SPAN_END = "</span>";

    /**
     * 浏览次数在session中的标记.
     */
    public static final String SCAN_TIMES_POOL = "SCAN_TIMES_POOL";

    /**
     * 上传时的图片.
     */
    public static final String SESSION_PHOTO_UPLOAD_LIST = "SESSION_PHOTO_UPLOAD_LIST";
}
