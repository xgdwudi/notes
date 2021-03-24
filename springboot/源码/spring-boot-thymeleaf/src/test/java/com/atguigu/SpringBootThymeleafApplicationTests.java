package com.atguigu;

import org.junit.Test;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootThymeleafApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
//        Invoice invoice = new Invoice();
//        Invoice invoices = new SalesInvoice();
//        System.out.println(Invoice.formatId("1234"));
//        System.out.println(invoices.formatId("1234"));
//        int i = Integer.compareUnsigned(2, 2);
//        System.out.println(i);
        System.out.println(Math.floor(11.52));

    }



}

class Invoice {
    public static String formatId(String oldId) {
        return oldId + "_Invoice";
    }
}

class SalesInvoice extends Invoice {
    public static String formatId(String oldId) {
        return oldId + "_SalesInvoice";
    }
}
