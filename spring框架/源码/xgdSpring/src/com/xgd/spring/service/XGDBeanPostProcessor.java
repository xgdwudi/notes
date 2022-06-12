package com.xgd.spring.service;

import com.xgd.spring.spring.BeanPostProcessor;
import com.xgd.spring.spring.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author wudidemiaoa
 * @date 2022/4/13
 * @apiNote
 */
@Component
public class XGDBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessorBeforeInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
//            bean
        }

        return bean;
    }

    @Override
    public Object postProcessorAfterInitialization(String beanName, Object bean) {
        if(beanName.equals("userService")){
//            bean
            Object proxyInstance = Proxy.newProxyInstance(XGDBeanPostProcessor.class.getClassLoader(), bean.getClass().getInterfaces(), new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                    System.out.println("qiemianluoji");
                    return method.invoke(bean,args);
                }
            });

            return proxyInstance;
        }
        return bean;
    }
}
