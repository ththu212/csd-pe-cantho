package bst_b14;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author Admin
 */
public class BSTree {
    
    BSTNode root;
    String result;
    ArrayList<BSTNode> path;
    ArrayList<BSTNode> CS;

    /**
     * Construct khoi tao
     */
    public BSTree() {
        root = null;
        result = "";
        path = new ArrayList<>();
        CS = new ArrayList<>();
    }

    /**
     * tinh so nut cua cay
     *
     * @return
     */
    public int CalSize() {
        CS.clear();
        CalSize(root);
        return CS.size();
    }

    /**
     * Duyet in-order de tim so luong nut trong cay
     *
     * @param node
     */
    public void CalSize(BSTNode node) {
        if (node == null) {
            return;
        }
        CalSize(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            CS.add(node);
        }
        CalSize(node.getRightChild());
    }
    
    public BSTNode getRoot() {
        return root;
    }
    
    public ArrayList<BSTNode> getPath() {
        return path;
    }

    /**
     * Khoi tao lai result
     */
    public void resetResult() {
        result = "";
    }

    /**
     * Xoa dau , o cuoi cho traversalResult
     *
     * @return
     */
    public String getTraversalResult() {
        int tmp = result.length();
        if (!"".equals(result)) {
            return (result.substring(0, tmp - 2) + ".");
        } else {
            return "Result fail!";
        }
    }

    /**
     * Xoa dau -> cho findResult
     *
     * @return
     */
    public String getFindResult() {
        int tmp = result.length();
        if (!"".equals(result)) {
            return (result.substring(0, tmp - 3) + ".");
        } else {
            return "Result fail!";
        }
    }

    /**
     * Xoa dau '_' cho viec tim DFS va BFS
     *
     * @return
     */
    public String getTraversalDBResult() {
        int tmp = result.length();
        if (!"".equals(result)) {
            return (result.substring(0, tmp - 1) + ".");
        } else {
            return "Result fail!";
        }
    }

    /**
     * Them node vao cay
     *
     * @param data
     */
    public void addNode(int data) {
        if (root == null) {
            root = new BSTNode(data);
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
     * Dung de lay lai nut trong viec can bang cay
     *
     * @param data
     * @param count
     */
    public void addNode(int data, int count) {
        if (root == null) {
            root = new BSTNode(data);
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
//                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
                
            }
        }
    }
    
    public void preOrder() {
        result = "";
        path.clear();
        preOrder(root);
        
    }

    //Duyet theo Pre-order
    private void preOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
            path.add(node);
//            System.out.print(node.getData() + ", ");
        }
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }
    
    public void inOrder() {
        result = "";
        inOrder(root);
    }

    //Duyet theo In-order
    private void inOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.getLeftChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
//            System.out.print(node.getData() + ", ");
        }
        inOrder(node.getRightChild());
    }
    
    public void postOrder() {
        result = "";
        postOrder(root);
    }

    //Duyet theo Post-order
    private void postOrder(BSTNode node) {
        if (node == null) {
            return;
        }
        postOrder(node.getLeftChild());
        postOrder(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
//            System.out.print(node.getData() + ", ");
        }
        
    }

    //Duyet tim kiem phan tu trong cay
    /**
     * Tim node trong cay
     *
     * @param data
     * @return
     */
    public BSTNode findNode(int data) {
        path.clear();
        resetResult();
        BSTNode node = this.root;
        while (node != null) {
//            System.out.print(node.getData() + "-> ");
            result += (node.getData() + "-> ");
            path.add(node);
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        path.clear();
        return null;
    }

    /**
     * De ham goi tu ngoai xoa nut
     *
     * @param data
     * @return
     */
    public boolean removeNode(int data) {
        BSTNode node = findNode(data);
        // neu la nut goc va cay chi co 1 nut thi xoa toan bo cay
        if (this.root.isRoot() && CalSize() == 1) {
            clear();
            return true;
        } else {
            return removeNode(node);
        }
    }

    /**
     * De quy de xoa nut
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
     * dung de goi tu ngoai xoa tat ca cac nut giong nhau khoi cay
     *
     * @param data
     * @return
     */
    public boolean removeAllCountNode(int data) {
        BSTNode node = findNode(data);
        if (this.root.isRoot() && CalSize() == 1) {
            clear();
            return true;
        } else {
            return removeAllCountNode(node);
        }
    }

    /**
     * de quy dung de xoa tat ca cac nut giong nhau khoi cay
     *
     * @param node
     * @return
     */
    public boolean removeAllCountNode(BSTNode node) {
        if (node == null) {
            return false;
        }
        node.setCount(0);
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
    }

    /**
     * xoa ca cay
     */
    public void clear() {
        clear(this.root);
        this.root = null;
    }

    /**
     * goi de quy de xoa tat ca cac nut khoi cay
     *
     * @param node
     */
    public void clear(BSTNode node) {
        if (node == null) {
            return;
        }
        if (node.isLeaf() && node.isRoot()) {       //Neu cay chi co 1 nut thi xoa lun khong can goi parent
            node.removeLeafChild(node);
        } else {
            if (node.isLeaf()) {
                node.getParent().removeLeafChild(node);
            } else {
                if (node.hasRightChild()) {
                    clear(node.getRightChild());
                }
                if (node.hasLeftChild()) {
                    clear(node.getLeftChild());
                }
            }
        }
    }

    /**
     * Duyet DFS
     */
    public void DFS() {
        resetResult();
        Stack<BSTNode> s = new Stack<>();
        s.add(root);
        
        BSTNode node;
        while (!s.isEmpty()) {
            node = s.pop();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    result += (node.getData() + " ");
                }
            }
            if (node.hasRightChild()) {
                s.add(node.getRightChild());
            }
            if (node.hasLeftChild()) {
                s.add(node.getLeftChild());
            }
        }
    }

    /**
     * Duyet BFS
     */
    public void BFS() {
        resetResult();
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);
        
        BSTNode node;
        while (!q.isEmpty()) {
            node = q.poll();
            if (node != null) {
                for (int i = 0; i < node.getCount(); i++) {
                    result += (node.getData() + " ");
                }
            }
            if (node.hasLeftChild()) {
                q.add(node.getLeftChild());
            }
            if (node.hasRightChild()) {
                q.add(node.getRightChild());
            }
        }
    }
    
    private void BST2Array(BSTNode node) {
        if (node == null) {
            return;
        }
        BST2Array(node.getLeftChild());

        /*for (int i = 0; i < node.getCount(); i++) {
            result += (node.getData() + ", ");
        }*/
        BST2Array(node.getRightChild());
    }

    /**
     * Can bang cay
     */
