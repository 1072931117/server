package com.lhx.manager.controller;

import com.alibaba.fastjson.JSON;
import com.lhx.manager.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by lhx on 2017/3/23.
 */
@Controller
public class TestController {
    @Autowired
    private TestService testService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        return JSON.toJSONString(testService.test());
    }

    @RequestMapping("/redis")
    @ResponseBody
    public String redis() {
        redisTemplate.opsForValue().set("redisKey123", UUID.randomUUID().toString(), 20, TimeUnit.SECONDS);
        return redisTemplate.opsForValue().get("redisKey123");
    }

    @RequestMapping("/html")
    public String html(Model model) {
        model.addAttribute("id", 123);
        model.addAttribute("userId", 456);
        return "testHtml";
    }

    @RequestMapping("/session")
    @ResponseBody
    public String session(HttpServletRequest request, HttpServletResponse response) {
        HttpSession httpSession = request.getSession();
        if (httpSession.getAttribute("lhxSession") == null)
            httpSession.setAttribute("lhxSession", UUID.randomUUID().toString());
        return redisTemplate.opsForValue().get("lhxSession");
    }


}
