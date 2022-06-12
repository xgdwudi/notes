package com.wudidemiao.springboot.graph;

/**
 * 有向图
 *
 * @author wudidemiaoa
 * @date 2022/3/18
 * @apiNote
 */
public class DiGraph extends Graph {
    DiGraph(int V) {
        super(V);
    }

    /**
     * 在图中添加一条边v-w
     *
     * @param v
     * @param w
     */
    @Override
    void addEdge(int v, int w) {
        adj[v].add(w);
        E++;
    }

    /**
     * 遍历每一个结点，然后进行翻转
     *
     * @return 返回翻转后的图
     */
    public DiGraph reverse() {
        DiGraph diGraph = new DiGraph(V);
        for (int i = 0; i < V; i++) {
            for (int w : adj(i)) {
                diGraph.addEdge(w, i);
            }
        }
        return diGraph;
    }


    @Override
    Iterable<Integer> adj(int v) {
        return adj[v];
    }

    @Override
    Iterable<Integer> search(int s) {
        return null;
    }

    @Override
    boolean hasPathTo(int s, int v) {
        DirectGraphDFS directGraphDFS = new DirectGraphDFS(this, s);
        return directGraphDFS.pathTo(v);
    }

    @Override
    Iterable<Integer> pathTo(int s, int v) {
        return null;
    }
}
