/*
 * 地址：杭州市萧山区风情大道与建设一路交叉口中栋国际北干科创园1403
 * 联系电话：0571-82757223
 * 邮箱：zhuwenli@readyidu.comvv
 * 版权所有：杭州益读科技有限公司
 */

package com.lhx.web.controller;

import com.lhx.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by lhx on 2017/4/17.
 */
@Controller
public class DubboDemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("/test")
    @ResponseBody
    public String dubboTest(String name){
        return demoService.sayHello(name);
    }
}
