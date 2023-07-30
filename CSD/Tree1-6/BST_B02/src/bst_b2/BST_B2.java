/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BST_B2 {

    private String inputFile = "ex02_input.txt";
    private String outputFile = "ex02_output.txt";

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

    int n, m;
    ArrayList<Integer> myArr, myArrDelete;
    BSTree tree;
    String pre = "", post = "";

    public void readData() {
        try {
            Scanner ip = new Scanner(new File(fi));
            n = ip.nextInt();
            myArr = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                int temp;
                temp = ip.nextInt();
                myArr.add(temp);
            }
            m = ip.nextInt();
            myArrDelete = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                int temp;
                temp = ip.nextInt();
                myArrDelete.add(temp);
            }
            ip.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }

    }

    public void solve() {
        tree = new BSTree();
        for (Integer i : myArr) {
            tree.addNode(i);
        }
        for (Integer i : myArrDelete) {
            tree.removeAllCountNode(i);
        }
        tree.preOrder();
        pre = tree.getTraversalResult();
        tree.postOrder();
        post = tree.getTraversalResult();
    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            fw.write(pre + "\n");
            fw.write(post);

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
        BST_B2 app = new BST_B2();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
