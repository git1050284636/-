package cn.smbms.controller;

import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
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
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/provider")
public class ProviderController {
    @Resource
    private ProviderService providerService;

    @RequestMapping("/providerlist.html")
    public String providerList(
            @RequestParam(value = "queryProName", defaultValue = "") String queryProName,
            @RequestParam(value = "queryProCode", defaultValue = "") String queryProCode,
            HttpServletRequest request
    ) {
        List<Provider> providerList = providerService.getProviderList(queryProName, queryProCode);
        request.setAttribute("providerList", providerList);
        request.setAttribute("queryProName", queryProName);
        request.setAttribute("queryProCode", queryProCode);
        return "providerlist";
    }

    @RequestMapping("/addprovider.html")
    public String addProvider(@ModelAttribute("provider") Provider provider

    ) {
        return "provideradd";

    }

    @RequestMapping("/addprovidersave.html")
    public String addProviderSave(@ModelAttribute("provider") @Valid Provider provider,
                                  BindingResult result, HttpSession session,
                                  @RequestParam("a_idPicPath") MultipartFile multipartFile,
                                  @RequestParam("a_workPicPath") MultipartFile workFile,
                                  Model model
    ) {

        String savePath = null;
        if (!multipartFile.isEmpty()) {
            //上传准备工作
            //获取文件的原名和大小
            String oldName = multipartFile.getOriginalFilename();
            long size = multipartFile.getSize();
            //后缀
            String ext = FilenameUtils.getExtension(oldName);

            //上传图片不超过500k
            if (size > 500 * 1024) {
                model.addAttribute("uploadFileError", "上传图片不能超过500k");
                return "provideradd";
            } else {
                String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                if (!Arrays.asList(types).contains(ext)) {
                    model.addAttribute("uploadFileError", "上传的图片格式不满足要求");
                    return "provideradd";
                } else {
                    //正式上传
                    String targetPath = session.getServletContext().getRealPath("statics" + File.separator + "upload");
                    //修改上传文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_work" + ext;
                    File saveFile = new File(targetPath, fileName);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    try {
                        multipartFile.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadFileError", "上传失败，请联系管理员");
                        return "provideradd";
                    }
                    savePath = targetPath + File.separator + fileName;

                }
            }
        }
        //多文件
        String workPath = null;
        if (!multipartFile.isEmpty()) {
            //上传准备工作
            //获取文件的原名和大小
            String oldName = workFile.getOriginalFilename();
            long size = workFile.getSize();
            //后缀
            String ext = FilenameUtils.getExtension(oldName);

            //上传图片不超过500k
            if (size > 500 * 1024) {
                model.addAttribute("uploadWorkFileError", "上传图片不能超过500k");
                return "provideradd";
            } else {
                String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                if (!Arrays.asList(types).contains(ext)) {
                    model.addAttribute("uploadWorkFileError", "上传的图片格式不满足要求");
                    return "provideradd";
                } else {
                    //正式上传
                    String targetPath = session.getServletContext().getRealPath("statics" + File.separator + "upload");
                    //修改上传文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_worktwo" + ext;
                    File saveFile = new File(targetPath, fileName);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    try {
                        workFile.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadWorkFileError", "上传失败，请联系管理员");
                        return "provideradd";
                    }
                    workPath = targetPath + File.separator + fileName;

                }
            }
        }












        if (result.hasErrors()) {
            return "provideradd";
        }

        provider.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        provider.setCreationDate(new Date());

        provider.setIdPicPath(savePath);
        provider.setWorkPicPath(workPath);

        boolean flag = false;
        flag = providerService.add(provider);
        if (flag) {
            return "redirect:/provider/providerlist.html";
        } else {
            return "provideradd";
        }
    }

