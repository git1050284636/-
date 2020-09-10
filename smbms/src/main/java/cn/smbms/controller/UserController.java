package cn.smbms.controller;

import cn.smbms.pojo.Role;
import cn.smbms.pojo.User;
import cn.smbms.service.role.RoleService;
import cn.smbms.service.user.UserService;
import cn.smbms.tools.Constants;
import cn.smbms.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;



    @RequestMapping("/main.html")
    public String main(HttpSession session) {
        if (session.getAttribute(Constants.USER_SESSION) == null) {
            return "redirect:/user/login.html";
        }
        return "frame";
    }


    /*局部异常*/
    /*@ExceptionHandler(value = {RuntimeException.class})

    public String handleException(RuntimeException e, HttpServletRequest request) {
        request.setAttribute("e", e);
        return "login";

    }*/

    @RequestMapping("/logout.html")
    public String logOut(HttpSession session) {
        session.removeAttribute(Constants.USER_SESSION);
        return "login";
    }


    @RequestMapping(value = "/userlist.html", method = {RequestMethod.GET, RequestMethod.POST})
    public String userList(
            @RequestParam(value = "queryname", defaultValue = "") String queryUserName,
            @RequestParam(value = "queryUserRole", defaultValue = "0") Integer queryUserRole,
            @RequestParam(value = "pageIndex", defaultValue = "1") Integer currentPageNo,
            Model model
    ) {

        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //总数量（表）
        int totalCount = userService.getUserCount(queryUserName, queryUserRole);
        //总页数
        PageSupport pages = new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);

        int totalPageCount = pages.getTotalPageCount();

        //控制首页和尾页
        if (currentPageNo < 1) {
            currentPageNo = 1;
        } else if (currentPageNo > totalPageCount) {
            currentPageNo = totalPageCount;
        }

        userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
        List<Role> roleList = roleService.getRoleList();

        model.addAttribute("userList", userList);
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);
        return "userlist";

    }

    @RequestMapping("/adduser.html")
    public String addUser(@ModelAttribute("user") User user) {
        return "useradd";
    }

    @RequestMapping("/addusersave.html")

    public String addUserSave(@ModelAttribute("user") @Valid User user,
                              BindingResult result, HttpSession session,
                              @RequestParam("a_idPicPath") MultipartFile multipartFile,
                              Model model
    ) {

        String savePath = null;
        if (!multipartFile.isEmpty()) {
            //上传准备工作
            //获取文件的原名和大小
            String oldName = multipartFile.getOriginalFilename();
            //后缀
            String ext = FilenameUtils.getExtension(oldName);
            long size = multipartFile.getSize();
            //上传图片不能查过500k
            if (size > 500 * 1024) {
                model.addAttribute("uploadFileError", "上传图片不能超过500k");
                return "useradd";
            } else {
                String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                if (!Arrays.asList(types).contains(ext)) {
                    model.addAttribute("uploadFileError", "上传图片格式不满足要求");
                    return "useradd";
                } else {
                    //正式上传
                    String targetPath = session.getServletContext().getRealPath("statics" + File.separator + "upload");
                    //修改上传文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_Personal." + ext;
                    File saveFile = new File(targetPath, fileName);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    try {
                        multipartFile.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadFileError", "上传失败，请联系管理员");
                        return "useradd";
                    }
                    savePath = targetPath + File.separator + fileName;
                }
            }
        }


        if (result.hasErrors()) {
            return "useradd";
        }
        user.setCreationDate(new Date());
        user.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        user.setIdPicPath(savePath);
        if (userService.add(user)) {
            /*if (false) {*/
            return "redirect:/user/userlist.html";
        } else {
            return "useradd";
        }
    }

    @RequestMapping(value = "/tomodify.html/{id}")
    public String toModify(@PathVariable String id, Model model
    ) {
        if (!StringUtils.isNullOrEmpty(id)) {
            User user = userService.getUserById(id);
            String str = user.getIdPicPath();


            if (str != null) {

                String strr = str.substring(73);
                model.addAttribute("str", strr);
            }


            model.addAttribute("user", user);
            /*  user.setIdPicPath(savePath);*/
            return "usermodify";
        } else {
            throw new RuntimeException("数据不存在");
        }
    }

    @RequestMapping("/modifysave.html")
    public String modifySave(User user, HttpServletRequest request, HttpSession session,
                             @RequestParam("a_idPicPath") MultipartFile multipartFile, Model model
    ) {

        String savePath = null;
        if (!multipartFile.isEmpty()) {
            //上传准备工作
            //获取文件的原名和大小
            String oldName = multipartFile.getOriginalFilename();
            //后缀
            String ext = FilenameUtils.getExtension(oldName);
            long size = multipartFile.getSize();
            //上传图片不能查过500k
            if (size > 500 * 1024) {
                model.addAttribute("uploadFileError", "上传图片不能超过500k");
                return "useradd";
            } else {
                String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                if (!Arrays.asList(types).contains(ext)) {
                    model.addAttribute("uploadFileError", "上传图片格式不满足要求");
                    return "useradd";
                } else {
                    //正式上传
                    String targetPath = session.getServletContext().getRealPath("statics" + File.separator + "upload");
                    //修改上传文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_Personal." + ext;
                    File saveFile = new File(targetPath, fileName);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    try {
                        multipartFile.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadFileError", "上传失败，请联系管理员");
                        return "useradd";
                    }
                    savePath = targetPath + File.separator + fileName;
                }
            }
        }

        user.setModifyBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        user.setModifyDate(new Date());
        user.setIdPicPath(savePath);
        if (userService.modify(user)) {
            return "redirect:/user/userlist.html";
        } else {
            return "usermodify";
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
    public String del(@RequestParam(value = "uid", defaultValue = "") String id) {
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
            if (userService.deleteUserById(delId)) {
                resultMap.put("delResult", "true");
            }  else {
                resultMap.put("delResult", "false");
            }
        }
        return JSON.toJSONString(resultMap);
    }

}
