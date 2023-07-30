
package graph_b8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import javax.print.attribute.standard.PDLOverrideSupported;
import graph_b8.Gdrawing;

/**
 *
 * GRAPH CUT VERTEX
 */
public class GRAPH_B8 {
    
    private String inputFile = "cutVertex_input.txt";
    private String outputFile = "cutVertex_output.txt";
    
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
    int result = 0;
    String resultString = "";
    
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
            t.Edges();
            ip.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        
    }
    
    public void solve() {
        t.CutEdges();
        result = t.getCountCutVertex();
        resultString = t.getCountVertexResult();
    }
    
    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(result + "\n" + resultString.substring(0, resultString.length() - 1));
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
        GRAPH_B8 app = new GRAPH_B8();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }
    
}
