package com.tju.zq.controller;


import com.tju.zq.mapper.ProviderItemMapper;
import com.tju.zq.mapper.ProviderMapper;
import com.tju.zq.mapper.ProviderOrderMapper;
import com.tju.zq.model.Provider;
import com.tju.zq.model.ProviderItem;
import com.tju.zq.model.ProviderOrder;
import com.tju.zq.model.ResObject;
import com.tju.zq.util.Constant;
import com.tju.zq.util.ExcelUtil;
import com.tju.zq.util.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


@Controller
public class ProviderController {

    List<Provider> providerList;
    List<ProviderItem> providerItemList;
    List<ProviderOrder> providerOrderList;




    @Autowired
    private ProviderMapper providerMapper;

    @Autowired
    private ProviderItemMapper providerItemMapper;

    @Autowired
    private ProviderOrderMapper providerOrderMapper;


    @RequestMapping("/user/providerItemManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String providerItemManage(ProviderItem item, @PathVariable Integer pageCurrent,
                                     @PathVariable Integer pageSize,
                                     @PathVariable Integer pageCount,
                                     Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;
        int rows = providerItemMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        int totalPrice = 0;
        HashMap<String, Integer> m = new HashMap<>();
        providerItemList = providerItemMapper.list(item);
//        for (ProviderOrder p :
//                providerOrderList) {
//            m.put(p.getProvider(), 1);
//            totalPrice += Float.valueOf(p.getTotal());
//        }

        model.addAttribute("itemList", providerItemList);
        model.addAttribute("totalCount", providerItemList.size());
//        model.addAttribute("totalProvider", m.size());
//        model.addAttribute("totalPrice", totalPrice);
        String pageHTML = PageUtil.getPageContent("providerItemManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "provider/providerItemManage";
    }

    @RequestMapping("/user/providerManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String providerManage(Provider item, @PathVariable Integer pageCurrent,
                                 @PathVariable Integer pageSize,
                                 @PathVariable Integer pageCount,
                                 Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;

        int rows = providerMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        providerList = providerMapper.list(item);
        model.addAttribute("itemList", providerList);
        String pageHTML = PageUtil.getPageContent("providerManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "provider/providerManage";
    }


    @RequestMapping("/user/providerOrderManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String providerOrderManage(ProviderOrder item, @PathVariable Integer pageCurrent,
                                      @PathVariable Integer pageSize,
                                      @PathVariable Integer pageCount,
                                      Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;

        int rows = providerOrderMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        providerOrderList = providerOrderMapper.list(item);
        int totalPrice = 0;
        HashMap<String, Integer> m = new HashMap<>();
        for (ProviderOrder p :
                providerOrderList) {
            m.put(p.getProvider(), 1);
            if (p.getTotal() != null && !p.getTotal().isEmpty())
                totalPrice += Float.valueOf(p.getTotal());
        }
        model.addAttribute("totalCount", providerOrderList.size());
        model.addAttribute("totalProvider", m.size());
        model.addAttribute("totalPrice", totalPrice);

        model.addAttribute("itemList", providerOrderList);
        String pageHTML = PageUtil.getPageContent("providerOrderManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "provider/providerOrderManage";
    }

    @RequestMapping("/user/providerOrder")
    public void postProviderOrderExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("id", "订单id");
        fieldMap.put("name", "货物名称");
        fieldMap.put("provider", "供应商");
        fieldMap.put("count", "进货数量");
        fieldMap.put("price", "单价");
        fieldMap.put("total", "总价");
        fieldMap.put("sdes", "状态");
        fieldMap.put("time", "签订时间");
        String sheetName = "进货管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=providerOrder.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(providerOrderList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @GetMapping("/user/providerOrderEdit")
    public String providerOrderEdit(Model model, ProviderOrder item) {
//        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() == null) item.setId(0);
        if (item.getId() != 0) {
            ProviderOrder item1 = providerOrderMapper.selectByPrimaryKey(item.getId());
            model.addAttribute("p", item1);
        } else {
            model.addAttribute("p", item);
        }
        return "provider/providerOrderEdit";
    }


    @PostMapping("/user/providerOrderEdit")
    public String providerOrderEditPost(Model model, ProviderOrder item, HttpSession httpSession) {
        if (item.getId() != 0) {
            providerOrderMapper.updateByPrimaryKey(item);
        } else {
            providerOrderMapper.insert(item);
        }
        return "redirect:providerOrderManage_0_0_0";
    }

    @GetMapping("/user/providerItemEdit")
    public String providerItemEdit(Model model, ProviderItem item) {
//        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() == null) item.setId(0);
        if (item.getId() != 0) {
            ProviderItem item1 = providerItemMapper.selectByPrimaryKey(item.getId());
            model.addAttribute("p", item1);
        } else {
            model.addAttribute("p", item);
        }
        return "provider/providerItemEdit";
    }

    @PostMapping("/user/providerItemEdit")
    public String providerItemEditPost(Model model, ProviderItem item, HttpSession httpSession) {
        if (item.getId() != 0) {
            providerItemMapper.updateByPrimaryKey(item);
        } else {
            providerItemMapper.insert(item);
        }
        return "redirect:providerItemManage_0_0_0";
    }

    @GetMapping("/user/providerEdit")
    public String providerEditGet(Model model, Provider item) {
//        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() == null) item.setId(0);
        if (item.getId() != 0) {
            Provider item1 = providerMapper.selectByPrimaryKey(item.getId());
        } else {
            model.addAttribute("p", item);
        }

        return "provider/providerEdit";
    }


    @PostMapping("/user/providerEdit")
    public String providerEditPost(Model model, Provider item, HttpSession httpSession) {

        if (item.getId() != 0) {
            providerMapper.updateByPrimaryKey(item);
        } else {
            providerMapper.insert(item);
        }
        return "redirect:providerManage_0_0_0";
    }


    @ResponseBody
    @PostMapping("/user/providerOrderEditState")
    public ResObject<Object> providerOrderEditState(ProviderOrder item1) {
        ProviderOrder item = providerOrderMapper.selectByPrimaryKey(item1.getId());
        providerOrderMapper.deleteByPrimaryKey(item.getId());
        //userMapper.insert(reItem);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

    @ResponseBody
    @PostMapping("/user/providerItemEditState")
    public ResObject<Object> providerItemEditState(ProviderItem item1) {
        ProviderItem item = providerItemMapper.selectByPrimaryKey(item1.getId());
        providerItemMapper.deleteByPrimaryKey(item.getId());
        //userMapper.insert(reItem);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

    @ResponseBody
    @PostMapping("/user/providerEditState")
    public ResObject<Object> providerEditState(Provider item1) {
        Provider item = providerMapper.selectByPrimaryKey(item1.getId());
        providerMapper.deleteByPrimaryKey(item.getId());
        //userMapper.insert(reItem);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

}
