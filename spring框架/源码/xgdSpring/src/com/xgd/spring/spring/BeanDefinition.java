package com.xgd.spring.spring;

/**
 * @author wudidemiaoa
 * @date 2022/4/10
 * @apiNote
 */
public class BeanDefinition {
    private Class type;

    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}
