/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.List;

public class EulerPathFinder {

    private int[][] graph;
    private List<Integer> eulerPath;

    public EulerPathFinder(int[][] graph) {
        this.graph = graph;
        eulerPath = new ArrayList<>();
    }

    public List<Integer> findEulerPath() {
        int numVertices = graph.length;
        int numEdges = countEdges();

        // Kiểm tra điều kiện tồn tại đường đi Euler
        if (!isValidGraph(numVertices, numEdges)) {
            System.out.println("Đồ thị không tồn tại đường đi Euler.");
            return eulerPath;
        }

        // Tìm đường đi Euler
        int startVertex = findStartVertex();
        findEulerPathUtil(startVertex);

        return eulerPath;
    }

    private void findEulerPathUtil(int vertex) {
        while (true) {
            eulerPath.add(vertex); // Thêm đỉnh vào đường đi Euler

            int[] neighbors = graph[vertex];
            int nextVertex = -1;
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] > 0) {
                    // Kiểm tra xem cạnh (vertex, i) có thể đi được không
                    if (isValidEdge(vertex, i)) {
                        removeEdge(vertex, i); // Xóa cạnh (vertex, i) khỏi đồ thị

                        nextVertex = i;
                        break;
                    }
                }
            }

            if (nextVertex == -1) {
                break; // Không tìm thấy đỉnh kề tiếp theo
            }

            vertex = nextVertex;
        }
    }

    private int countEdges() {
        int count = 0;
        for (int[] neighbors : graph) {
            for (int neighbor : neighbors) {
                count += neighbor;
            }
        }
        return count / 2; // Đồ thị vô hướng, mỗi cạnh được đếm 2 lần
    }

    private boolean isValidGraph(int numVertices, int numEdges) {
        int oddDegreeVertices = 0;
        for (int[] neighbors : graph) {
            int count = 0;
            for (int neighbor : neighbors) {
                count += neighbor;
            }
            if (count % 2 != 0) {
                oddDegreeVertices++;
            }
        }

        return (numEdges == 0 && oddDegreeVertices == 0) || (numEdges > 0 && oddDegreeVertices == 2);
    }

    private boolean isValidEdge(int vertex1, int vertex2) {
        // Kiểm tra xem việc xóa cạnh (vertex1, vertex2) có làm đồ thị bị tách thành nhiều thành phần không liên thông
        int[] temp1 = graph[vertex1];
        int[] temp2 = graph[vertex2];

        removeEdge(vertex1, vertex2);

        boolean[] visited = new boolean[graph.length];
        dfs(vertex1, visited);

        addEdge(vertex1, vertex2);

        return !visited[vertex2];
    }

    private void dfs(int vertex, boolean[] visited) {
        visited[vertex] = true;

        int[] neighbors = graph[vertex];
        for (int i = 0; i < neighbors.length; i++) {
            if (neighbors[i] > 0 && !visited[i]) {
                dfs(i, visited);
            }
        }
    }

    private void removeEdge(int vertex1, int vertex2) {
        graph[vertex1][vertex2]--;
        graph[vertex2][vertex1]--;
    }

    private void addEdge(int vertex1, int vertex2) {
        graph[vertex1][vertex2]++;
        graph[vertex2][vertex1]++;
    }

    private int findStartVertex() {
        int startVertex = 0;
        int oddDegreeVertex = -1;

        for (int i = 0; i < graph.length; i++) {
            int[] neighbors = graph[i];
            int count = 0;
            for (int neighbor : neighbors) {
                count += neighbor;
            }
            if (count % 2 != 0) {
                oddDegreeVertex = i;
                break;
            }
        }

        if (oddDegreeVertex != -1) {
            startVertex = oddDegreeVertex;
        }

        return startVertex;
    }

    public static void main(String[] args) {
        int[][] graph = {
            {0, 1, 1, 1, 0},
            {1, 0, 1, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 0, 0, 0, 1},
            {0, 0, 0, 1, 0}

        };

        EulerPathFinder eulerPathFinder = new EulerPathFinder(graph);
        List<Integer> eulerPath = eulerPathFinder.findEulerPath();
        System.out.println("Đường đi Euler: " + eulerPath);
    }
}
