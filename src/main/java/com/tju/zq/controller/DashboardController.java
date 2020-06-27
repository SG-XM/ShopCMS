package com.tju.zq.controller;


import com.tju.zq.model.Stats;
import com.tju.zq.redis.RedisService;
import com.tju.zq.util.RunnableThreadWebCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 仪表板页面
 *
 * @param model
 * @return
 */

/**
 */
@Controller
public class DashboardController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/user/dashboard")
    public String dashboard(Model model, Stats stats) {
        return "redirect:itemManage_0_0_0";
    }

    public String getPer(long a, long b) {
        StringBuilder orderNumPer = new StringBuilder();
        double differ = a - b;
        double d = differ / a;
        String s = String.format("%.2f", d);
        orderNumPer.append(s).append("%");
        return orderNumPer.toString();
    }

    @RequestMapping(value = "/border/website/count/")
    @ResponseBody
    public int count(@RequestParam("key") String key) {
        return RunnableThreadWebCount.addCount(key);
    }
}