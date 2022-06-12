package com.wudidemiao.springboot.graph;

/**
 * @author wudidemiaoa
 * @date 2022/3/18
 * @apiNote
 */
public class Test01 {
    public static void main(String[] args) {
        Graph diGraph = new DiGraph(10);
        diGraph.addEdge(1,2);
        diGraph.addEdge(4,3);
        DirectedCycle directedCycle = new DirectedCycle(diGraph);
        System.out.println(directedCycle.hasCycle());
        System.out.println();
    }
}
