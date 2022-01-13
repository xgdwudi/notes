package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

/**
 * @author wudidemiaoa
 * @date 2022/1/13
 * @apiNote
 */
@Slf4j(topic = "c.Test7")
public class Test7 {
    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",dateTimeFormatter.parse("1951-04-21"));
                }catch (Exception e){
                    log.error("{}",e);
                }
            }).start();
        }
    }

    static void test(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    log.debug("{}",simpleDateFormat.parse("1951-04-21"));
                }catch (Exception e){
                    log.error("{}",e);
                }
            }).start();
        }
    }
}
