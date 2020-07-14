package com.houde.guava.graph;

import com.google.common.graph.Graph;
import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

/**
 * @author qiukun
 * @create 2019-10-11 17:32
 */
public class GraphTest {

    public static void main(String[] args) {
        // Creating mutable graphs
        MutableValueGraph<String, Integer> roads  = ValueGraphBuilder.undirected().build();
        roads.putEdgeValue("武汉","上海",800);
        roads.putEdgeValue("武汉","长沙",300);
        roads.putEdgeValue("上海","广州",900);
        roads.putEdgeValue("武汉","广州",900);
        Graph<String> stringGraph = roads.asGraph();
        System.out.println(stringGraph);
        System.out.println( stringGraph.degree("广州"));
        System.out.println( stringGraph.degree("广州"));
    }
}
