package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;
import java.io.Serializable;

import ex1.src.WGraph_DS;
import ex1.src.WGraph_Algo;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import ex1.src.weighted_graph_algorithms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Juint_WGraph_DS {

    private WGraph_Algo graph;


    @Test
    public void test1_createNodes() {
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        if (nodesGraph.hasEdge(2, 5)) {
            fail("Should be false");
        }
        assertEquals(nodesGraph.nodeSize(), 3);
        if (nodesGraph.nodeSize() != 3) {
            fail("need to be 3");
        }
    }

    @Test
    public void test2_Connection() {
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.connect(2, 5, 20);
        nodesGraph.connect(2, 7, 40);
        assertEquals(0, nodesGraph.edgeSize());
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        assertFalse(nodesGraph.hasEdge(2,5));
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(2,7,40);
        assertEquals(2,nodesGraph.edgeSize());
        assertTrue(nodesGraph.hasEdge(2,5));

    }

    @Test
    public void test3_RemoveNodes(){
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(3);
        nodesGraph.addNode(6);
        nodesGraph.addNode(10);
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(2,7,40);
        assertEquals(2,nodesGraph.edgeSize());
        nodesGraph.removeNode(2);
        assertFalse(nodesGraph.hasEdge(2,5));
        assertEquals(5,nodesGraph.nodeSize());
        nodesGraph.removeNode(1);
        assertEquals(5,nodesGraph.nodeSize());
        assertEquals(0,nodesGraph.edgeSize());
        nodesGraph.removeNode(3);
        assertEquals(4,nodesGraph.nodeSize());


    }
    @Test
    public void test4_RemoveEdges(){
        WGraph_DS nodesGraph = new WGraph_DS();
        nodesGraph.addNode(2);
        nodesGraph.addNode(5);
        nodesGraph.addNode(7);
        nodesGraph.addNode(3);
        nodesGraph.addNode(6);
        nodesGraph.addNode(10);
        nodesGraph.connect(2,5,20);
        nodesGraph.connect(3,5,45);
        nodesGraph.connect(3,10,33);
        nodesGraph.removeEdge(3,5);
        assertEquals(2,nodesGraph.edgeSize());

    }


}
