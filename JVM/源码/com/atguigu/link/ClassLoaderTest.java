package com.atguigu.link;

public class ClassLoaderTest {
    public static void main(String[] args) {
//        获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        System.out.println(systemClassLoader);
//        获取器上层扩展类加载器
        ClassLoader parent = systemClassLoader.getParent();
        System.out.println(parent);
        //        获取器上层扩展类加载器  获取不到引导类加载器
        ClassLoader boostarpa = parent.getParent();
        System.out.println(boostarpa);
//        对于用户自定义类来说  默认使用系统类加载器来进行加载
        ClassLoader classLoader = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader);
//     获取不到引导类加载器   java的核心类库都是使用引导类加载器  加载的
        ClassLoader classLoader1 = String.class.getClassLoader();
        System.out.println(classLoader1);
    }
}
