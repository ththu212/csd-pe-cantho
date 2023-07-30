/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b14;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Vo Thanh Thu - CE170522
 */
public class BST_B14 {

    private String inputFile = "ex14_input.txt";
    private String outputFile = "ex14_output.txt";

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
    int n;
    int a;
    int[] temp;
    ArrayList<Integer> arr;
    String result;

    public void readData() {
        try {
            Scanner sc = new Scanner(new File(fi));

            arr = new ArrayList<Integer>();
            n = sc.nextInt();
            int val;

            for (int i = 0; i < n; i++) {
                val = sc.nextInt();
                arr.add(val);
            }
            a = sc.nextInt();
            temp = new int[a];
            for (int i = 0; i < a; i++) {
                val = sc.nextInt();
                temp[i] = val;
            }
            sc.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
    }

    public void solve() {
        result = "";
        tree = new BSTree();
        for (int i = 0; i < n; i++) {
            tree.addNode(arr.get(i));
        }
        for (int i = 0; i < a; i++) {
            result += tree.fullTree(temp[i]);
        }
        result = result.substring(0, result.length() - 2);
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
        BST_B14 app = new BST_B14();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
