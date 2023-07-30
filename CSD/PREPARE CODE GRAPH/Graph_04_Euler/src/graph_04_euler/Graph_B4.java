package graph_04_euler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.List;
import java.util.Scanner;

/**
 *
 * GRAPH SHORTEST PATH
 */
public class Graph_B4 {

    private String inputFile = "ex01_input.txt";
    private String outputFile = "ex01_output.txt";

    private String fi, fo;

    public void setFile(String[] args) {
        if (args.length >= 2) {
            fi = args[0];
            fo = args[1];
        } else {
            fi = inputFile;
            fo = outputFile;
        }
    }
    
    Graph graph;
    
    private int[][] a;
    private int nov;
    private int start;
    String result;

    public void readData() {
        try {
            Scanner scan = new Scanner(new File(fi));
            nov = scan.nextInt();
            a = new int[nov][nov];
            start = scan.nextInt();
            for (int i = 0; i < this.nov; i++) {
                for (int j = 0; j < this.nov; j++) {
                    a[i][j] = scan.nextInt();
                }
            }
            scan.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void solve() {
        result = "";
         graph = new Graph(nov);
        for (int i = 0; i < nov; i++) {
            for (int j = i + 1; j < nov; j++) {
                if (a[i][j] == 1) {
                    graph.addEdge(i, j);
                }
            }
        }

        int startVertex = start;
        List<Integer> listVertex = graph.eulerianCycle(startVertex);
        result = listVertex.toString();
        System.out.println(result);
        graph.eulerianPath(startVertex);
    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(result);
            fw.flush();
            fw.close();
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Graph_B4 app = new Graph_B4();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