//    public void balancing() {
//        treeData.clear();
//        BST2Array(this.root);       //luu BST tang dan 
//
//        this.clear();       //xoa tat ca cac node tu BST
//        Queue<BSTRange> q = new LinkedList<>();
//
//        q.add(new BSTRange(0, treeData.size() - 1));
//
//        BSTRange range;
//
//        NodeData nodeData;
//
//        int middleIndex, leftIndex, rightIndex;
//
//        while (!q.isEmpty()) {
//            range = q.poll();
//            leftIndex = range.getLeftIndex();
//            rightIndex = range.getRightIndex();
//            if (range.getLeftIndex() <= range.getRightIndex()) {
//                middleIndex = (range.getLeftIndex() + range.getRightIndex()) / 2;       //Lay phan tu o giua ra them vao cay
//                nodeData = treeData.get(middleIndex);
//                this.addNode(nodeData.getData(), nodeData.getCount());
//
//                q.add(new BSTRange(leftIndex, middleIndex - 1));
//                q.add(new BSTRange(middleIndex + 1, rightIndex));
//            }
//        }
//    }
    public BSTNode findNode2(int data) {
        BSTNode node = this.root;
        while (node != null) {
//            System.out.print(node.getData() + "-> ");
            if (data == node.getData()) {
                return node;
            } else if (data < node.getData()) {
                node = node.getLeftChild();
            } else {
                node = node.getRightChild();
            }
        }
        return null;
    }
    
    public String getParent(int data, ArrayList<BSTNode> value) {
        return getParent(root, data, value);
    }
    
    public String getParent(BSTNode node, int data, ArrayList<BSTNode> value) {
        preOrder();
        ArrayList<BSTNode> temp;
        temp = value;
        for (BSTNode v : temp) {
            for (BSTNode s : path) {
                if (s.getData() != v.getData()) {
                    if (s.getParent() == v.getParent()) {
                        result += s.getData() + " ";
                    } else {
                        result += "null ";
                    }
                }
            }
        }
        return result;
    }
    
    public int getLevel(int data) {
        return getLevel(root, data, 0);
    }
    
    public int getLevel(BSTNode node, int data, int level) {
        if (node == null) {
            return 0;
        }
        
        if (node.getData() == data) {
            return level;
        }
        
        int leftLevel = getLevel(node.getLeftChild(), data, level + 1);
        if (leftLevel != 0) {
            return leftLevel;
        }
        
        int rightLevel = getLevel(node.getRightChild(), data, level + 1);
        return rightLevel;
    }
    
    public String fullTree(int data) {
        result = "";
        fullTree(root, data);
        return result;
    }
    
    public void fullTree(BSTNode node, int value) {
        if (!node.isLeaf()) {
            if (node.hasLeftChild() && node.hasRightChild()) {
                if (node.getLeftChild().getData() == value) {
                    result += node.getRightChild().getData() + ", ";
                    return;
                } else if (node.getRightChild().getData() == value) {
                    result += node.getLeftChild().getData() + ", ";
                    return;
                }
                
            } else if (node.hasLeftChild() || node.hasRightChild()) {
                if (node.hasLeftChild() && (node.getLeftChild().getData() == value)) {
                    result += "null, ";
                    return;
                }
                if (node.hasRightChild() && (node.getRightChild().getData() == value)) {
                    result += "null, ";
                    return;
                }
            } else {
                result += "null, ";
            }
            
        }
        if (node.hasLeftChild()) {
            fullTree(node.getLeftChild(), value);
        }
        if (node.hasRightChild()) {
            fullTree(node.getRightChild(), value);
        }
        
    }
}
