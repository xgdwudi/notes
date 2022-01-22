package com.wudidemiao.study.algorithm.dac;

/**
 * @author wudidemiaoa
 * @date 2022/1/22
 * @apiNote
 */
public class Hanitower {
    public static void main(String[] args) {
        hanoitTower(5,'A','B','c');
    }

    //    汉罗塔移动方案
//    使用分治算法
    public static void hanoitTower(int num, char a, char b, char c) {
//         如果只有一个盘
        if (num == 1) {
            System.out.println("第一个盘从" + a + "->" + c);
        } else {
//            1. 如果我们n>=2 情况，我们总是可以看作是两个盘，1个是最下面的盘，和上面所有盘
//            2. 先把最上面的盘a->b
            hanoitTower(num - 1, a, c, b);
//           把最下面的盘从a-c
            System.out.println("第" + num + "盘" + a + "->" + c);
//            把B塔的所有盘移动到b->c,移动过程使用到A塔
            hanoitTower(num - 1, b, a, c);
        }
    }
}
