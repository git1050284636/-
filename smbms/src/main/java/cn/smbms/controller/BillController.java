package cn.smbms.controller;

import cn.smbms.dao.bill.BillMapper;
import cn.smbms.dao.provider.ProviderMapper;
import cn.smbms.pojo.Bill;
import cn.smbms.pojo.Provider;
import cn.smbms.pojo.User;
import cn.smbms.service.bill.BillService;
import cn.smbms.service.provider.ProviderService;
import cn.smbms.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/bill")
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    @Autowired
    private BillMapper billMapper;
    @Autowired
    private ProviderMapper providerMapper;

    @RequestMapping("/billlist.html")
    public String billList(
            @RequestParam(value = "queryProductName", defaultValue = "") String queryProductName,
            @RequestParam(value = "queryProviderId", defaultValue = "0") Integer queryProviderId,
            @RequestParam(value = "queryIsPayment", defaultValue = "0") Integer queryIsPayment,
            HttpServletRequest request
    ) {

        List<Provider> providerList = providerService.getProviderList("", "");
        request.setAttribute("providerList", providerList);

        Bill bill = new Bill();

        bill.setProductName(queryProductName);
        bill.setIsPayment(queryIsPayment);
        bill.setProviderId(queryProviderId);

        List<Bill> billList = billService.getBillList(bill);
        request.setAttribute("billList", billList);

        request.setAttribute("queryProductName", queryProductName);
        request.setAttribute("queryProviderId", queryProviderId);
        request.setAttribute("queryIsPayment", queryIsPayment);
        return "billlist";
    }

    @RequestMapping("/addbill.html")
    public String addBill(@ModelAttribute("bill") Bill bill) {
        return "billadd";
    }

    @RequestMapping("/addbillsave.html")
    public String addBillSave(@ModelAttribute("bill") @Valid Bill bill, BindingResult result,
                              HttpSession session) {
        if (result.hasErrors()){
            return "billadd";
        }
        bill.setCreatedBy(((User) session.getAttribute(Constants.USER_SESSION)).getId());
        bill.setCreationDate(new Date());
        boolean flag = false;
        flag = billService.add(bill);
        System.out.println("add flag -- > " + flag);
        if (flag) {
            return "redirect:/bill/billlist.html";
        } else {
            return "billadd";
        }

    }
    @RequestMapping("/tomodify.html/{id}")
    public String toModify(@PathVariable String id, Model model){
        if(!StringUtils.isNullOrEmpty(id)) {
            Bill bill = null;
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
            return "billmodify";
        }else {
            throw new RuntimeException("数据不存在");
        }
    }
    @RequestMapping("/modifysave.html")
    public String modifySave(Bill bill,HttpServletRequest request){
        bill.setModifyBy(((User)request.getSession().getAttribute(Constants.USER_SESSION)).getId());
        bill.setModifyDate(new Date());
        boolean flag = false;
        flag = billService.modify(bill);
        if(flag){
           return "redirect:/bill/billlist.html";
        }else{
           return "billmodify";
        }
    }
    @RequestMapping("/view/{id}")
    public String view(@PathVariable String id,Model model){
        if(!StringUtils.isNullOrEmpty(id)) {
            Bill bill = null;
            bill = billService.getBillById(id);
            model.addAttribute("bill", bill);
            return "billview";
        }else {
            throw new RuntimeException("数据不存在");
        }
    }
    @RequestMapping(value = "/view")
    @ResponseBody
    public String view(@RequestParam(value = "id",defaultValue = "") String id){
        if(!StringUtils.isNullOrEmpty(id)) {
            Bill bill = null;
            bill = billService.getBillById(id);
            return JSON.toJSONString(bill);
        }else {
            return "null";
        }
    }


    @RequestMapping("/providerlist")
    @ResponseBody
    public String providerList(){
        List<Provider> providerList = new ArrayList<Provider>();
        providerList = providerService.getProviderList("","");

        return JSON.toJSONString(providerList);

    }

    @RequestMapping("/del")
    @ResponseBody
    public String del(@RequestParam(value = "billid",defaultValue = "")String id){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(!StringUtils.isNullOrEmpty(id)){
            boolean flag = billService.deleteBillById(id);
            if(flag){//删除成功
                resultMap.put("delResult", "true");
            }else{//删除失败
                resultMap.put("delResult", "false");
            }
        }else{
            resultMap.put("delResult", "notexit");
        }
        return JSON.toJSONString(resultMap);

    }



    @RequestMapping("/bcexists.html")
    @ResponseBody
    public String bcexists(@RequestParam(value = "billCode",defaultValue = "")String billCode){
        HashMap<String, String> resultMap = new HashMap<String, String>();
        if(StringUtils.isNullOrEmpty(billCode)){
            resultMap.put("billCode", "exist");
        }else{
            Bill bill = billService.selectBillCodeExist(billCode);
            if(null != bill){
                resultMap.put("billCode","exist");
            }else{
                resultMap.put("billCode", "notexist");
            }
        }
        return JSONArray.toJSONString(resultMap);
    }

}
