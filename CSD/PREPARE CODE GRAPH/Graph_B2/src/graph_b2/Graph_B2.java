
package graph_b2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * GRAPH 1.PRIME 2.KRUSAL
 */
public class Graph_B2 {

    private String inputFile = "ex01_input.txt";
    private String outputFile = "ex01_output.txt";

    private GPaper graph;
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

    private int[][] a;
    private int nov;
    private int start;

    public void readData() {
        try {
            Scanner scan = new Scanner(new File(fi));
            nov = scan.nextInt();
            a = new int[nov][nov];
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

    String result = "";

    public void solve() {
        graph = new GPaper();
        graph.setNumberOfVertices(nov);
        for (int i = 0; i < nov; i++) {
            graph.getVertices().add(new GVertex(i, 0, 0));

        }
        for (int i = 0; i < this.nov; i++) {
            for (int j = 0; j < this.nov; j++) {
                graph.getGraph()[i][j] = a[i][j];
                if (i < j && graph.getGraph()[i][j] > 0) {
                    graph.getEdges().add(new GEdge(graph.getGraph()[i][j], graph.getVertices().get(i), graph.getVertices().get(j)));
                }
            }
        }

        graph.prim();
        result = graph.primPath;
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
        Graph_B2 app = new Graph_B2();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
