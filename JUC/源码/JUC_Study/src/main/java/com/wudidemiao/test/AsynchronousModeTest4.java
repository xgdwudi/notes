package com.wudidemiao.test;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Objects;

import static java.lang.Thread.sleep;

@Slf4j(topic = "c.AsynchronousModeTest4")
public class AsynchronousModeTest4 {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(2);
        for (int i = 0; i < 100; i++) {
            int id = i;
            new Thread(() -> {
                messageQueue.put(new Message(id, "结果" + id));
            }, "生产者" + i).start();
        }

        new Thread(() -> {
         while (true){
             try {
                 sleep(1);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
             Message message = messageQueue.getMessage();
             log.debug("收到了"+message.toString());
         }
        }, "消费者").start();
    }
}

//消息队列类
@Slf4j(topic = "c.MessageQueue")
class MessageQueue {
    private LinkedList<Message> list = new LinkedList<>();

    //    队列容量
    private int capcity;

    public MessageQueue(int capcity) {
        this.capcity = capcity;
    }

    //    获取消息
    public Message getMessage() {
        synchronized (list) {
//        检查队列是否为空
            while (list.isEmpty()) {
                try {
                    log.debug("等待中...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = list.removeFirst();
            list.notifyAll();
            return message;
        }

    }

    public void put(Message message) {
        synchronized (list) {
            if (list.size() == capcity) {
                try {
                    log.debug("容量满，等待泄洪中...");
                    list.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug("存储中。。。");
            list.addLast(message);
            list.notifyAll();
        }
    }
}

final class Message {
    private int id;
    private Object value;

    public Message(int id, Object value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public Object getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", value=" + value +
                '}';
    }
}
