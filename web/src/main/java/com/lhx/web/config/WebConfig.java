/*
 * 地址：杭州市萧山区风情大道与建设一路交叉口中栋国际北干科创园1403
 * 联系电话：0571-82757223
 * 邮箱：zhuwenli@readyidu.comvv
 * 版权所有：杭州益读科技有限公司
 */

package com.lhx.web.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

/**
 * Created by lhx on 2017/4/17.
 */
@Configurable
@ComponentScan("com.lhx.web.controller")
@Import(DubboConsumerConfig.class)
public class WebConfig {

}
