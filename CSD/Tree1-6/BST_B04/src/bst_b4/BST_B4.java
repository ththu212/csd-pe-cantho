/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b4;

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
public class BST_B4 {

    private String inputFile = "ex04_input.txt";
    private String outputFile = "ex04_output.txt";

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
    int n;
    ArrayList<Integer> a;
    BSTtree tree;
    BSTNode node;
    String result;

    public void readFile() {
        try {
            a = new ArrayList<>();

            Scanner sc = new Scanner(new File(fi));
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
        tree = new BSTtree();
        for (int i = 0; i < n; i++) {
            tree.addNode(a.get(i));
        }
        result = tree.getNodeInside();
        result = result.substring(0, result.length()- 1);

    }

    public void writeFile() {
        try {
            FileWriter fw = new FileWriter(fo);

            fw.write(result);

            fw.flush();
            fw.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BST_B4 app = new BST_B4();
        app.setFile(args);
        app.readFile();
        app.solve();
        app.writeFile();
    }

}
