package com.wudidemiao.springboot.graph01;

import java.util.List;

/**
 * @author wudidemiaoa
 * @date 2022/3/18
 * @apiNote
 */
public class Test01 {
    public static void main(String[] args) {
        IDirectGraph<String> directGraph = new ListDirectGraph<>();
//1. 初始化顶点
        directGraph.addVertex("1");
        directGraph.addVertex("2");
        directGraph.addVertex("3");
        directGraph.addVertex("4");
        directGraph.addVertex("5");
        directGraph.addVertex("6");
        directGraph.addVertex("7");
        directGraph.addVertex("8");

//2. 初始化边
        directGraph.addEdge(new Edge<>("1", "2"));
        directGraph.addEdge(new Edge<>("1", "3"));
        directGraph.addEdge(new Edge<>("2", "4"));
        directGraph.addEdge(new Edge<>("2", "5"));
        directGraph.addEdge(new Edge<>("3", "6"));
        directGraph.addEdge(new Edge<>("3", "7"));
        directGraph.addEdge(new Edge<>("4", "8"));
        directGraph.addEdge(new Edge<>("8", "5"));
        directGraph.addEdge(new Edge<>("6", "7"));
//4. DFS 遍历
        List<String> dfs = directGraph.dfs("1");
        System.out.println(dfs);
    }
}
