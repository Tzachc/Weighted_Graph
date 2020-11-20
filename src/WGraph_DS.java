package ex1.src;

import java.io.Serializable;
import java.util.*;

public class WGraph_DS implements weighted_graph, Serializable {
    private HashMap<Integer, node_info> nodes;
    private HashMap<Integer, HashMap<Integer, Double>> WeightsHash;
    private int edgesCounter;
    private int MC = 0;

    /**
     * NodeData class that represent a node in the graph
     * implement the interface "node_info"
     */
    public  class NodeData implements node_info, Comparable<NodeData>, Serializable {
        //public static int uniqueKey = 0;
        private int _key;
        private String _info;
        private double _tag;
        private node_info pre;
        private HashMap<Integer, node_info> NeighborsList;
        private HashMap<Integer, Double> NeighborsWeight;

        public NodeData() { // constructor
            this._info = "";
            this._tag = 0;
            this._key = 0;
            this.pre = null;
            NeighborsList = new HashMap<Integer, node_info>();
            NeighborsWeight = new HashMap<Integer, Double>();
        }

        public NodeData(int key) {
            this._info = "";
            this._tag = 0;
            this._key = key;
            this.pre = null;
            NeighborsList = new HashMap<Integer, node_info>();
            NeighborsWeight = new HashMap<Integer, Double>();
        }

        public NodeData(node_info node) {
            this._key = node.getKey();
            this._info = node.getInfo();
            this._tag = node.getTag();
            NeighborsList = new HashMap<Integer, node_info>();
            NeighborsWeight = new HashMap<Integer, Double>();
        }

        @Override
        public int getKey() {
            return this._key;
        }

        @Override
        public String getInfo() {
            return this._info;
        }

        @Override
        public void setInfo(String s) {
            this._info = s;
        }

        @Override
        public double getTag() {
            return this._tag;
        }

        @Override
        public void setTag(double t) {
            this._tag = t;
        }

        public Collection<node_info> getNi() {
            return this.NeighborsList.values();
        }

        public boolean hasNi(int key) {
            return NeighborsList.containsKey(key);
        }

        /**
         * add connection and weights
         * @param t
         * @param w
         */
        public void addNi(node_info t, double w) {
            this.NeighborsList.put(t.getKey(), t);
            this.NeighborsWeight.put(t.getKey(), w);

        }

        public Collection<Double> getNiWeight() {
            return this.NeighborsWeight.values();
        }

        @Override
        public int compareTo(NodeData o) {
            return Double.compare(this.getTag(), o.getTag());
        }

        public node_info getPre() {
            return this.pre;
        }


        /**
         * used to set the previous node of the current one
         * will be used to find the shortest path.
         *
         * @param n node.
         */
        public void setPre(node_info n) {
            this.pre = n;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeData nodeData = (NodeData) o;

            return _key == nodeData._key;
        }
    } // NodeData class end

    public WGraph_DS() {
        nodes = new HashMap<Integer, node_info>();
        WeightsHash = new HashMap<Integer, HashMap<Integer, Double>>();
        edgesCounter = 0;
        MC = 0;
    }

    /**
     * constructor used for the deep copy method
     * @param other
     */
    public WGraph_DS(weighted_graph other) {
        this();
        Iterator<node_info> it = other.getV().iterator();
        while (it.hasNext()) {
            node_info nodes = it.next();
            addNode(nodes.getKey());
        }
        for (node_info node1 : other.getV()) {
            for (node_info node2 : other.getV(node1.getKey())) {
                double weight = getEdge(node2.getKey(), node1.getKey());
                this.connect(node2.getKey(), node1.getKey(), weight);
            }
        }
        this.MC = other.getMC();
    }

    @Override
    public node_info getNode(int key) {
        return nodes.get(key);
    }

    /**
     * check if there's edge betweeen two nodes in the graph
     * @param node1
     * @param node2
     * @return
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        if(node1 == node2 || getNode(node1) == null || getNode(node2) == null){
            return false;
        }
        if(WeightsHash.containsKey(node1) && WeightsHash.get(node1).containsKey(getNode(node2).getKey())){
            return WeightsHash.containsKey(node2) && WeightsHash.get(node2).containsKey(getNode(node1).getKey());
        }
        return false;
    }

    /**
     * if there edge between node1 and node1
     * then return the weight between them.
     * @param node1
     * @param node2
     * @return weight.
     */
    @Override
    public double getEdge(int node1, int node2) {
        if(getNode(node1) != null && getNode(node2) != null){
            if(hasEdge(node1,node2)){
                return WeightsHash.get(node1).get(getNode(node2).getKey());
            }
        }
        return -1;
    }

