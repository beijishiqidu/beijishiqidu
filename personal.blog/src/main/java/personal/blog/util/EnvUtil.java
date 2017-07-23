package personal.blog.util;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EnvUtil extends GenericServlet {

    private static final long serialVersionUID = -3428563283363139472L;

    private static boolean localEnv;

    private static boolean prodEnv;

    private static boolean testIe8Env = false;

    private static String releaseVersion;

    private static String url;

    @Override
    public void init() throws ServletException {
        super.init();
        setLocalEnv(false);
        setProdEnv(false);
        setTestIe8Env(true);
        setReleaseVersion(String.valueOf(System.currentTimeMillis()));

        // 测试ie8的时候设置为true
        // setTestIe8Env(true);

        String os = System.getProperty("os.name");
        if (os.contains("Linux")) {
            setProdEnv(true);
            url = "//www.beijishiqidu.com/";
        } else {
            setLocalEnv(true);
            url = "//local.beijishiqidu.com/";
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

    }

    public static boolean isLocalEnv() {
        return localEnv;
    }

    public static void setLocalEnv(boolean localEnv) {
        EnvUtil.localEnv = localEnv;
    }

    public static boolean isProdEnv() {
        return prodEnv;
    }

    public static void setProdEnv(boolean prodEnv) {
        EnvUtil.prodEnv = prodEnv;
    }

    public static boolean isTestIe8Env() {
        return testIe8Env;
    }

    public static void setTestIe8Env(boolean testIe8Env) {
        EnvUtil.testIe8Env = testIe8Env;
    }

    public static String getReleaseVersion() {
        return releaseVersion;
    }

    public static void setReleaseVersion(String releaseVersion) {
        EnvUtil.releaseVersion = releaseVersion;
    }

    public static boolean isMainTomcat() {
        String url = EnvUtil.class.getClassLoader().getResource("/").getPath();

        Pattern p = null;
        if (EnvUtil.isLocalEnv()) {
            p = Pattern.compile("tomcat");
        } else {
            p = Pattern.compile("tomcat01");
        }

        Matcher m = p.matcher(url);
        return m.find();
    }

    public static String getUrl() {
        return url;
    }

}
