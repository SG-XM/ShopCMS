package com.tju.zq.controller;

import com.mongodb.gridfs.GridFSDBFile;
import com.tju.zq.mapper.ItemCategoryMapper;
import com.tju.zq.mapper.ItemMapper;
import com.tju.zq.mapper.ItemOrderMapper;
import com.tju.zq.mapper.ReItemMapper;
import com.tju.zq.model.*;
import com.tju.zq.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.*;


/**
 * 商品管理
 */
@Controller
public class ItemController {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemOrderMapper itemOrderMapper;


    @Autowired
    private ItemCategoryMapper itemCategoryMapper;

    @Autowired
    private ReItemMapper reItemMapper;

    public static final String ROOT = "src/main/resources/static/img/item/";

    MongoUtil mongoUtil = new MongoUtil();

    private final ResourceLoader resourceLoader;

    @Autowired
    public ItemController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    List<ItemOrder> itemOrderList;
    List<Item> itemList;

    File getFile = null;


    @RequestMapping("/user/itemManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemManage(Item item, @PathVariable Integer pageCurrent,
                             @PathVariable Integer pageSize,
                             @PathVariable Integer pageCount,
                             Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;

        int rows = itemMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        itemList = itemMapper.list(item);
        for (Item i : itemList) {
            i.setUpdatedStr(DateUtil.getDateStr(i.getUpdated()));
        }
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setStart(0);
        itemCategory.setEnd(Integer.MAX_VALUE);
        List<ItemCategory> itemCategoryList = new ArrayList<>();//itemCategoryMapper.list(itemCategory);
        Integer minPrice = item.getMinPrice();
        Integer maxPrice = item.getMaxPrice();
        model.addAttribute("itemCategoryList", itemCategoryList);
        model.addAttribute("itemList", itemList);
        String pageHTML = PageUtil.getPageContent("itemManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getTitle() + "&cid=" + item.getCid() + "&minPrice" + minPrice + "&maxPrice" + maxPrice, pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "item/itemManage";
    }

    @RequestMapping("/user/itemOrderManage_{pageCurrent}_{pageSize}_{pageCount}")
    public String itemOrderManage(ItemOrder item, @PathVariable Integer pageCurrent,
                                  @PathVariable Integer pageSize,
                                  @PathVariable Integer pageCount,
                                  Model model) {
        if (pageSize == 0) pageSize = 50;
        if (pageCurrent == 0) pageCurrent = 1;

        int rows = itemOrderMapper.count(item);
        if (pageCount == 0) pageCount = rows % pageSize == 0 ? (rows / pageSize) : (rows / pageSize) + 1;
        item.setStart((pageCurrent - 1) * pageSize);
        item.setEnd(pageSize);
        itemOrderList = itemOrderMapper.list(item);
        int totalPrice = 0;
        int totalsell = 0;
        HashMap<String, Integer> m = new HashMap<>();
        for (ItemOrder p :
                itemOrderList) {
            m.put(p.getName(), 1);
            if (p.getTotal() != null && !p.getTotal().isEmpty()) {
                totalPrice += (Float.valueOf(p.getTotal()) - Float.valueOf(p.getCount()) * Float.valueOf(p.getPrice().toString()));
                totalsell += Float.valueOf(p.getTotal());
            }
        }

        model.addAttribute("totalCount", itemOrderList.size());
        model.addAttribute("totalCate", m.size());
        model.addAttribute("totalSell", totalsell);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("itemList", itemOrderList);
        String pageHTML = PageUtil.getPageContent("itemOrderManage_{pageCurrent}_{pageSize}_{pageCount}?title=" + item.getName(), pageCurrent, pageSize, pageCount);
        model.addAttribute("pageHTML", pageHTML);
        model.addAttribute("item", item);
        return "item/itemOrderManage";
    }

    @RequestMapping("/user/download1")
    public void postItemExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {

        //导出excel
        LinkedHashMap<String, String> fieldMap = new LinkedHashMap<String, String>();
        fieldMap.put("id", "商品id");
        fieldMap.put("title", "商品标题");
        fieldMap.put("sellPoint", "商品卖点");
        fieldMap.put("price", "商品价格");
        fieldMap.put("num", "库存数量");
        fieldMap.put("image", "商品图片");
        fieldMap.put("cid", "所属类目，叶子类目");
        fieldMap.put("status", "商品状态，1-正常，2-下架，3-删除");
        fieldMap.put("created", "创建时间");
        fieldMap.put("updated", "更新时间");
        String sheetName = "商品管理报表";
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment;filename=ItemManage.xls");//默认Excel名称
        response.flushBuffer();
        OutputStream fos = response.getOutputStream();
        try {
            ExcelUtil.listToExcel(itemList, fieldMap, sheetName, fos);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String imageName = null;

    @GetMapping("/user/itemEdit")
    public String itemEditGet(Model model, Item item) {
        ItemCategory itemCategory = new ItemCategory();
        itemCategory.setStart(0);
        itemCategory.setEnd(Integer.MAX_VALUE);
        List<ItemCategory> itemCategoryList = new ArrayList<>();
        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() != 0) {
            Item item1 = itemMapper.findById(item);
            String id = String.valueOf(item.getId());
            GridFSDBFile fileById = mongoUtil.getFileById(id);
            if (fileById != null) {
                StringBuilder sb = new StringBuilder(ROOT);
                imageName = fileById.getFilename();
                sb.append(imageName);
                try {
                    getFile = new File(sb.toString());
                    fileById.writeTo(getFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                item1.setImage(imageName);
            }
            model.addAttribute("item", item1);
        }
        return "item/itemEdit";
    }

    @ResponseBody
    @PostMapping("/user/itemOrderEditState")
    public ResObject<Object> itemOrderEditState(ItemOrder item1) {
        ItemOrder item = itemOrderMapper.selectByPrimaryKey(item1.getId());
        itemOrderMapper.deleteByPrimaryKey(item.getId());
        //userMapper.insert(reItem);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }

    @GetMapping("/user/itemOrderEdit")
    public String providerOrderEdit(Model model, ItemOrder item) {
//        model.addAttribute("itemCategoryList", itemCategoryList);
        if (item.getId() == null) item.setId(0);
        if (item.getId() != 0) {
            ItemOrder item1 = itemOrderMapper.selectByPrimaryKey(item.getId());
            model.addAttribute("p", item1);
        } else {
            model.addAttribute("p", item);
        }
        return "item/itemOrderEdit";
    }


    @PostMapping("/user/itemOrderEdit")
    public String providerOrderEditPost(Model model, ItemOrder item, HttpSession httpSession) {
        if (item.getId() != 0) {
            itemOrderMapper.updateByPrimaryKey(item);
        } else {
            itemOrderMapper.insert(item);
        }
        return "redirect:itemOrderManage_0_0_0";
    }

    @GetMapping("/user/buy_{name}_{count}_{price}_{discount}_{provider}_{address}_{info}")
    public String buy(Model model, @PathVariable String name, @PathVariable int count,
                      @PathVariable int price, @PathVariable Float discount,
                      @PathVariable String provider, @PathVariable String address, @PathVariable String info) {
        ItemOrder item = new ItemOrder();
        item.setAddress(address);
        item.setCount(count);
        item.setCustomer("test12");
        item.setInfo(info);
        item.setName(name);
        item.setProvider(provider);
        item.setPrice(BigDecimal.valueOf(price));
        item.setTime(new Date());
        item.setTotal(String.valueOf(price * count * discount / 10f));
        itemOrderMapper.insert(item);
        model.addAttribute("itemList", itemList);
        return "redirect:itemManage_0_0_0";
    }

    @PostMapping("/user/itemEdit")
    public String itemEditPost(Model model, HttpServletRequest request, Item item, HttpSession httpSession) {
        //根据时间和随机数生成id
        Date date = new Date();
        item.setCreated(date);
        item.setUpdated(date);
        item.setBarcode("");
        item.setImage("");
        int rannum = 0;

        if (item.getId() != 0) {
            itemMapper.update(item);
        } else {

            item.setId(rannum);
            itemMapper.insert(item);
        }
        return "redirect:itemManage_0_0_0";
    }

    @GetMapping(value = "/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile() {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(ROOT, imageName).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @ResponseBody
    @PostMapping("/user/itemEditState")
    public ResObject<Object> itemEditState(Item item1) {
        Item item = itemMapper.findById(item1);
        ReItem reItem = new ReItem();
        reItem.setId(item.getId());
        reItem.setBarcode(item.getBarcode());
        reItem.setCid(item.getCid());
        reItem.setImage(item.getImage());
        reItem.setPrice(item.getPrice());
        reItem.setNum(item.getNum());
        reItem.setSellPoint(item.getSellPoint());
        reItem.setStatus(item.getStatus());
        reItem.setTitle(item.getTitle());
        reItem.setRecovered(new Date());
        reItemMapper.insert(reItem);
        itemMapper.delete(item1);
        ResObject<Object> object = new ResObject<Object>(Constant.Code01, Constant.Msg01, null, null);
        return object;
    }
}
