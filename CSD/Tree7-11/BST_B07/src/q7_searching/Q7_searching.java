/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package q7_searching;

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
public class Q7_searching {

    private String inputFile = "ex07_input.txt";
    private String outputFile = "ex07_output.txt";

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
    int n, m;
    ArrayList<Integer> a;
    BSTtree tree;
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
            m = sc.nextInt();
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

        tree.findNode(m);
        result = tree.getFindResult();
        if (result.equals("")) {
            result = "No";
        } 
        
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
        Q7_searching app = new Q7_searching();
        app.setFile(args);
        app.readFile();
        app.solve();
        app.writeFile();
    }

}
