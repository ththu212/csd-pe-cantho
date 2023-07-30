/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b03;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *BST leaf counting 
 * 
 */
//DE YEU CAU DEM SO LA THI MO CMT LEN
public class BST_B03 {

    private String inputFile = "ex03_input.txt";
    private String outputFile = "ex03_output.txt";

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
    ArrayList<Integer> myArr;
    BSTree tree;
    String result = "";
    //int dem = 0; PHAN DEM SO LA

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
        //dem= tree.count(); PHAN DEM SO LA
        result = tree.findLeaf();
        result = result.substring(0, result.length() - 1);
    }

    public void printResult() {
        try {
            FileWriter fw = new FileWriter(fo);
            //fw.write(dem + "\n"); PHAN DEM SO LA
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
        BST_B03 app = new BST_B03();
        app.setFile(args);
        app.readData();
        app.solve();
        app.printResult();
    }

}
