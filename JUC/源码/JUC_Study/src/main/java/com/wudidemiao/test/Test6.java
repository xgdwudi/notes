package com.wudidemiao.test;

import lombok.Data;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author wudidemiaoa
 * @date 2022/1/13
 * @apiNote
 */
public class Test6 {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        Unsafe unsafe = (Unsafe) theUnsafe.get(null);
        System.out.println(unsafe);

//      1. 获取属性的偏移地址
        long idOffect = unsafe.objectFieldOffset(Student.class.getDeclaredField("id"));
        long nameOffect = unsafe.objectFieldOffset(Student.class.getDeclaredField("name"));
        Student student = new Student();
//       2.执行cas操作
        unsafe.compareAndSwapInt(student, idOffect, 0, 1);
        unsafe.compareAndSwapObject(student, nameOffect, null, "张三");
        System.out.println(student);
    }
}

@Data
class Student {
    volatile int id;
    volatile String name;
}
