package bst_b15;

/**
 *
 * @author Admin
 */
public class BSTNode {

    private int data;
    private int count;      //Khong chen gia tri trung vao cay!
    private BSTNode leftChild;
    private BSTNode rightChild;
    private BSTNode parent;

    //DRAWING
    private static final int LEVEL_DY = 60;
    private int level;
    private int order;
    private int x, y, width;

    public enum NodeType {
        LEFT_CHILD,
        RIGHT_CHILD
    }

    public BSTNode(int data) {
        this.data = data;
        this.count = 1;
        this.leftChild = this.rightChild = this.parent = null;
        this.level = 0;
        this.order = 0;

        this.x = 0;
        this.y = 0;
        this.width = 0;
    }

    public BSTNode(int data, int y, int widthScreen) {
        this.data = data;
        this.count = 1;
        this.leftChild = this.rightChild = this.parent = null;
        this.level = 0;
        this.order = 0;

        this.x = this.width = widthScreen / 2;
        this.y = y;
    }

    public BSTNode(int data, int count) {       //Danh cho can bang cay
        this.data = data;
        this.count = count;
        this.leftChild = this.rightChild = this.parent = null;
        this.level = 0;
        this.order = 0;

        this.x = 0;
        this.y = 0;
        this.width = 0;
    }

    public boolean isLeaf() {
        return (this.leftChild == null && this.rightChild == null);
    }

    public boolean hasChild() {
        return !isLeaf();
    }

    public boolean hasLeftChild() {
        return (this.leftChild != null);
    }

    public boolean hasRightChild() {
        return (this.rightChild != null);
    }

    public boolean isRoot() {
        return (this.parent == null);
    }

    public boolean isInside() {
        return !(isRoot() && isLeaf());
    }

    //-------------------------------------------------
    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BSTNode getLeftChild() {
        return leftChild;
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
        if (leftChild != null) {
            this.leftChild.setParent(this, NodeType.LEFT_CHILD);
        }
    }

    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
        if (rightChild != null) {
            this.rightChild.setParent(this, NodeType.RIGHT_CHILD);
        }
    }

    public BSTNode getParent() {
        return parent;
    }

    //Cap Nhat parent
    public void setParent(BSTNode parent, NodeType type) {
        this.parent = parent;
        this.level = parent.getLevel() + 1;
        if (type == NodeType.LEFT_CHILD) {
            this.order = parent.getOrder() * 2 + 1;
        } else {
            this.order = parent.getOrder() * 2 + 2;
        }

        this.width = parent.getWidth() / 2;
        if (type == NodeType.LEFT_CHILD) {
            this.x = parent.getX() - (this.width + 5 - this.level);     //Lam dep so do
        } else {
            this.x = parent.getX() + this.width;
        }
        this.y = parent.getY() + LEVEL_DY;

    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    /**
     *  Tim gia tri node lon nhat
     * @return
     */
    public BSTNode findMaxNode() {
        BSTNode node = this;
        while (node.hasRightChild()) {
            node = node.getRightChild();
        }
        return node;
    }

    /**
     *  Tim gia tri node nho nhat
     * @return
     */
    public BSTNode findMinNode() {
        BSTNode node = this;
        while (node.hasLeftChild()) {
            node = node.getLeftChild();
        }
        return node;
    }

    /**
     *  Xoa nut la
     * @param node
     * @return
     */
    public boolean removeLeafChild(BSTNode node) {
        if (node == null) {
            return false;
        }
        if (node.isLeaf()) {
            if (this.hasLeftChild()) {
                if (this.getLeftChild().getData() == node.getData()) {
                    this.setLeftChild(null);        //Xoa cai la ben trai
                    return true;
                }
            }
            if (this.hasRightChild()) {
                if (this.getRightChild().getData() == node.getData()) {
                    this.setRightChild(null);        //Xoa cai la ben phai
                    return true;
                }
            }
        }
        return false;
    }
}
