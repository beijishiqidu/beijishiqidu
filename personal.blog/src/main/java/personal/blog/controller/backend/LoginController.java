package personal.blog.controller.backend;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import personal.blog.service.LoginService;

@Controller
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public ModelAndView forwardToLoginPage() {
        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login.do", method = RequestMethod.POST)
    @ResponseBody
    public Object login(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String userName = request.getParameter("userName");
        String passWord = request.getParameter("passWord");

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

    @RequestMapping(value = "/loginOut.do", method = RequestMethod.GET)
    public ModelAndView execLogoutProcess(HttpServletRequest request) {
        request.getSession().invalidate();;
        return new ModelAndView("login");
    }
}
