package com.atguigu;

import redis.clients.jedis.Jedis;

/**
 * @version 1.0
 * 注意：本内容仅限于西安城市发展资源信息有限公司内部传阅，禁止外泄以及用于其他的商业目
 * @ClassName Demo01
 * @Description TODO
 * @Aurhor xu
 * @Ddte 2021/3/17 19:31
 **/
public class Demo01 {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        System.out.println(jedis.ping());
        jedis.watch("");
    }
}
