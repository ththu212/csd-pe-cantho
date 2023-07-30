package bstree_16;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author
 */
public class BSTree_16 {

    private String inputFile = "ex16_input.txt";
    private String outputFile = "ex16_output.txt";

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

    //declare variable
    BSTree tree;
    int N;
    ArrayList<Integer> arr;
    String result;

    public void readData() {
        try {
            Scanner sc = new Scanner(new File(fi));

            arr = new ArrayList<Integer>();
            N = sc.nextInt();
            int val;

            for (int i = 0; i < N; i++) {
                val = sc.nextInt();
                arr.add(val);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void solve() {
        tree = new BSTree();
        for (int i = 0; i < N; i++) {
            tree.addNode(arr.get(i));
        }
        ArrayList<BSTNode> preOrderResult;
        tree.preOrder();
        preOrderResult = tree.getPath();
        result = "";
        for (int i = 0; i < preOrderResult.size(); i++) {
            int num = preOrderResult.get(i).getData();
            result += num + "[" + tree.getOrder(num) + "],";
        }
        if (result != "") {
            result = result.substring(0, result.length() - 1);
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
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        BSTree_16 app = new BSTree_16();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }
}
