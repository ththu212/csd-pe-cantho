/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b12;

/**
 *
 * @author CE170522
 */
public class BSTNode {

    //=>thuộc tính của node
    private int data;
    private int count;//xu ly truong hop trung gia tri tren cay
    private BSTNode leftChild;
    private BSTNode rightChild;
    private BSTNode parent;
    //=>thông tin khác của node
    private int level;
    private int order;//số thứ tự của node

    public enum NodeType {//để phân biệt khi thêm là thêm vô node con trái hay phải
        LEFT_CHILD,
        RIGHT_CHILD,
    }
    //=>cho vc vẽ trên giao diện
    public static final int LEVER_DY = 60;//là khoảng cách giữa 2 level, cho là 60px, cho càng lớn thì khoảng cách giữa 2 level càng lớn
    private int x;
    private int y;
    private int width;//độ rộng tính từ biên bên trái cho đến vị trí tọa độ của node

    public BSTNode(int data) {
        this.data = data;
        this.count = 1;//nếu trùng thì tăng lên 2 3...
        this.leftChild = this.rightChild = this.parent = null;
        //nếu thiết lập con trái con phải thì cập nhật con trái con phải theo nút cha của nó
        this.level = 0;
        this.order = 0;

        //phục vụ cho vẽ giao diện
        this.x = 0;
        this.y = 0;
        this.width = 0;
    }

    public BSTNode(int data, int y, int screenWidth) {
        this.data = data;
        this.count = 1;//nếu trùng thì tăng lên 2 3...
        this.leftChild = this.rightChild = this.parent = null;
        //nếu thiết lập con trái con phải thì cập nhật con trái con phải theo nút cha của nó
        this.level = 0;
        this.order = 0;

        //phục vụ cho vẽ giao diện
        this.x = this.width = screenWidth / 2;
        this.y = y;
    }
    //phục vụ cho cân bằng cây

    public BSTNode(int data, int count) {
        this.data = data;
        this.count = count;//nếu trùng thì tăng lên 2 3...
        this.leftChild = this.rightChild = this.parent = null;
        //nếu thiết lập con trái con phải thì cập nhật con trái con phải theo nút cha của nó
        this.level = 0;
        this.order = 0;

        //phục vụ cho vẽ giao diện
        this.x = 0;
        this.y = 0;
        this.width = 0;
    }

    //kiểm tra xem phải node lá không, k có con trái và con phải
    public boolean isLeaf() {
        return this.leftChild == null && this.rightChild == null;
    }

    //kiểm tra xem có node con không
    public boolean hasChild() {
        return !isLeaf();//không phải node lá thì là node có con
    }

    //kiểm tra xem node có con trái
    public boolean hasLeftChild() {
        return this.leftChild != null;
    }

    //kiểm tra xem node có con phải
    public boolean hasRightChild() {
        return this.rightChild != null;
    }

    //kiểm tra xem có phải node gốc không
    public boolean isRoot() {
        return this.parent == null;//k có node cha
    }

    //kiểm tra có phải node nằm trong hay không
    public boolean isInside() {
        return !isRoot() && !isLeaf();//node nằm trong là node k phải node gốc cũng k phải node lá
    }

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

    //thiết lập con trái
    public void setLeftChild(BSTNode leftChild) {
        this.leftChild = leftChild;
        //cập nhận con trái thì con trái đó phải nhận node hiện tại(this) làm node cha 
        // trc khi sửa this.leftChild.setParent(this, NodeType.LEFT_CHILD);
        if (leftChild != null) {//bỏ vô if, nếu leftchild khác node thì ms set đc
            this.leftChild.setParent(this, NodeType.LEFT_CHILD);
        }
    }

    public BSTNode getRightChild() {
        return rightChild;
    }

    //thiết lập con phải
    public void setRightChild(BSTNode rightChild) {
        this.rightChild = rightChild;
        //cập nhận con phải thì con phải đó phải nhận node hiện tại(this) làm node cha 
        //sửa tương tự theo setleftchild
        if (rightChild != null) {
            this.rightChild.setParent(this, NodeType.RIGHT_CHILD);
        }
    }

    public BSTNode getParent() {
        return parent;
    }

    public void setParent(BSTNode parent, NodeType type) {
        //để cập nhật lại node
        this.parent = parent;
        this.level = parent.getLevel() + 1;//cập nhật lại level
        //cập nhật lại số thứ tự(order)
        if (type == NodeType.LEFT_CHILD) {
            this.order = parent.getOrder() * 2 + 1;
        } else {
            this.order = parent.getOrder() * 2 + 2;
        }
        //=>cho vs vẽ
        this.width = parent.getWidth() / 2;//lấy độ rộng của đời cha chia 2 đc ở giữ là đời con

        if (type == NodeType.LEFT_CHILD) {
            //this.x = parent.getX() - (this.width + 5 - this.level);//cho đẹp hơn
            this.x = parent.getX() - (this.width);
        } else {
            this.x = parent.getX() + (this.width);
        }
        this.y = parent.getY() + LEVER_DY;
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

    public BSTNode findMaxNode() {//qua phải liên tục vì node lớn thì chuyển qua phải
        BSTNode node = this;
        while (node.hasRightChild()) {
            node = node.getRightChild();
        }
        return node;
    }

    public BSTNode findMinNode() {//qua trái liên tục vì node lớn thì chuyển qua trái
        BSTNode node = this;
        while (node.hasLeftChild()) {
            node = node.getLeftChild();
        }
        return node;
    }

    //hàm gỡ bỏ 1 node lá
    public void removeLeafChild(BSTNode node) {
        if (node.isLeaf()) {//phải là node lá thì mới gỡ đc
            if (this.hasLeftChild()) {
                //nếu con bên trái bằng với node mà ta cần remove thì set node đó null
                if (this.getLeftChild().getData() == node.getData()) {
                    this.setLeftChild(null);//xóa bỏ node con trái
                    //tới đây phải cải tiến lại setLeftChild nếu không sẽ bị lỗi
                }
            }
            if (this.hasRightChild()) {
                //nếu con bên phải bằng với node mà ta cần remove thì set node đó null
                if (this.getRightChild().getData() == node.getData()) {
                    this.setRightChild(null);//xóa bỏ node con phải
                    //tới đây phải cải tiến lại setLeftChild nếu không sẽ bị lỗi
                }
            }
        }
    }
}