    /**
     * add node to the graph.
     * @param key
     */
    @Override
    public void addNode(int key) {
        if (nodes.containsKey(key)) {
            return;
        } else {
            nodes.put(key, new NodeData(key));
            WeightsHash.put(key,new HashMap<Integer, Double>());
            this.MC++;
        }
    }

    /**
     * connect 2 nodes with given weight
     * if theres already edge, then update the weight.
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        NodeData nodeOne = (NodeData) nodes.get(node1);
        if (nodes.containsKey(node1) && nodes.containsKey(node2) && nodeOne.hasNi(node2) && getEdge(node1, node2) != w) { //update the weight
            NodeData nodeTwo = (NodeData) nodes.get(node2);
            nodeOne.addNi(nodeTwo, w);
            nodeOne.addNi(nodeOne, w);
            WeightsHash.get(node1).put(nodeTwo.getKey(),w);
            WeightsHash.get(node2).put(nodeOne.getKey(),w);
            this.MC++;
        }
        if (node1 != node2 && nodes.containsKey(node1) && nodes.containsKey(node2) && !nodeOne.hasNi(node2)) {
            NodeData nodeTwo = (NodeData) nodes.get(node2);
            nodeOne.addNi(nodeTwo, w);
            nodeTwo.addNi(nodeOne, w);
            WeightsHash.get(node1).put(node2,w);
            WeightsHash.get(node2).put(node1,w);
            edgesCounter++;
            MC++;
        }
    }

    @Override
    public Collection<node_info> getV() {
        return this.nodes.values();
    }

    /**
     * This method returns a Collection containing all the
     *  nodes connected to node_id
     * @param node_id
     * @return
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        if(!nodes.containsKey(node_id))
            return null;
        Collection<node_info> coll = new ArrayList<>();
        for(Integer nodeKey : WeightsHash.get(node_id).keySet()) {
            coll.add(nodes.get(nodeKey));
        }
        return coll;
    }

    /**
     * remove node and all his edges connection from the graph.
     * @param key
     * @return
     */
    @Override
    public node_info removeNode(int key) {
        boolean flag = nodes.containsKey(key);
        if (flag) {
            NodeData nodeToRemove = (NodeData) nodes.get(key);
            edgesCounter -= nodeToRemove.getNi().size();
            MC += nodeToRemove.getNi().size() + 1;
            Iterator<node_info> it = nodeToRemove.getNi().iterator();
            while(it.hasNext()) {
                node_info node = it.next();
                //NodeData nodeOne = (NodeData) node;
               // removeEdge(key,node.getKey());
                WeightsHash.get(node.getKey()).remove(key);
            }
            nodeToRemove.getNi().clear();
            // nodeToRemove.getNiWeight().clear();
            nodeToRemove.NeighborsWeight.clear();
            WeightsHash.remove(key);
        }
        return nodes.remove(key);
    }

    /**
     * remove edge between two nodes.
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        NodeData nodeOne = (NodeData) nodes.get(node1);
        NodeData nodeTwo = (NodeData) nodes.get(node2);
        if (nodeOne.hasNi(node2)) {
            nodeOne.getNi().remove(nodeTwo);
            nodeOne.NeighborsWeight.remove(node2);
           WeightsHash.get(nodeOne.getKey()).remove(node2);
            nodeTwo.getNi().remove(nodeOne);
            nodeTwo.NeighborsWeight.remove(node1);
            WeightsHash.get(nodeTwo.getKey()).remove(node1);
           // WeightsHash.get(node2).remove(WeightsHash.getOrDefault(node1,null));
            MC++;
            edgesCounter--;
        }
    }

    @Override
    public int nodeSize() {
        return this.nodes.size();
    }

    @Override
    public int edgeSize() {
        return this.edgesCounter;
    }

    @Override
    public int getMC() {
        return this.MC;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WGraph_DS that = (WGraph_DS) o;
        if (MC != that.MC) return false;
        if (edgesCounter != that.edgesCounter) return false;
        if (!nodes.equals(that.nodes)) return false;
        return WeightsHash.equals(that.WeightsHash);
    }
}
