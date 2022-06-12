package com.wudidemiao.springboot.graph01;

import java.util.*;

/**
 * 链表实现的有向图
 *
 * 邻接链表（Adjacency List）实现的有向图
 *
 * @author binbin.hou
 * @since 0.0.2
 */
public class ListDirectGraph<V> implements IDirectGraph<V> {

    /**
     * 节点链表
     * @since 0.0.2
     */
    private List<GraphNode<V>> nodeList;

    /**
     * 初始化有向图
     * @since 0.0.2
     */
    public ListDirectGraph() {
        this.nodeList = new ArrayList<GraphNode<V>>();
    }

    @Override
    public void addVertex(V v) {
        GraphNode<V> node = new GraphNode<>(v);

        // 直接加入到集合中
        this.nodeList.add(node);
    }

    @Override
    public boolean removeVertex(V v) {
        //1. 移除一个顶点
        //2. 所有和这个顶点关联的边也要被移除
        Iterator<GraphNode<V>> iterator = nodeList.iterator();
        while (iterator.hasNext()) {
            GraphNode<V> graphNode = iterator.next();

            if(v.equals(graphNode.getVertex())) {
                iterator.remove();
            }
        }

        return true;
    }

    @Override
    public V getVertex(int index) {
        return nodeList.get(index).getVertex();
    }

    @Override
    public void addEdge(Edge<V> edge) {
        //1. 新增一条边，直接遍历列表。
        // 如果存在这条的起始节点，则将这条边加入。
        // 如果不存在，则直接报错即可。

        for(GraphNode<V> graphNode : nodeList) {
            V from = edge.getFrom();
            V vertex = graphNode.getVertex();

            // 起始节点在开头
            if(from.equals(vertex)) {
                graphNode.getEdgeSet().add(edge);
            }
        }
    }

    @Override
    public boolean removeEdge(Edge<V> edge) {
        // 直接从列表中对应的节点，移除即可
        GraphNode<V> node = getGraphNode(edge);
        if(null != node) {
            // 移除目标为 to 的边
            node.remove(edge.getTo());
        }

        return true;
    }

    @Override
    public Edge<V> getEdge(int from, int to) {
        // 获取开始和结束的顶点
        V toVertex = getVertex(from);

        // 获取节点
        GraphNode<V> fromNode = nodeList.get(from);
        // 获取对应结束顶点的边
        return fromNode.get(toVertex);
    }

    /**
     * 获取图节点
     * @param edge 边
     * @return 图节点
     */
    private GraphNode<V> getGraphNode(final Edge<V> edge) {
        for(GraphNode<V> node : nodeList) {
            final V from = edge.getFrom();

            if(node.getVertex().equals(from)) {
                return node;
            }
        }

        return null;
    }

    /**
     * 获取对应的图节点
     * @param vertex 顶点
     * @return  图节点
     * @since 0.0.2
     */
    private GraphNode<V> getGraphNode(final V vertex) {
        for(GraphNode<V> node : nodeList) {
            if(vertex.equals(node.getVertex())) {
                return node;
            }
        }
        return null;
    }

    @Override
    public List<V> dfs(V root) {
        List<V> visitedList = new ArrayList<>();
        Stack<V> visitingStack = new Stack<>();
        // 顶点首先压入堆栈
        visitingStack.push(root);
        // 获取一个边的节点
        while (!visitingStack.isEmpty()) {
            V visitingVertex = visitingStack.peek();
            GraphNode<V> graphNode = getGraphNode(visitingVertex);
            boolean hasPush = false;
            if(null != graphNode) {
                Set<Edge<V>> edgeSet = graphNode.getEdgeSet();
                for(Edge<V> edge : edgeSet) {
                    V to = edge.getTo();
                    if(!visitedList.contains(to)
                            && !visitingStack.contains(to)) {
                        // 寻找到下一个临接点
                        visitingStack.push(to);
                        hasPush = true;
                        break;
                    }
                }
            }
            // 循环之后已经结束，没有找到下一个临点，则说明访问结束。
            if(!hasPush) {
                // 获取第一个元素
                visitedList.add(visitingStack.pop());
            }
        }
        return visitedList;
    }

}