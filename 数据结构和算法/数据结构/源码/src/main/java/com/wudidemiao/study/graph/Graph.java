package com.wudidemiao.study.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wudidemiaoa
 * @date 2022/1/21
 * @apiNote
 */
public class Graph {
    /**
     * 存储顶点集合
     */
    private List<String> vertexList;

    /**
     * 存储图对应的临界举证
     */
    private int[][] edges;

    private boolean[] isVisited;

    /**
     * 表示边的数目
     */
    private int numOfEdges;

    public static void main(String[] args) {
        String[] vertexValue = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(5);
        for (String s : vertexValue) {
            graph.insertVertex(s);
        }
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);
        graph.show();
//        graph.dfs();
        graph.bfs();
    }

    public Graph(int n) {
//        初始化矩阵和vertexList
        edges = new int[n][n];
        isVisited = new boolean[n];
        this.vertexList = new ArrayList<>(n);
        this.numOfEdges = 0;
    }

    // 获取第一个邻接节点的下标

    /**
     * @param index
     * @return 如果存在就返回对应的下标
     */
    public int getFirstNeighbor(int index) {
        for (int i = 0; i < vertexList.size(); i++) {
            if (edges[index][i] > 0) {
                return i;
            }
        }
        return -1;
    }

    //    根据前一个邻接节点的下标来获取下一个邻接节点
    public int getNextNeighbor(int v1, int v2) {
        for (int i = v2 + 1; i < vertexList.size(); i++) {
            if (edges[v1][i] > 0) {
                return i;
            }
        }
        return -1;
    }


//    对一个节点进行广度优先遍历的方法
    public void bfs(boolean[] isVisited,int i){
        int u;  // 表示队列头节点对应的下标
        int w;  // 表示队邻接节点的下标
//        队列，节点访问顺序
        LinkedList<Integer> linkedList = new LinkedList();
        // 访问节点 输出节点信息
        System.out.println(getValueByIndex(i)+"=>");
        isVisited[i] = true;
//        将这个节点加入队列
        linkedList.addLast(i);
        while (!linkedList.isEmpty()){
//            取出队列的头下表
            u = linkedList.removeFirst();
            w = getFirstNeighbor(u);
            while (w!=-1){
//                是否访问过
                if(!isVisited[w]){
                    // 访问节点 输出节点信息
                    System.out.println(getValueByIndex(w)+"=>");
//                    标记已经访问
                    isVisited[w]=true;
//                    入对
                    linkedList.addLast(w);
                }

//                以u为前驱节点找w后面的第一个节点
                w = getNextNeighbor(u,w);  // 体现出广度优先
            }

        }
    }

//    遍历所有的节点搜进行广度优先搜索
    public void bfs(){
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                bfs(isVisited,i);
            }
        }
    }

    //    深度优先遍历suanfa
    public void dfs(boolean[] isVisited, int i) {
//        首先访问该节点
        System.out.print(getValueByIndex(i) + "->");
        isVisited[i] = true;
        int firstNeighbor = getFirstNeighbor(i);
        while (firstNeighbor != -1) {
            if (!isVisited[firstNeighbor]) {
                dfs(isVisited, firstNeighbor);
            }
//            firstNeighbor 被访问过
            firstNeighbor = getNextNeighbor(i, firstNeighbor);
        }
    }

//    对dfs进行重载
    public void dfs(){
//        遍历所有的节点，dfs
        for (int i = 0; i < getNumOfVertex(); i++) {
            if(!isVisited[i]){
                dfs(isVisited,i);
            }
        }
    }

    //    途中常用的方法
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //    途中常用的方法
    public int getNumOfEdges() {
        return numOfEdges;
    }

    //    返回节点i对应数据的下标
    public String getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //    返回v1和v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //    显示图对应的举证
    public void show() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }

    }


    /**
     * 插入顶点
     *
     * @param vertex
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    //    添加边

    /**
     * @param v1     表示第几个顶点对应的下标
     * @param v2     第二个顶点对应的下标
     * @param weight
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }
}
