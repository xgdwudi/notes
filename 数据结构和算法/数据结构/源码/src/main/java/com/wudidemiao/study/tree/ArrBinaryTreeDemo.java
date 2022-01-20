package com.wudidemiao.study.tree;

/**
 * @author wudidemiaoa
 * @date 2022/1/17
 * @apiNote
 */
public class ArrBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        ArrBinaryTree arrBinaryTree = new ArrBinaryTree(arr);
        arrBinaryTree.preOrder();
    }
}

class ArrBinaryTree {
    private int[] arr;

    public ArrBinaryTree(int[] arr) {
        this.arr = arr;
    }

    public void preOrder(){
        preOrder(0);
    }

    //    编写方法，完成顺序存储二叉树的前序遍历
    public void preOrder(int order) {
        if (arr == null && arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历。");
        }
        System.out.println(arr[order]);
//        向左前序遍历
        if (order * 2 + 1 < arr.length) {
            preOrder(order * 2 + 1);
        }

//        向右递归遍历
        if (order * 2 + 2 < arr.length) {
            preOrder(order * 2 + 2);
        }
    }
}
