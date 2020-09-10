package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class RoleController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;


    @RequestMapping(value = "/rolelist.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String roleList(Model model) {

        List<Role> rolelist = null;
        rolelist = roleService.getRoleList();
        model.addAttribute("rolelist", rolelist);
        return "rolelist";

    }

    @RequestMapping("/addrole.html")
    public String addRole(@ModelAttribute("role") Role role) {
        return "roleadd";
    }

    @RequestMapping("/addrolesave.html")

    public String addRoleSave(@ModelAttribute("role") @Valid Role role,
                              BindingResult result, HttpSession session,
                              Model model
    ) {

        if (result.hasErrors()) {
            return "useradd";
        }
        role.setCreationDate(new Date());
        role.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());

        if (roleService.add(role)) {
            /*if (false) {*/
            return "redirect:/role/rolelist.html";
        } else {
            return "roleadd";
        }
    }

    @RequestMapping(value = "/tomodify.html/{id}")
    public String toModify(@PathVariable String id, Model model
    ) {
        if (!StringUtils.isNullOrEmpty(id)) {
            Role role = roleService.getRoleById(id);
            model.addAttribute("role", role);
            return "rolemodify";
        } else {
            throw new RuntimeException("数据不存在");
        }
    }

    @RequestMapping("/modifysave.html")
    public String modifySave(Role role, HttpServletRequest request, HttpSession session,
                             Model model
    ) {
        role.setModifyBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        role.setModifyDate(new Date());
        if (roleService.modify(role)) {
            return "redirect:/role/rolelist.html";
        } else {
            return "rolemodify";
        }
    }

    @RequestMapping(value = "/view/{id}")
    public String View(@PathVariable String id, Model model) {
        if (!StringUtils.isNullOrEmpty(id)) {
            User user = userService.getUserById(id);
            model.addAttribute("user", user);
            return "userview";
        } else {
            throw new RuntimeException("数据不存在");
        }
    }

    @RequestMapping(value = "/view", produces = {"application/json;charset=utf-8"})
    @ResponseBody
    public String View(@RequestParam(value = "id", defaultValue = "") String id) {
        if (!StringUtils.isNullOrEmpty(id)) {
            User user = userService.getUserById(id);
            return JSON.toJSONString(user);
        } else {
            return "null";
        }
    }

    @RequestMapping("/pwdmodify")
    public String pwdModify() {
        return "pwdmodify";
    }

    @RequestMapping("/tomodifypwd.html")
    @ResponseBody
    public String toModifypwd(Model model, @RequestParam(value = "oldpassword", defaultValue = "") String oldpassword,
                              HttpSession session) {
        Object o = session.getAttribute(Constants.USER_SESSION);
        Map<String, String> resultMap = new HashMap<String, String>();
        if (null == o) {//session过期
            resultMap.put("result", "sessionerror");
        } else if (StringUtils.isNullOrEmpty(oldpassword)) {//旧密码输入为空
            resultMap.put("result", "error");
        } else {
            String sessionPwd = ((User) o).getUserPassword();
            if (oldpassword.equals(sessionPwd)) {
                resultMap.put("result", "true");
            } else {//旧密码输入不正确
                resultMap.put("result", "false");
            }
        }
        return JSON.toJSONString(resultMap);
    }


    @RequestMapping("/pwdexists.html")
    public String pwdexists(@RequestParam(value = "newpassword", defaultValue = "") String newpassword,
                            HttpServletRequest request) {
        Object o = request.getSession().getAttribute(Constants.USER_SESSION);
        boolean flag = false;
        if (o != null && !StringUtils.isNullOrEmpty(newpassword)) {
            flag = userService.updatePwd(((User) o).getId(), newpassword);
            if (flag) {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码成功,请退出并使用新密码重新登录！");
                request.getSession().removeAttribute(Constants.USER_SESSION);//session注销
            } else {
                request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
            }
        } else {
            request.setAttribute(Constants.SYS_MESSAGE, "修改密码失败！");
        }
        return "pwdmodify";
    }

    @RequestMapping("/ucexists.html")
    @ResponseBody
    public String ucexists(@RequestParam(value = "userCode", defaultValue = "") String userCode) {
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (StringUtils.isNullOrEmpty(userCode)) {
            resultMap.put("userCode", "exist");
        } else {
            User user = userService.selectUserCodeExist(userCode);
            if (null != user) {
                resultMap.put("userCode", "exist");
            } else {
                resultMap.put("userCode", "notexist");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

    @RequestMapping("/rolelist")
    @ResponseBody
    public String roleList() {
        List<Role> roleList = null;
        roleList = roleService.getRoleList();
        return JSON.toJSONString(roleList);
    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(@RequestParam(value = "delId", defaultValue = "") String id) {
        Integer delId = 0;
        try {
            delId = Integer.parseInt(id);
        } catch (Exception e) {
            // TODO: handle exception
            delId = 0;
        }
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if (delId <= 0) {
            resultMap.put("delResult", "notexist");
        } else {
            if (roleService.deleteRoleById(delId)) {
                resultMap.put("delResult", "true");
            } else {
                resultMap.put("delResult", "false");
            }
        }
        return JSON.toJSONString(resultMap);
    }
}
