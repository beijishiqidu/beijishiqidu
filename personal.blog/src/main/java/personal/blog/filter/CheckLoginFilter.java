package personal.blog.filter;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import personal.blog.enums.GlobalValueKey;
import personal.blog.util.EnvUtil;
import personal.blog.util.WebApplicationContextUtil;

public class CheckLoginFilter implements Filter {

    private static final Logger LOGGER = Logger.getLogger(CheckLoginFilter.class);
    
    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        request.setAttribute("releaseVersion", EnvUtil.getReleaseVersion());
        request.setAttribute("localEnv", EnvUtil.isLocalEnv());
        request.setAttribute("prodEnv", EnvUtil.isProdEnv());
        request.setAttribute("testIe8Env", EnvUtil.isTestIe8Env());
        
        String globalUrl = WebApplicationContextUtil.getGlobalValueFromApplication(GlobalValueKey.URL_PATH);
        if (globalUrl == null) {
            String tempContextUrl = null;
            if (EnvUtil.isLocalEnv()) {
                tempContextUrl = "//local.beijishiqidu.com";
            } else if (EnvUtil.isProdEnv()) {
                tempContextUrl = "//www.beijishiqidu.com";
            }

            // StringBuffer url = new StringBuffer(request.getRequestURL());
            // LOGGER.debug(url);
            // tempContextUrl =
            // url.delete(url.length() - request.getRequestURI().length(), url.length())
            // .append(request.getContextPath()).toString();
            WebApplicationContextUtil.setGlobalValueFromApplication(GlobalValueKey.URL_PATH, tempContextUrl);
            LOGGER.debug("Get the Domain URL:" + tempContextUrl);
        }

        LOGGER.debug("当前全局的urlPath为>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + globalUrl);

        if (EnvUtil.isLocalEnv()) {

            // 构造js和css文件的路径
            String resourcePath = CheckLoginFilter.class.getClassLoader().getResource("/").getPath();
            resourcePath = resourcePath.replaceAll("%20", " ");
            String[] paths = resourcePath.split("WEB-INF");
            File file = new File(paths[0]);

            StringBuilder javascriptPathBuilder = new StringBuilder();
            StringBuilder javascriptLibPathBuilder = new StringBuilder();
            StringBuilder stylePathBuilder = new StringBuilder();

            javascriptPathBuilder.append(file.getCanonicalPath()).append(File.separator).append("javascript")
                    .append(File.separator).append("app");
            javascriptLibPathBuilder.append(file.getCanonicalPath()).append(File.separator).append("javascript")
                    .append(File.separator).append("lib");
            stylePathBuilder.append(file.getCanonicalPath()).append(File.separator).append("style")
                    .append(File.separator).append("app");

            File[] cssAppFiles = new File(stylePathBuilder.toString()).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String tempFileName = pathname.getName().toLowerCase();
                    if (tempFileName.endsWith(".less")) {
                        return !EnvUtil.isTestIe8Env();
                    }
                    return EnvUtil.isTestIe8Env();
                }
            });

            File[] jsLibFiles = new File(javascriptLibPathBuilder.toString()).listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    String tempFileName = pathname.getName().toLowerCase();
                    if (tempFileName.matches(".*txt")) {
                        return false;
                    }
                    if (tempFileName.equalsIgnoreCase("less.min.js")) {
                        if (EnvUtil.isTestIe8Env()) {
                            return false;
                        }
                        return true;
                    }
                    return true;
                }
            });
            File[] jsAppFiles = new File(javascriptPathBuilder.toString()).listFiles();

            request.setAttribute("jsAppFiles", jsAppFiles);
            request.setAttribute("jsLibFiles", jsLibFiles);
            request.setAttribute("cssAppFiles", cssAppFiles);
        }
        
        String url = request.getRequestURI();
        String context = request.getContextPath();
        String filterUrl = "/admin/";

        if (url.startsWith(context + filterUrl)) {
            Object loginSign = session.getAttribute("hasLogin");
            if (loginSign == null) {
                response.sendRedirect(context + "/login.html");
            } else {
                chain.doFilter(request, response);
            }
        } else {
            chain.doFilter(request, response);
        }

        /**
         * 临时禁止缓存的做法，发布时需要清理掉.
         */
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

    }

}
