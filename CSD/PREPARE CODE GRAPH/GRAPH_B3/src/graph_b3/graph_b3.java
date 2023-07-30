package graph_b3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import graph_b3.Gdrawing;

/**
 *
 * GRAPH DEM DINH CO LAP
 */
public class graph_b3 {

    private String inputFile = "isolatedVertices_input.txt";
    private String outputFile = "isolatedVertices_output.txt";

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

    int n;
    Gdrawing t = new Gdrawing();
    String result = "";

    public void readData() {
        try {
            Scanner ip = new Scanner(new File(fi));
            n = ip.nextInt();
            t.setNumVertex(n);
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int temp;
                    temp = ip.nextInt();
                    t.addVertex(temp, i, j);
                }
            }
            ip.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void solve() {
        t.isolatedVertex();
        result = t.getIsolatedVertex();
        if (!result.equals("")) {
            result = result.substring(0, result.length() - 1);
        } else {
            result = "There is a connected graph";
        }
    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(result);

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
        graph_b3 app = new graph_b3();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }
}
