package com.xgd.spring.spring;

/**
 * @author wudidemiaoa
 * @date 2022/4/13
 * @apiNote
 */
public interface BeanPostProcessor {
    Object postProcessorBeforeInitialization(String beanName,Object bean);

    Object postProcessorAfterInitialization(String beanName,Object bean);
}
