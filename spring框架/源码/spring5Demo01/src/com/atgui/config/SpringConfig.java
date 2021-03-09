package com.atgui.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName SpringConfig
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 13:27
 **/
@Configuration  // 加此注解，开启配置类，替代Xml配置文件
@ComponentScan(basePackages = {"com"})
//<!--    开启Aspect生成代理对象-->
//<aop:aspectj-autoproxy></aop:aspectj-autoproxy>
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringConfig {

}
