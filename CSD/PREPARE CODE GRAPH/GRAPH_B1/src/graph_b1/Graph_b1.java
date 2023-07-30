
package graph_b1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * GRAPH 1.DFS 2.BFS
 */
public class Graph_b1 {

    private String inputFile = "bfs_input.txt";
    private String outputFile = "bfs_output.txt";

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

    int n, m, s;
    Gdrawing t = new Gdrawing();
    String result = "", result1 = "";

    public void readData() {
        try {
            Scanner ip = new Scanner(new File(fi));
            n = ip.nextInt();
            t.setNumVertex(n);
            m = ip.nextInt();
            s = ip.nextInt();
            for (int i = 0; i < m; i++) {
                int from, to;
                from = ip.nextInt();
                to = ip.nextInt();
                t.addVertex(from, to);
            }
            ip.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void solve() {
        t.DFS(s);
        result = t.printTravel();
        result = result.substring(0, result.length() - 1);

        t.BFS(s);
        result1 = t.printTravel();
        result1 = result1.substring(0, result1.length() - 1);
    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(result + "\n");
            fw.write(result1);

            fw.flush();
            fw.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph_b1 app = new Graph_b1();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
