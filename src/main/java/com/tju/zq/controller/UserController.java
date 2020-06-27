package com.tju.zq.controller;

import com.tju.zq.mapper.UserMapper;
import com.tju.zq.model.ResObject;
import com.tju.zq.model.User;
import com.tju.zq.util.Constant;
import com.tju.zq.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 用户管理
 */
@Controller
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JavaMailSender mailSender; //自动注入的Bean

    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    List<User> itemList;
    /**
     * 登录跳转
     *
     * @param model
     * @return
     */
    @GetMapping("/user/login")
    public String loginGet(Model model) {
        return "login";
    }

    @PostMapping("/user/login")
    public String loginPost(User user, Model model) {
        User user1 = userMapper.selectByNameAndPwd(user);
        if (user1 != null) {
            httpSession.setAttribute("user", user1);
            User name = (User) httpSession.getAttribute("user");
            return "redirect:dashboard";
        } else {
            model.addAttribute("error", "用户名或密码错误，请重新登录！");
            return "login";
        }
    }

    @GetMapping("/user/register")
    public String register(Model model) {
        return "register";
    }

    @PostMapping("/user/register")
    public String registerPost(User user, Model model) {
        System.out.println("用户名" + user.getUserName());
        try {
            userMapper.selectIsName(user);
            model.addAttribute("error", "该账号已存在！");
        } catch (Exception e) {
            Date date = new Date();
            user.setAddDate(date);
            user.setDiscount(10);
            user.setUpdateDate(date);
            userMapper.insert(user);
            System.out.println("注册成功");
            model.addAttribute("error", "恭喜您，注册成功！");
            return "login";
        }

        return "register";
    }


    @RequestMapping("/user/userDownload")
    public void postProviderOrderExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("id", "id");
        fieldMap.put("userName", "用戶名");
        fieldMap.put("password", "密码");
        fieldMap.put("realName", "真实姓名");
        fieldMap.put("business", "角色");
        fieldMap.put("email", "邮箱");
        fieldMap.put("addDate", "创建时间");
        String sheetName = "顾客报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=providerOrder.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(itemList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/user/userManage")
    public String userManageGet(Model model) {
        User user = (User) httpSession.getAttribute("user");
        User user1 = userMapper.selectByNameAndPwd(user);
        model.addAttribute("user", user1);
        return "user/userManage";
    }

    @PostMapping("/user/userManage")
    public String userManagePost(Model model, User user, HttpSession httpSession) {
        Date date = new Date();
        user.setUpdateDate(date);
        int i = userMapper.update(user);
        httpSession.setAttribute("user",user);
        return "redirect:userManage";
    }
    @RequestMapping("/user/customerManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemManage(User item, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;

        int rows = userMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        itemList = userMapper.list(item);

        model.addAttribute("itemList", itemList);
        String pageHTML = "";//PageUtil.getPageContent("customerManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getTitle() + "&cid=" + item.getCid() + "&minPrice" + minPrice + "&maxPrice" + maxPrice, pageCurrent, pageSize, pageCount);
       // model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "user/customerManage";
    }
    @ResponseBody
    @PostMapping("/user/customerEditState")
    public ResObject<Object> itemEditState(User item1) {
        User item = userMapper.findById(item1);
        User reItem = new User(item);
        userMapper.delete(item1);
        //userMapper.insert(reItem);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

    @GetMapping("/user/customerEdit")
    public String customerEditGet(Model model, User item) {
//        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() != 0) {
            User item1 = userMapper.findById(item);
            model.addAttribute("user", item1);
        }
        return "user/customerEdit";
    }
    @PostMapping("/user/customerEdit")
    public String customerEditPost(Model model, User user, HttpSession httpSession) {
        Date date = new Date();
        user.setUpdateDate(date);
        int i = userMapper.update(user);
        //httpSession.setAttribute("user",user);
        model.addAttribute("user", user);
        return "user/customerEdit";
    }

}
