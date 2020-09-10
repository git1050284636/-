package cn.smbms.controller;

import cn.smbms.pojo.User;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
@Controller
public class LoginControler {
    @Resource
    private UserService userService;


    @RequestMapping("/login.html")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/dologin.html")
    public String doLogin(@RequestParam("userCode") String userCode,
                          @RequestParam("userPassword") String userPassword,
                          HttpSession session,
                          Model model) {
        //调用service方法，进行用户匹配
        User user = userService.login(userCode);
        if (null != user) {//存在用户

            if (user.getUserPassword().equals(userPassword)) {
                //放入session
                session.setAttribute(Constants.USER_SESSION, user);
                //页面跳转（frame.jsp）
                return "redirect:/user/main.html";
            } else {
                throw new RuntimeException("密码不正确");
            }

        } else {
            throw new RuntimeException("用户名不正确");
        }



    }
    @RequestMapping("/logout.html")
    public String logOut(HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }
}
