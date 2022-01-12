package com.wudidemiao.threads.sync;

/*

第一步创建资源类，在资源类创建属性和操作方法

第二步创建多个线程，调用资源类的操作方法

*/

class Ticket{
//   票数
    private int number = 30 ;
//    买票
    public synchronized void sale(){
//
        if(number>0){
            number--;
            System.out.println(Thread.currentThread().getName()+":: 剩下:"+number );
        }else {
            System.out.println("没票了");
        }
    }
}
public class SaleTicket {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"aa").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"bb").start();
        new Thread(()->{
            for (int i = 0; i < 40; i++) {
                ticket.sale();
            }
        },"cc").start();
    }
}
