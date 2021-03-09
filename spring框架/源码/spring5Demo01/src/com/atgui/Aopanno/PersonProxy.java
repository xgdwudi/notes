package com.atgui.Aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName PersonProxy
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/6 17:31
 **/
@Component
@Aspect
@Order(10)
public class PersonProxy {

    @Pointcut(value = "execution(* com.atgui.Aopanno.User.add(..))")
    public void pointdemo(){

    }

    // 前置通知
    @Before("execution(* com.atgui.Aopanno.User.add(..))")
    public void before(){
        System.out.println("before。。PersonProxy");
    }
    // 最终通知
    @After("execution(* com.atgui.Aopanno.User.add(..))")
    public void after(){
        System.out.println("after。。PersonProxy");
    }
    // 后置通知（返回通知）
    @AfterReturning("execution(* com.atgui.Aopanno.User.add(..))")
    public void AfterReturning(){
        System.out.println("AfterReturning。。PersonProxy");
    }

    @AfterThrowing("execution(* com.atgui.Aopanno.User.add(..))")
    public void AfterThrowing(){
        System.out.println("AfterThrowing。。PersonProxy");
    }
    @Around("execution(* com.atgui.Aopanno.User.add(..))")
    public void Around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("坏绕之前。。PersonProxy");
        proceedingJoinPoint.proceed();  // 调用这个方法
        System.out.println("坏绕之后。。PersonProxy");
    }
}
