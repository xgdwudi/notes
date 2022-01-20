package com.wudidemiao.study.huffmantree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wudidemiaoa
 * @date 2022/1/19
 * @apiNote
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node huffmanTree = createHuffmanTree(arr);
    }

    //     创建赫夫曼树的方法
    public static Node createHuffmanTree(int arr[]) {
//      1.  遍历arr数组
//      2.  将arr的每个元素构建成每一个node
//        3.将Node 放入ArrayList中
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            nodes.add(new Node(arr[i]));
        }
        while (nodes.size() != 1) {
            Collections.sort(nodes);
            System.out.println("nodes=" + nodes);
//         去除权值最小的二叉树节点
            Node left = nodes.remove(0);
            Node right = nodes.remove(0);
            Node parents = new Node(left.value + right.value);
            parents.left = left;
            parents.right = right;
            nodes.add(parents);
        }
        return nodes.remove(0);
    }
}


// 创建节点类
// 为了让Node对象持续排序Collections 集合排序
class Node implements Comparable<Node> {
    //    权值
    int value;
    Node left; // 左子节点
    Node right;

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }

    @Override
    public int compareTo(Node o) {
//        从小到大排序
        return this.value - o.value;
    }
}
