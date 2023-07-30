/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bst_b12;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author CE170522
 */
public class BSTree {

    BSTNode root;
    String result;//để hiển thị chuỗi lên chỗ text field
    ArrayList<BSTNode> path;//để lưu lại mấy node trên đường đi, cho vc tô đỏ đường đi khi tìm thấy node
    ArrayList<NodeData> treeData;
    //để cho vs cân bằng
    //=>để vẽ 
    int screenWidth;//độ rộng của màn hình
    int yMin;//vị trí cây xuất phát từ tọa độ y 

    //không truyền gì hết chỉ để tạo ra cây
    public BSTree() {
        root = null;
        this.screenWidth = 0;
        this.yMin = 0;
        result = "";
        this.path = new ArrayList<>();
        this.treeData = new ArrayList<>();
    }

    //cho việc vẽ
    //truyền độ rộng màn hình vs vị trí để cập nhật giá trị để vẽ
    public BSTree(int screenWidth, int yMin) {
        root = null;
        this.screenWidth = screenWidth;
        this.yMin = yMin;
        result = "";
        this.path = new ArrayList<>();
        this.treeData = new ArrayList<>();
    }

    public BSTNode getRoot() {
        return this.root;
    }

    public String getTraversalResult() {
        if (result.endsWith(",")) {//cắt bỏ dấu phẩy dư khi sout ra giao diện
            result = result.substring(0, result.length() - 1);
        } else if (result.endsWith("->")) {//cắt bỏ -> dư khi sout ra giao diện
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public void addNode(int data) {
        //nếu cây chưa có node nào
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {//chưa xong thì add tiếp

                //Thêm vô bên tay trái
                //data nhỏ hơn node hiện tại thì thêm data bên trái,
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {//nếu node có con bên trái, thì cho đi qua bên trái
                        node = node.getLeftChild();
                    } else {//k có con bên trái, thì thêm node mới thành node lá
                        node.setLeftChild(new BSTNode(data));
                        isAdded = true;
                    }
                } //Thêm vô bên tay phải
                //data lớn hơn node hiện tại thì thêm data bên phải,
                else if (data > node.getData()) {
                    if (node.hasRightChild()) {//nếu node có con bên phải, thì cho đi qua bên phải
                        node = node.getRightChild();
                    } else {//k có con bên phải, thì thêm node mới thành node lá
                        node.setRightChild(new BSTNode(data));
                        isAdded = true;
                    }
                } //trường hợp bằng có nghĩa đã tồn tại giá trị nên tăng count lên
                else {
//                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    //cho cân bằng cây
    public void addNode(int data, int count) {
        //nếu cây chưa có node nào
        if (root == null) {
            root = new BSTNode(data, yMin, screenWidth);
        } else {
            boolean isAdded = false;
            BSTNode node = root;
            while (!isAdded) {//chưa xong thì add tiếp

                //Thêm vô bên tay trái
                //data nhỏ hơn node hiện tại thì thêm data bên trái,
                if (data < node.getData()) {
                    if (node.hasLeftChild()) {//nếu node có con bên trái, thì cho đi qua bên trái
                        node = node.getLeftChild();
                    } else {//k có con bên trái, thì thêm node mới thành node lá
                        node.setLeftChild(new BSTNode(data, count));
                        isAdded = true;
                    }
                } //Thêm vô bên tay phải
                //data lớn hơn node hiện tại thì thêm data bên phải,
                else if (data > node.getData()) {
                    if (node.hasRightChild()) {//nếu node có con bên phải, thì cho đi qua bên phải
                        node = node.getRightChild();
                    } else {//k có con bên phải, thì thêm node mới thành node lá
                        node.setRightChild(new BSTNode(data, count));
                        isAdded = true;
                    }
                } //trường hợp bằng có nghĩa đã tồn tại giá trị nên tăng count lên
                else {
                    node.setCount(node.getCount() + 1);
                    isAdded = true;
                }
            }
        }
    }

    public void preOrder() {
        result = "";
        preOrder(root);
    }

    public void preOrder(BSTNode node) {
        if (node == null) {//không có giá trị gì hết
            return;
        }
        //cây có thể có trùng lắp giá trị
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");//in mấy cái giống nhau
        }
        preOrder(node.getLeftChild());
        preOrder(node.getRightChild());
    }

    public void inOrder() {
        result = "";
        inOrder(root);
    }

    public void inOrder(BSTNode node) {
        if (node == null) {//không có giá trị gì hết
            return;
        }
        inOrder(node.getLeftChild());
        //cây có thể có trùng lắp giá trị
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");//in mấy cái giống nhau
        }
        inOrder(node.getRightChild());
    }

    public void postOrder() {
        result = "";
        postOrder(root);
    }

    public void postOrder(BSTNode node) {
        if (node == null) {//không có giá trị gì hết
            return;
        }
        postOrder(node.getLeftChild());
        //cây có thể có trùng lắp giá trị
        postOrder(node.getRightChild());
        for (int i = 0; i < node.getCount(); i++) {
            result += node.getData() + ",";
            System.out.print(node.getData() + ",");//in mấy cái giống nhau
        }
    }

    //lấy path của cây
    public ArrayList<BSTNode> getPath() {
        return this.path;
    }

    public BSTNode findNode(int data) {
        BSTNode node = this.root;//khi tìm kiếm thì luôn bắt đầu từ node gốc
        result = "";

        path.clear();//mỗi lần tìm kiếm thì xóa path đã tìm trc đó

        while (node != null) {
            System.out.print(node.getData() + "->");
            result += node.getData() + "->";
            path.add(node);//thêm node vô list cho mỗi lần thực hiện
            if (data == node.getData()) {//nếu đã tìm thấy thì return
                return node;
                //ngc lại kiểm tra tiếp
            } else if (data < node.getData()) {
                node = node.getLeftChild();//nếu số cần tìm nhỏ hơn vị trí hiện tại thì di chuyển sang trái
            } else {
                node = node.getRightChild();//nếu số cần tìm nhỏ hơn vị trí hiện tại thì di chuyển sang phải
            }
        }

        path.clear();//nếu tìm k thấy thì k có return trong while, nên xóa sạch các node đã đánh dấu trc đó
        //nếu tìm hoài k thấy thì return về null
        return null;
    }

    public boolean removeNode(int data) {
        BSTNode node = findNode(data);//gọi hàm findNode tìm xem giá trị cần remove có nằm trên cây không
        return removeNode(node);
    }

    private boolean removeNode(BSTNode node) {
        if (node == null) {
            return false;
        }
        node.setCount(node.getCount() - 1);//giảm count xuống, nếu trùng thì giảm xuống 1 đơn vị hoặc count =1 giảm xuống count =0, sẽ tiến hành remove node khỏi giao diện
        //tiến hành xóa node khỏi giao diện khi count ==0
        if (node.getCount() == 0) {
            if (node.isLeaf()) {
                node.getParent().removeLeafChild(node);
                return true;//thông báo gỡ thành công
            } else {//node k phải node lá thì phải tìm node thay thế vị trí sau khi gỡ
                //ưu tiên tìm bên tay trái
                BSTNode incomer = null;
                if (node.hasLeftChild()) {
                    incomer = node.getLeftChild().findMaxNode();
                } else {//nếu k có con trái thì sang con bên phải
                    incomer = node.getRightChild().findMinNode();
                }

                node.setData(incomer.getData());
                node.setCount(incomer.getCount());

                //tiếp tục xóa vị trí cũ của tk lấy lên thế
                incomer.setCount(1);

                return removeNode(incomer);
            }
        } else {
            return true;
        }
    }

    //gọi đệ quy cho đến null thì return về
    public void clear() {
        clear(this.root);//xóa từ node gốc
        this.root = null;
    }

    public void clear(BSTNode node) {
        if (node == null) {//nếu gốc = null thì return về
            return;
        }
        if (node != null) {
            if (node.hasLeftChild()) {//nếu có node trái thì xóa
                clear(node.getLeftChild());
            }
            if (node.hasRightChild()) {//nếu có node phải thì xóa
                clear(node.getRightChild());
            }
            if (!node.isRoot() && node.isLeaf()) {//nếu k phải là gốc mà là lá thì gọi hàm xóa lá
                node.getParent().removeLeafChild(node);
            }
        }
    }

    //cài theo danh sách liên kết
    public void BFS() {
        this.result = "";
        Queue<BSTNode> q = new LinkedList<>();
        q.add(root);//thêm node đầu tiên là node gốc

        BSTNode node;
        while (!q.isEmpty()) {//khi còn giá trị trong queue
            //tiến hành lấy giá trị ra cho đến khi hàng đợi rỗng
            node = q.poll();
            if (node != null) {//thực hiện cho đến khi nào khác null
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.println(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                //tiếp tục xửa lý con của nó
                q.add(node.getLeftChild());
                q.add(node.getRightChild());
            }
        }
    }

    public void DFS() {
        this.result = "";
        Stack<BSTNode> s = new Stack<>();
        s.add(root);//thêm node đầu tiên là node gốc

        BSTNode node;
        while (!s.isEmpty()) {//khi còn giá trị trong stack
            //tiến hành lấy giá trị ra cho đến khi hàng ngăn xếp
            node = s.pop();
            if (node != null) {//thực hiện cho đến khi nào khác null
                for (int i = 0; i < node.getCount(); i++) {
                    System.out.println(node.getData() + ", ");
                    result += node.getData() + ", ";
                }
                //tiếp tục xửa lý con của nó
                //ngăn xếp thì vào sau ra trước 
                //để xử lý con trái trước thì thêm con phải trước
                s.add(node.getRightChild());
                s.add(node.getLeftChild());
            }
        }
    }

    //chuyển cây tìm kiếm nhị phân thành mảng
    public void BST2Array(BSTNode node) {
        if (node == null) {//không có giá trị gì hết
            return;
        }
        BST2Array(node.getLeftChild());
        treeData.add(new NodeData(node.getData(), node.getCount()));

        BST2Array(node.getRightChild());
    }

    public void balancing() {
        treeData.clear();//xóa bỏ hết dữ liệu trong cây

        BST2Array(this.root);//sao chép dữ liệu từ cây sang treeData được sắp xếp tăng dần

        this.clear();//xóa bỏ toàn bộ cây để làm cây mới, dữ cũ liệu đã được lưu vào mảng

        Queue<BSTRange> q = new LinkedList<>();
        q.add(new BSTRange(0, treeData.size() - 1));//lưu 2 biên index bên trái và index bên phải

        BSTRange range;
        NodeData nodeData;
        int middleIndex, leftIndex, rightIndex;//giữa của mảng
        while (!q.isEmpty()) {//khi hàng đợi còn dữ liệu thì lấy ra            
            range = q.poll();//lấy giá trị ra
            leftIndex = range.getLeftIndex();
            rightIndex = range.getRightIndex();
            if (leftIndex <= rightIndex) {
                middleIndex = (leftIndex + rightIndex) / 2;
                nodeData = treeData.get(middleIndex);
                //thêm node giữ
                this.addNode(nodeData.getData(), nodeData.getCount());
                //xử ý node bên trái và phải của node đó
                q.add(new BSTRange(leftIndex, middleIndex - 1));
                q.add(new BSTRange(middleIndex + 1, rightIndex));
            }
        }
    }
}