    @RequestMapping("/tomodify.html/{id}")
    public String toModify(@PathVariable String id, Model model) {
        if (!StringUtils.isNullOrEmpty(id)) {
            Provider provider = providerService.getProviderById(id);
            String str = provider.getIdPicPath();
            String strw = provider.getWorkPicPath();

            if (strw != null) {
                String stww = strw.substring(73);
                model.addAttribute("stww", stww);
            }
            if (str != null) {
                String strr = str.substring(73);
                model.addAttribute("str", strr);
            }
            model.addAttribute("provider", provider);
            return "providermodify";
        } else {
            throw new RuntimeException("数据不存在");
        }
    }

    @RequestMapping("/modifysave.html")
    public String modifySave(Provider provider, HttpServletRequest request,HttpSession session,
                             @RequestParam("a_idPicPath") MultipartFile multipartFile, Model model,
                             @RequestParam("a_workPicPath") MultipartFile workFile) {
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
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_work." + ext;
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

        String workPath = null;
        if (!multipartFile.isEmpty()) {
            //上传准备工作
            //获取文件的原名和大小
            String oldName = workFile.getOriginalFilename();
            long size = workFile.getSize();
            //后缀
            String ext = FilenameUtils.getExtension(oldName);

            //上传图片不超过500k
            if (size > 500 * 1024) {
                model.addAttribute("uploadWorkFileError", "上传图片不能超过500k");
                return "provideradd";
            } else {
                String[] types = {"jpg", "jpeg", "png", "pneg", "gif"};
                if (!Arrays.asList(types).contains(ext)) {
                    model.addAttribute("uploadWorkFileError", "上传的图片格式不满足要求");
                    return "provideradd";
                } else {
                    //正式上传
                    String targetPath = session.getServletContext().getRealPath("statics" + File.separator + "upload");
                    //修改上传文件名
                    String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000) + "_worktwo" + ext;
                    File saveFile = new File(targetPath, fileName);
                    if (!saveFile.exists()) {
                        saveFile.mkdirs();
                    }
                    try {
                        workFile.transferTo(saveFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        model.addAttribute("uploadWorkFileError", "上传失败，请联系管理员");
                        return "provideradd";
                    }
                    workPath = targetPath + File.separator + fileName;

                }
            }
        }



        provider.setModifyBy(((User) request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        provider.setModifyDate(new Date());
        provider.setIdPicPath(savePath);
        provider.setWorkPicPath(workPath);
        boolean flag = false;
        flag = providerService.modify(provider);
        if (flag) {
            return "redirect:/provider/providerlist.html";
        } else {
            return "providermodify";
        }
    }

    @RequestMapping("/view/{id}")
    public String view(@PathVariable String id, Model model) {
        if (!StringUtils.isNullOrEmpty(id)) {
            Provider provider = providerService.getProviderById(id);
            model.addAttribute("provider", provider);
            return "providerview";
        } else {
            throw new RuntimeException("数据不存在");
        }
    }
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(@RequestParam(value = "id",defaultValue = "") String id) {
        if (!StringUtils.isNullOrEmpty(id)) {
            Provider provider = providerService.getProviderById(id);
            return JSON.toJSONString(provider);
        } else {
          return "null";
        }
    }






    @RequestMapping("/del")
    @ResponseBody
    public String del(@RequestParam(value = "proid",defaultValue = "")String id){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            int flag = providerService.deleteProviderById(id);
            if(flag == 0){//删除成功
                resultMap.put("delResult", "true");
            }else if(flag == -1){//删除失败
                resultMap.put("delResult", "false");
            }else if(flag > 0){//该供应商下有订单，不能删除，返回订单数
                resultMap.put("delResult", String.valueOf(flag));
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
      return JSON.toJSONString(resultMap);
    }

    @RequestMapping("/pcexists.html")
    @ResponseBody
    public String pcexists(@RequestParam(value = "proCode",defaultValue = "")String proCode){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(proCode)){
            resultMap.put("proCode", "exist");
        }else{
            Provider provider = providerService.selectProviderCodeExist(proCode);
            if(null != provider){
                resultMap.put("proCode","exist");
            }else{
                resultMap.put("proCode", "notexist");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

}
