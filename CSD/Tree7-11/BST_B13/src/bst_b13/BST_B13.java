/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b13;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
public class BST_B13 {

    private String inputFile = "ex13_input.txt";
    private String outFile = "ex13_output.txt";

    private String fi, fo;

    public void setFile(String[] args) {
        if (args.length >= 2) {
            fi = args[0];
            fo = args[1];
        } else {
            fi = inputFile;
            fo = outFile;
        }
    }
    //declare variables

    int n;
    ArrayList<Integer> a;
    BSTree tree;
    String result;

    public void readData() {
        try {
            Scanner sc = new Scanner(new File(fi));

            a = new ArrayList<>();
            n = sc.nextInt();
            int value;
            for (int i = 0; i < n; i++) {
                value = sc.nextInt();
                a.add(value);
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void solve() {
        tree = new BSTree();
        for (int i = 0; i < n; i++) {
            tree.addNode(a.get(i));
        }
        ArrayList<BSTNode> node;
        tree.preOrder();

        node = tree.getPath();
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int num;
        for (BSTNode b : node) {
            num = b.getData();
            if (num >= max) {
                max = num;
            }
            if (num <= min) {
                min = num;
            }
        }
        result = max + "\n" + min;
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

    public static void main(String[] args) {
        BST_B13 app = new BST_B13();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
