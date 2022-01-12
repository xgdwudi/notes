package com.wudidemiao.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j(topic = "c.SynchronousMode")
public class SynchronousMode {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new People().start();
        }

        Thread.sleep(2000);

        for (Integer id : Mailboxes.getIds()) {
            new Postman(id,"内容"+id).start();
        }
        
    }
}

class Mailboxes {
    private static Map<Integer, GuardeObject> boxes = new Hashtable<>();

    private static int id = 1;

    public static synchronized int generateId() {
        return id++;
    }

    public static GuardeObject createGuardeObject() {
        GuardeObject guardeObject = new GuardeObject(generateId());
        boxes.put(guardeObject.getId(), guardeObject);
        return guardeObject;
    }

    public static Set<Integer> getIds() {
        return boxes.keySet();
    }

    public static GuardeObject getGuardeObject(int id) {
        return boxes.remove(id);
    }
}

@Slf4j(topic = "c.People")
class People extends Thread {
    @Override
    public void run() {
        //        收信
        GuardeObject guardeObject = Mailboxes.createGuardeObject();
//        id
        log.debug("收信id::" + guardeObject.getId());
        Object response = guardeObject.getResponse(5000);
        log.debug("收信id:{},内容 {}", guardeObject.getId(), response);
    }
}

@Slf4j(topic = "c.Postman")
class Postman extends Thread {
    private int id;
    private String mail;

    public Postman(int id, String mail) {
        this.id = id;
        this.mail = mail;
    }

    @SneakyThrows
    @Override
    public void run() {
        GuardeObject guardeObject = Mailboxes.getGuardeObject(id);
        guardeObject.complete(mail);
        log.debug("发送完毕。。id:{},内容{}", id, mail);
    }
}

class GuardeObject {
    private int id;

    public GuardeObject(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //     结果
    private Object response;

    //    获取结果
    public Object getResponse(long timeOut) {
        synchronized (this) {
//            开始点 
            long begin = System.currentTimeMillis();
//            经历的时间
            long passedTime = 0;
            while (Objects.isNull(response)) {
                long waitTime = timeOut - passedTime;
//                时间超过了等待时间
                if (waitTime <= 0) {
                    return new ArrayList<String>();
                }
                try {
                    this.wait(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                求得经历时间
                passedTime = System.currentTimeMillis() - begin;
            }

            return response;
        }
    }

    public void complete(Object response) {
        synchronized (this) {
//            给成员变量赋值
            this.response = response;
            this.notifyAll();
        }
    }


    public static List<String> download() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

}
