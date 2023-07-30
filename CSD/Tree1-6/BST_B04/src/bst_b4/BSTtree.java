package bst_b4;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Admin
 */
public class BSTtree {

    BSTNode var;

    BSTNode root;
    String result;
    ArrayList<BSTNode> path = new ArrayList<>();
//    ArrayList<NodeData> treeData = new ArrayList<>();

    //For drawing
    int screenWidth;
    int yMin;

    /**
     * Contructor khoi tao
     */
    public BSTtree() {
        root = null;
        this.screenWidth = 0;
        this.yMin = 0;
        result = "";
        ArrayList<BSTNode> path = new ArrayList<>();
//        ArrayList<NodeData> treeData = new ArrayList<>();
    }

    /**
     * Contructor khoi tao
     *
     * @param screenWidth
     * @param yMin
     */
    public BSTtree(int screenWidth, int yMin) {
        root = null;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
        ArrayList<BSTNode> path = new ArrayList<>();
//        ArrayList<NodeData> treeData = new ArrayList<>();
    }

    public String getNodeInside() {
        result = "";
        getNodeInside(root);
        return result;
    }

    public void getNodeInside(BSTNode node) {
        for (int i = 0; i < node.getCount(); i++) {
            if (node.isInside()) {
                result += node.getData() + ",";
            }
            
            if (node.hasLeftChild()) {
                getNodeInside(node.getLeftChild());
            }
            if (node.hasRightChild()) {
                getNodeInside(node.getRightChild());
            }
        }
    }

    /**
     *
     * @return
     */
    public BSTNode getRoot() {
        return this.root;
    }

    /**
     * xoa bo dau phay cuoi khi in Traversal
     *
     * @return
     */
    public String getTraversalResult() {
        int hn = result.length();
        return result.substring(0, hn - 1);
    }

    /**
     * them nut
     *
     * @param data
     */
    public void addNode(int data) {
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(data));
                        isAdded = true;
                    }

                } else if (data > node.getData()) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(data));
                        isAdded = true;
                    }
                } else {
//                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }

        }
    }

    /**
     * them nut trong can bang cay
     *
     * @param data
     * @param count
     */
    public void addNode(int data, int count) {
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {
                        node = node.getLeftChild();
                    } else {
                        node.setLeftChild(new BSTNode(data, count));
                        isAdded = true;
                    }

                } else if (data > node.getData()) {
                    if (node.hasRightChild()) {
                        node = node.getRightChild();
                    } else {
                        node.setRightChild(new BSTNode(data, count));
                        isAdded = true;
                    }
                } else {
                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }

        }
    }

    /**
     * Duyet tuan tu Pre-order Traversal
     */
    public void preOrder() {
        result = "";
        preOdrer(root);

    }

    private void preOdrer(BSTNode node) {
        if (node == null) {
            return;
        }

        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");
        }
        preOdrer(node.getLeftChild());
        preOdrer(node.getRightChild());
    }

    /**
     * Duyet trung tu In-order Traversal
     */
    public void inOrder() {
        result = "";
        inOdrer(root);

    }

    private void inOdrer(BSTNode node) {
        if (node == null) {
            return;
        }
        inOdrer(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");
        }
        inOdrer(node.getRightChild());
    }

    /**
     * Duyet hau tu Post-order Traversal
     */
    public void postOrder() {
        result = "";
        postOdrer(root);

    }

    private void postOdrer(BSTNode node) {
        if (node == null) {
            return;
        }
        postOdrer(node.getLeftChild());
        postOdrer(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<BSTNode> getPath() {
        return this.path;
    }

    /**
     * xoa bo dau -> cuoi khi in Find
     *
     * @return
     */
    public String getFindResult() {
        int hn = result.length();
        if (result == "") {
            return result;
        }
        return result.substring(0, hn - 2);
    }

    /**
     * Tim kiem nut
     *
     * @param data
     * @return
     */
    public BSTNode findNode(int data) {
        BSTNode node = this.root;
        result = "";

        path.clear();

        while (node != null) {
//            System.out.print(node.getData() + "->");
            result += node.getData() + "->";
            path.add(node);
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        result = "";

        path.clear();
        return null;
    }

    /**
     * Xoa nut
     *
     * @param data
     * @return
     */
    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        return removeNode(node);
    }

    /**
     * De qui xoa nut co con
     *
     * @param node
     * @return
     */
    public boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        }
        node.setCount(node.getCount() - 1);
        if (node.getCount() == 0) {
            if (node.isLeaf()) {
                node.getParent().removeLeafChild(node);
                return true;
            } else {
                BSTNode incomer = null;
                if (node.hasLeftChild()) {
                    incomer = node.getLeftChild().findMaxNode();
                } else {
                    incomer = node.getRightChild().findMinNode();
                }
                node.setData(incomer.getData());
                node.setCount(incomer.getCount());

                incomer.setCount(1);
                return removeNode(incomer);
            }
        } else {
            return true;
        }
    }

    /**
     * Xoa tat ca nut ra khoi cay
     */
    public void clear() {
        clear(this.root);
        this.root = null;
    }

    /**
     * De qui xoa nut hien thoi
     *
     * @param node
     */
    public void clear(BSTNode node) {
        if (node == null) {
            return;
        }
        if (node.isLeaf()) {
            node.getParent().removeLeafChild(node);
        } else {
            clear(node.getLeftChild());
            clear(node.getRightChild());
        }
    }

    void BFS() {
        this.result = "";
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);

        BSTNode node;
        while (!q.isEmpty()) {
            node = q.poll();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.println(node.getData() + ",");
                    result += node.getData() + ",";
                }
                q.add(node.getLeftChild());
                q.add(node.getRightChild());
            }
        }
    }

    void DFS() {
        this.result = "";
        Stack<BSTNode> s = new Stack<>();
        s.add(root);

        BSTNode node;
        while (!s.isEmpty()) {
            node = s.pop();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.println(node.getData() + ",");
                    result += node.getData() + ",";
                }
                s.add(node.getRightChild());
                s.add(node.getLeftChild());
            }
        }
    }

//    private void BST2Array(BSTNode node) {
//        if (node == null) {
//            return;
//        }
//        BST2Array(node.getLeftChild());
//        treeData.add(new NodeData(node.getData(), node.getCount()));
//        BST2Array(node.getRightChild());
//    }
//
//    /**
//     * Can bang cay
//     */
//    public void balancing() {
//        treeData.clear();
//        BST2Array(this.root); // store BST into ascending ordered array
//
//        this.clear(); //remove all node in the BST
//        Queue<BSTRange> q = new LinkedList<>();
//        q.add(new BSTRange(0, treeData.size() - 1));
//        BSTRange range;
//        NodeData nodeData;
//        int middleIndex, leftIndex, rightIndex;
//        while (!q.isEmpty()) {
//            range = q.poll();
//            leftIndex = range.getLeftIndex();
//            rightIndex = range.getRightIndex();
//            if (leftIndex <= rightIndex) {
//                middleIndex = (leftIndex + rightIndex) / 2;
//                nodeData = treeData.get(middleIndex);
//                this.addNode(nodeData.getData(), nodeData.getCount());
//
//                q.add(new BSTRange(leftIndex, middleIndex - 1));
//                q.add(new BSTRange(middleIndex + 1, rightIndex));
//            }
//        }
//    }
}
