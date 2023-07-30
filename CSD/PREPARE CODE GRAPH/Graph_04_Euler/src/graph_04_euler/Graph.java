package graph_04_euler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int vertices;
    private List<List<Integer>> adjacencyList;

    public Graph() {
    }

    public Graph(int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjacencyList.add(new LinkedList<>());
        }
    }

    public void addEdge(int source, int destination) {
        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source);
    }

    public List<Integer> eulerianCycle(int startVertex) {
        int[] visited = new int[vertices];
        List<Integer> cycle = new ArrayList<>();

        eulerianCycleUtil(startVertex, visited, cycle);

        return cycle;
    }

    private void eulerianCycleUtil(int vertex, int[] visited, List<Integer> cycle) {
        visited[vertex] = 1;

        for (Integer adjacentVertex : adjacencyList.get(vertex)) {
            if (visited[adjacentVertex] == 0) {
                cycle.add(vertex);
                eulerianCycleUtil(adjacentVertex, visited, cycle);
            }
        }
    }

    public void eulerianPath(int startVertex) {
        int[] visited = new int[vertices];
        List<Integer> path = new ArrayList<>();

        eulerianPathUtil(startVertex, visited, path);

        System.out.println("Eulerian path: " + path);
    }

    private void eulerianPathUtil(int vertex, int[] visited, List<Integer> path) {
        visited[vertex] = 1;

        for (Integer adjacentVertex : adjacencyList.get(vertex)) {
            if (visited[adjacentVertex] == 0) {
                path.add(vertex);
                eulerianPathUtil(adjacentVertex, visited, path);
            }
        }

        if (!adjacencyList.get(vertex).isEmpty())
            path.add(vertex);
    }
    
}

//public class EulerianGraph {
//    public static void main(String[] args) {
//        int[][] adjacencyMatrix = {
//                {0, 1, 1, 0, 0},
//                {1, 0, 1, 1, 1},
//                {1, 1, 0, 0, 0},
//                {0, 1, 0, 0, 1},
//                {0, 1, 0, 1, 0}
//        };
//
//        int vertices = adjacencyMatrix.length;
//        Graph graph = new Graph(vertices);
//
//        for (int i = 0; i < vertices; i++) {
//            for (int j = i + 1; j < vertices; j++) {
//                if (adjacencyMatrix[i][j] == 1) {
//                    graph.addEdge(i, j);
//                }
//            }
//        }
//
//        int startVertex = 0;
//        graph.eulerianCycle(startVertex);
//        graph.eulerianPath(startVertex);
//    }
//}
