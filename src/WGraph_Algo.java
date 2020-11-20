package ex1.src;

import java.io.*;
import java.util.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class WGraph_Algo implements weighted_graph_algorithms, Serializable {
    weighted_graph graph;
    private int V;

    public WGraph_Algo() { // constructor
        this.graph = new WGraph_DS();
        V = this.graph.nodeSize();
    }

    /**
     * preform a shallow copy with the graph g.
     *
     * @param g
     */
    @Override
    public void init(weighted_graph g) { //shallow copy init.
        this.graph = g;
        V = this.graph.nodeSize();
    }

    /**
     * this method return the graph.
     * @return graph
     */
    @Override
    public weighted_graph getGraph() {
        return this.graph;
    }

    /**
     * this method preform deep copy
     * @return
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS newGraph = new WGraph_DS(graph);
        return newGraph;
    }

    /**
     * this method check the connectivity of the graph.
     * using with BFS algorithm.
     * @return
     */
    @Override
    public boolean isConnected() {
        if (graph.nodeSize() == 0 || graph.nodeSize() == 1)
            return true;
        BFS(graph.getV().iterator().next().getKey());
        for (node_info node : this.graph.getV()) {
            if (this.graph.getNode(node.getKey()).getTag() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * finding the shortest path weight from src node to dest node.
     * the path is the sum of the shortest weight.
     * @param src - start node
     * @param dest - end (target) node
     * @return shortest weight
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        double distance = Dijkstra(this.graph.getNode(src), this.graph.getNode(dest));
        InitDijkstra();
        if (distance == Integer.MAX_VALUE) {
            return -1;
        }
        return distance;
    }

    /**
     * this method return the shortest path itself
     * based on the shortest weight from
     * src node --> .. --> dest node.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        List<node_info> ans = new ArrayList<node_info>();
        Dijkstra(graph.getNode(src), graph.getNode(dest));

        if (src == dest) {
            ans.add(graph.getNode(src));
            return ans;
        }

        int key = dest;
        double result = 0;
        node_info tem = graph.getNode(key);
        WGraph_DS.NodeData temp = (WGraph_DS.NodeData) tem;
        while (key != src && key != -1 && result != -1 && temp != null) {
            ans.add(temp);
            //result = graph.getNode(key).getTag();
            result = temp.getTag();
            temp = (WGraph_DS.NodeData) temp.getPre();
        }
        if (result == -1) {
            return null;
        }
        // ans.add(graph.getNode(src));
        Collections.reverse(ans);
        return ans;
    }

    /**
     * this method save the graph, and called it with the given name.
     * @param file - the file name (may include a relative path).
     * @return true/false
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(this);
            o.close();
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * this method read graph from file name
     * if the file successfully loaded then return true
     * otherwise false.
     * @param file - file name
     * @return
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream o = new ObjectInputStream(f);
            // o.writeObject(this.graph);
            WGraph_Algo GA = (WGraph_Algo) o.readObject();
            this.graph = GA.graph;
            // this.graph = (graph)o.readObject();
            o.close();
            f.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * BFS algorithm to find if there is a path between
     * given node to every node
     * created to be used in the graph connectivity method.
     * @param s
     */
    void BFS(int s) {
        // init(graph);
        //Initialize();
        InitBFS();
        LinkedList<Integer> queue = new LinkedList<>();
        node_info src = this.graph.getNode(s);
        src.setTag(1);
        // visited[s] = true;
        queue.add(s);
        while (!queue.isEmpty()) {
            s = queue.poll();
            node_info nodeS = this.graph.getNode(s);
            // System.out.printf(s + " ");
            // Iterator<node_data> it = this.graph.getNode(s).getNi().iterator();
            Iterator<node_info> it = this.graph.getV(s).iterator();
            while (it.hasNext()) {
                node_info n = it.next();
                if (n.getTag() == 0) {
                    //visited[n.getKey()] = true;
                    n.setTag(1);
                    queue.add(n.getKey());
                }
            }
            nodeS.setTag(3);
        }
    }

    /**
     * init the BFS, tags = 0
     *  0 = white, 1 = gray, 3 = black.
     */
    private void InitBFS() {
        for (node_info node : this.graph.getV()) {
            node.setTag(0);
        }
    }

    /**
     * this method implements Dijkstra algorithm in order
     * to find the shortest path on weighted graph.
     * the algorithm use Priority queue.
     * @param src
     * @param dest
     * @return
     */
    private double Dijkstra(node_info src, node_info dest) {
        InitDijkstra();
        PriorityQueue<node_info> pq = new PriorityQueue<>();
        src.setInfo("gray");
        src.setTag(0);
        pq.add(src);
        boolean flag;
        double shortDist = Integer.MAX_VALUE;
        if(src == dest){
            return src.getTag();
        }
        while (!pq.isEmpty()) {
            WGraph_DS.NodeData temp = (WGraph_DS.NodeData) pq.poll();
            Iterator<node_info> it = temp.getNi().iterator();
            while (it.hasNext()) {
                node_info n = it.next();
                if (n.getInfo() == "white" && n !=null) {
                    WGraph_DS.NodeData temp2 = (WGraph_DS.NodeData) n;
                    if (n.getTag() > temp.getTag() + this.graph.getEdge(n.getKey(), temp.getKey())) {
                        n.setTag(extractMin(n,temp));
                        temp2.setPre(temp);
                    }
                    pq.add(n);
                }
            }
            temp.setInfo("gray");
            flag = finish(temp,dest);
            if (flag) {
                return temp.getTag();
            }
        }
        return shortDist;
    }

    public boolean finish(node_info node1,node_info node2){
        if(node1 == node2){
            return true;
        }
        return false;
    }

    /**
     * this method is a helper method for the Dijkstra algorithm,
     * to find the min in the priority queue and extract it.
     * @param n
     * @param temp
     * @return
     */
    private double extractMin(node_info n,node_info temp){
        double tempTag = temp.getTag() + this.graph.getEdge(n.getKey(),temp.getKey());
        double min = Math.min(n.getTag(),tempTag);
        return min;
    }

    /**
     * before every Dijkstra run, we initialize the graph
     * with the given data:
     * tag = Integer.Max_Value
     * info = white (white , gray , black)
     * pre = null (used to save the path itself for the ShortestPath method).
     */
    private void InitDijkstra() {
        WGraph_DS.NodeData preNode;
        Iterator<node_info> it = this.graph.getV().iterator();
        while(it.hasNext()){
            node_info node = it.next();
            preNode = (WGraph_DS.NodeData)node;
            node.setTag(Integer.MAX_VALUE);
            node.setInfo("white");
            preNode.setPre(null);
        }
    }

}

