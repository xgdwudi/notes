package com.xgd.spring.spring;

import com.sun.xml.internal.ws.util.StringUtils;

import java.beans.Introspector;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wudidemiaoa
 * @date 2022/4/10
 * @apiNote
 */
public class XgdApplicationContext {
    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitions = new ConcurrentHashMap<>();

    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    private List<BeanPostProcessor> beanDefinitionsList = new ArrayList<>();

    public XgdApplicationContext(Class configClass) throws Exception {
        this.configClass = configClass;
// 扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScans = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScans.value();  // 扫描路径 com.sgd.sprig.service

            path = path.replace(".", "/");
//            value
            ClassLoader classLoader = XgdApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);
            String path1 = resource.getPath();
            path1 = URLDecoder.decode(path1, "utf-8");
            File file = new File(path1);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File fs : files) {
                    String absoluteFile = fs.getAbsolutePath();
                    System.out.println(absoluteFile);
                    if (absoluteFile.endsWith(".class")) {
//                        通过反射去拿对象信息
                        String calssName = absoluteFile.substring(absoluteFile.indexOf("com"), absoluteFile.indexOf(".class"));
                        calssName = calssName.replace("\\", ".");
                        try {
                            Class<?> aClazz = classLoader.loadClass(calssName);
                            if (aClazz.isAnnotationPresent(Component.class)) {
                                if (BeanPostProcessor.class.isAssignableFrom(aClazz)) {
                                    Object o = aClazz.newInstance();
                                    beanDefinitionsList.add((BeanPostProcessor) o);
                                }

                                Component annotation1 = aClazz.getAnnotation(Component.class);
                                String value = annotation1.value();
                                if ("".equals(value)) {
                                    value = Introspector.decapitalize(aClazz.getSimpleName());
                                }
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(aClazz);
                                if (aClazz.isAnnotationPresent(Scope.class)) {
                                    Scope annotation = aClazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(annotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitions.put(value, beanDefinition);

                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

//         创建单例对象
        for (String beanName : beanDefinitions.keySet()) {
            BeanDefinition beanDefinition = beanDefinitions.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object o = clazz.getConstructor().newInstance();

//       依赖注入
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                    f.set(o, getBean(f.getName()));
                }
            }

            if (o instanceof BeanNameAware) {
                ((BeanNameAware) o).setBeanName(beanName);
            }

            for (BeanPostProcessor beanPostProcessor : beanDefinitionsList) {
                o = beanPostProcessor.postProcessorBeforeInitialization(beanName, o);
            }

//            初始化方法
            if (o instanceof InitializingBean) {
                ((InitializingBean) o).afterPropertiesSet();
            }

            for (BeanPostProcessor beanPostProcessor : beanDefinitionsList) {
                o = beanPostProcessor.postProcessorAfterInitialization(beanName, o);
            }

//            初始化后 Aop  BeanPostProcessor
            return o;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    bean = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, bean);
                }
                return bean;
            } else {
//                多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}
