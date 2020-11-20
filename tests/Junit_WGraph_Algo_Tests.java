package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import ex1.src.WGraph_DS;
import ex1.src.WGraph_Algo;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.Serializable;
import java.util.*;

public class Junit_WGraph_Algo_Tests {
    private static Random _rnd = null;

    @Test
    public void test5_DeepCopyGraph() {
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(3);
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(3, 5, 45);
        WGraph_Algo cops = new WGraph_Algo();
        cops.init(nodesGraph);
        WGraph_DS newCopy = (WGraph_DS) cops.copy();
        assertEquals(nodesGraph.nodeSize(), newCopy.nodeSize());
        assertEquals(nodesGraph.edgeSize(), newCopy.edgeSize());
        checkDeepCopyElements(nodesGraph,newCopy);
        nodesGraph.getNode(2).setTag(1000);
        assertNotEquals(nodesGraph.getNode(2).getTag(),newCopy.getNode(2).getTag());
        nodesGraph.removeNode(7);
       // checkDeepCopyElements(nodesGraph,newCopy);
        nodesGraph.getNode(5).setTag(3);
        assertNotEquals(nodesGraph.nodeSize(), newCopy.nodeSize());

    }

    void checkDeepCopyElements(WGraph_DS nodesGraph,WGraph_DS newCopy) {
        Iterator<node_info> nodesGraph_it = nodesGraph.getV().iterator();
        Iterator<node_info> newCopy_it = newCopy.getV().iterator();
        while(newCopy_it.hasNext()){
            node_info node1 = nodesGraph_it.next();
            node_info node2 = newCopy_it.next();
            assertEquals(node1.getKey(),node2.getKey());
            assertEquals(node1.getTag(),node2.getTag());
            assertEquals(node1.getInfo(),node2.getInfo());
        }
    }

    @Test
    void test6_isConnected() {
        WGraph_DS nodesGraph = new WGraph_DS();
        WGraph_Algo emptyGraph = new WGraph_Algo();
        emptyGraph.init(nodesGraph);
        assertTrue(emptyGraph.isConnected());
        nodesGraph.addNode(1);
        assertTrue(emptyGraph.isConnected());
        nodesGraph.removeNode(1);
        nodesGraph.removeNode(3);
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        WGraph_Algo cops = new WGraph_Algo();
        cops.init(nodesGraph);
        boolean flag = cops.isConnected();
        assertFalse(cops.isConnected());
        nodesGraph.connect(2, 5, 12);
        nodesGraph.connect(7, 5, 12);
        assertTrue(cops.isConnected());
        nodesGraph.addNode(10);
        assertFalse(cops.isConnected());

    }

    private static int[] nodes(WGraph_DS g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for (int i = 0; i < size; i++) {
            ans[i] = nodes[i].getKey();
        }
        Arrays.sort(ans);
        return ans;
    }

    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0 + min, (double) max);
        int ans = (int) v;
        return ans;
    }

    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max - min;
        double ans = d * dx + min;
        return ans;
    }

    @Test
    void test7_Dijkstra() {
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(3);
        WGraph_Algo cops = new WGraph_Algo();
        cops.init(nodesGraph);
        nodesGraph.connect(2, 5, 12);
        nodesGraph.connect(7, 5, 9);
        assertEquals(21, cops.shortestPathDist(2, 7));
        assertEquals(12, cops.shortestPathDist(2, 5));
        nodesGraph.connect(2, 3, 1);
        nodesGraph.connect(3, 7, 4);
        assertEquals(5, cops.shortestPathDist(2, 7));
        double x = cops.shortestPathDist(2, 7);
        System.out.println(x);
        List<node_info> list = cops.shortestPath(2, 7);
        for (int i = 0; i < list.size(); i++) {
            System.out.printf(list.get(i).getKey() + " --> ");
        }
        nodesGraph.addNode(10);
        nodesGraph.addNode(8);
        nodesGraph.connect(7, 10, 2);
        nodesGraph.connect(3, 8, 50);
        nodesGraph.connect(10, 8, 3);
        double y = cops.shortestPathDist(2, 8);
        assertEquals(10, cops.shortestPathDist(2, 8));

        System.out.println("\n" + y);
        List<node_info> list2 = cops.shortestPath(2, 8);
        for (int i = 0; i < list2.size(); i++) {
            System.out.printf(list2.get(i).getKey() + " --> ");
        }
    }
    @Test
    void test8_save_Load(){
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(3);
        nodesGraph.connect(2, 5, 12);
        nodesGraph.connect(7, 5, 9);
        WGraph_Algo graph = new WGraph_Algo();
        graph.init(nodesGraph);
        graph.save("Ex1.txt");
        WGraph_Algo graph_other = new WGraph_Algo();
        graph_other.load("Ex1.txt");
        printGraph(graph_other.getGraph());
    }

    public static void printGraph(weighted_graph graph) {
        int src_vertex = 0;
        int i = 0;
        int list_size = graph.nodeSize();
        ArrayList<node_info> list = new ArrayList<>();
        Iterator<node_info> it = graph.getV().iterator();
        while (it.hasNext()) {
            node_info s = it.next();
            list.add(s);
        }
        System.out.println("The contents of the graph:");
        while (i < list_size) {
            src_vertex = list.get(i).getKey();
            WGraph_DS.NodeData srcVertex2 = (WGraph_DS.NodeData) list.get(i);
            if (srcVertex2.getNi().size() == 0) {
                System.out.println("Vertex:" + src_vertex + " ==> " + "\t");
            } else {
                //traverse through the adjacency list and print the edges
                for (node_info edge : srcVertex2.getNi()) {
                    if (srcVertex2.getNi().size() != 0) {
                        System.out.print("Vertex:" + src_vertex + " ==> " + edge.getKey() + "\t");
                    }
                }
            }
            System.out.println();
            i++;
        }
    }
}
