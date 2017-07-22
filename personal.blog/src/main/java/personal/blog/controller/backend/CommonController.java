package personal.blog.controller.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.cache.TypeCount;
import personal.blog.service.ArticleService;
import personal.blog.service.CommonService;
import personal.blog.service.LoginService;
import personal.blog.service.PhotoService;

@Controller
public class CommonController {

    private static final Logger LOGGER = Logger.getLogger(CommonController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private CommonService commonService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView forwardToLoginPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

        LOGGER.info("====>" + userName);

        boolean result = loginService.checkUserNameAndPassWord(userName, passWord);

        Map<String, Object> resultMap = new HashMap<String, Object>();
        if (!result) {
            resultMap.put("result", false);
            resultMap.put("msg", "用户名或密码不正确，请重试。");
        } else {
            resultMap.put("result", true);
            resultMap.put("msg", "登陆成功");
            HttpSession session = request.getSession();
            session.setAttribute("hasLogin", true);
            session.setAttribute("userName", userName);
        }

        return resultMap;
    }

    @RequestMapping(value = "/admin/home.html", method = RequestMethod.GET)
    public ModelAndView forwardAdminHomePage() {
        return new ModelAndView("admin/home");
    }

    @RequestMapping(value = "/loginout.do", method = RequestMethod.GET)
    public ModelAndView execLogoutProcess(HttpServletRequest request) {
        request.getSession().invalidate();
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/admin/type/list/article", method = RequestMethod.GET)
    public ModelAndView forwardArticleTypeListPage() {
        ModelAndView mav = new ModelAndView("admin/article_type_list");
        List<TypeCount> psu = articleService.getArticleTypeCount();
        mav.addObject("typeList", psu);
        return mav;
    }

    @RequestMapping(value = "/admin/type/list/photo", method = RequestMethod.GET)
    public ModelAndView forwardPhotoTypeListPage() {
        ModelAndView mav = new ModelAndView("admin/photo_type_list");
        List<TypeCount> psu = photoService.getPhotoTypeCount();
        mav.addObject("typeList", psu);
        return mav;
    }

    @RequestMapping(value = "/admin/type/add/article", method = RequestMethod.GET)
    public ModelAndView forwardAddArticleTypePage(String typeId, String typeName, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("upload");
        return mav;
    }

    @RequestMapping(value = "/admin/type/add/photo", method = RequestMethod.GET)
    public ModelAndView forwardAddPhotoTypePage(String typeId, String typeName, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("upload");
        return mav;
    }
}
