package personal.blog.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import personal.blog.constant.ApplicationConstant;
import personal.blog.enums.GlobalValueKey;


/**
 * spring上下文的辅助类.
 * 
 * @author Jack
 * 
 */
public final class WebApplicationContextUtil {

    private WebApplicationContextUtil() {

    }

    /**
     * 根据bean的名称获取在spring初始化的bean的实例.
     * 
     */
    public static final <T> T getBeanFromSpringContext(Class<T> cls, String beanName) {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();

        @SuppressWarnings("unchecked")
        T beanInstance = (T) wac.getBean(beanName);

        return beanInstance;
    }

    /**
     * 获取全局的值.
     */
    public static final String getGlobalValueFromApplication(GlobalValueKey keyName) {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        @SuppressWarnings("unchecked")
        Map<String, String> paramMap =
                (Map<String, String>) wac.getServletContext().getAttribute(ApplicationConstant.GLOBAL_PARAM);
        if (null == paramMap) {
            return null;
        }
        return paramMap.get(keyName.getKey());
    }

    /**
     * 设置全局的值.
     */
    public static final void setGlobalValueFromApplication(GlobalValueKey keyName, Object keyValue) {
        WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
        @SuppressWarnings("unchecked")
        Map<String, Object> paramMap =
                (Map<String, Object>) wac.getServletContext().getAttribute(ApplicationConstant.GLOBAL_PARAM);

        if (paramMap == null) {
            paramMap = new HashMap<String, Object>();
            wac.getServletContext().setAttribute(ApplicationConstant.GLOBAL_PARAM, paramMap);
        }

        paramMap.put(keyName.getKey(), keyValue);
    }


}
