# Weighted_Graph
Simple implementation for undirected weighted graph in java.
Ex1 in Object oriented programming in Ariel University.

# Undirected Weighted graphs

This Object oriented programming task written by:
* Tzach Cohen - 205650310

The main topic of this task is undirected weighted graphs and algorithems.

I've got 3 classes in my project(one of them is private class inside of another class):
-WGraph_DS: implements the interface "weighted_graph" and represents an undirected weighted graph.
also inside of WGraph_DS there is another class, that represent a Node in the graph, implement the interface "node_info".

-WGraph_Algo: implements the interface "weighted_graph_algorithms", represents sevral of algorithms based on the graph we created in our WGraph_DS class.


My graph have sevral importent methods:
WGraph_DS:

**note** : i've using 2 HashMaps in this class:
first one called : "nodes" - and it represent the nodes in the graph.
second one called WeightsHash - and it represent the Weight on the edges.


1) **getV** - return a collection that contain all the vertecis in the graph.
2) **getV(int node_id)** - return collection of Neigbors with that node_id.
3) **removeNode** - remove node from the graph,and cancel all his edges.
4) **removeEdge** - remove edge with two nodes.
5) **addNode** - add node to the graph.
6) **getNode** - return the node based on given key.
7) **connect** - initialize an edge with 2 given nodes, and set their weight.
8) **hasEdge** - boolean method that check if there is edge between two nodes.
9) **getEdge** - if there edge, then return the shortest path based on the cheaper 
weight from src node to destination node.
10) **equals** - overriding method to override the basic equals function.

in the private class NodeData i've implement the basic method and constructors
for a node in the graph such as : get/set Key,get/set info, etc.


WGraph_Algo:
1) **BFS** - method to find if we can reach all the nodes from a source node.
helper function for the graph connectivity check.
2) **shortestPathDist** - returns the shortest distance weight between source to destination node, using the BFS algorithem.
3) **shortestPath** - returns the shortest path itself (an actuall List).
like Src -> .. -> Dest.
4) **Init/InitBFS/InitDijkstra** -  init the graph/BFS algorithm/Dijkstra algorithm.
5) **isConnected** - check the connectivity of the graph, return true/false.
6) **copy** - Compute a deep copy of this graph.
7) **getGraph** - return the graph.
8) **save** - save the graph into a file.
9) **load** - load a graph from given name.
10) **Dijkstra** - method used to find the shortest path in directed/undirected weighted graph.
11) **extractMin** - helper method for Dijkstra algorithm to extract the min in the Priority Queue.

