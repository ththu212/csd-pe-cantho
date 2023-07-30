
package graph_b1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Integer.MAX_VALUE;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * GRAPH 1.DFS 2.BFS
 */
public class Gdrawing extends JPanel {

    /**
     * Cho so luong dinh toi da (o day la 50)
     */
    public static final int MAX_VERTEX = 50;
    public static final String SEPARATOR = " ";

    private JTextArea txtGraphInfo = null;
    private int graphType = 0;

    private int NumberOfVertices = 0;
    private int[][] graph;
    private ArrayList<GVertex> vertices;
    private ArrayList<GEdge> edges;
    private int startIndex = -1, stopIndex = -1;
    private int edgeValue = 1;

    private Graphics2D g = null;
    private int mouseX, mouseY, selectedVertexIndex = -1, selectedEdgeIndex = -1;
    private boolean isShift = false, isCtrl = false, isRightClicked = false, isClear = false;

    private String result = "";
    private boolean[] isVisited;
    private Queue<Integer> q;
    private Stack<Integer> s;

    /**
     * tao constructor
     */
    public Gdrawing() {

        distance = new int[MAX_VERTEX];
        theVertexBefore = new int[MAX_VERTEX];

        isVisited = new boolean[MAX_VERTEX];
        q = new LinkedList<>();
        s = new Stack<>();

        TraversalReset();

        this.graph = new int[MAX_VERTEX][MAX_VERTEX];
        for (int i = 0; i < MAX_VERTEX; i++) {
            for (int j = 0; j < MAX_VERTEX; j++) {
                this.graph[i][j] = 0;
            }
        }
        this.NumberOfVertices = 0;
        this.vertices = new ArrayList<>();
        this.edges = new ArrayList<>();

        this.mouseX = 0;
        this.mouseY = 0;
        this.selectedVertexIndex = -1;
        this.selectedEdgeIndex = -1;
        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                super.mouseDragged(me);
                mouseX = me.getX();
                mouseY = me.getY();
                moveVertex_dragged();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
                super.mouseClicked(me);
                mouseX = me.getX();
                mouseY = me.getY();

                isCtrl = me.isControlDown();
                isShift = me.isShiftDown();
                isRightClicked = me.getModifiers() == MouseEvent.BUTTON3_MASK;
                isClear = me.getModifiers() == MouseEvent.BUTTON2_MASK;
                checkMouseClicked();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                super.mousePressed(me);
                mouseX = me.getX();
                mouseY = me.getY();
                moveVertex_start();
            }
        });
    }

    private void TraversalReset() {
        result = "";
        for (int i = 0; i < MAX_VERTEX; i++) {
            isVisited[i] = false;
        }
        q.clear();
        s.clear();
    }

    /**
     * Them mot dinh vao ma tran
     */
    public void addVertex() {
        vertices.add(new GVertex(this.NumberOfVertices++, mouseX, mouseY));
        for (int i = 0; i < this.NumberOfVertices; i++) {
            graph[i][this.NumberOfVertices - 1] = 0;
            graph[this.NumberOfVertices - 1][i] = 0;
        }
        updateGraphInfo();
    }

    /**
     * Them mot canh vao ma tran
     */
    public void addEdge() {
        selectedEdgeIndex = findEdgeByVertex(startIndex, stopIndex);
        if (selectedEdgeIndex == -1) {
            try {
                this.edgeValue = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter the value of this edge", "1"));
                this.edges.add(new GEdge(edgeValue, this.vertices.get(startIndex), this.vertices.get(stopIndex)));
                graph[startIndex][stopIndex] = edgeValue;
                graph[stopIndex][startIndex] = edgeValue;
            } catch (Exception e) {
                vertices.get(startIndex).setSelected(false);
                vertices.get(stopIndex).setSelected(false);
            }
            startIndex = stopIndex = -1;
            updateGraphInfo();
        }
    }

    //Ham cho them de sua loi duong cheo luon bang 0
    private void updateGraph() {
        for (int i = 0; i < this.NumberOfVertices; i++) {
            for (int j = 0; j < this.NumberOfVertices; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                }
                if (graph[i][j] != graph[j][i]) {
                    graph[i][j] = 0;
                    graph[j][i] = 0;
                }
            }
        }
    }

    public void setGraphType(int type) {
        this.graphType = type;
        updateGraphInfo();
    }

    //update lai ma tran o trong txtArea
    private void updateGraphInfo() {
        updateGraph();
        String giStr = "";
        if (NumberOfVertices != 0) {
            if (this.graphType == 0) {
                giStr += this.NumberOfVertices + "";
                for (int i = 0; i < this.NumberOfVertices; i++) {
                    giStr += "\n" + graph[i][0];
                    for (int j = 1; j < this.NumberOfVertices; j++) {
                        giStr += SEPARATOR + graph[i][j];
                    }
                }
            } else {
                int countEdge = 0;
                for (int i = 0; i < this.NumberOfVertices - 1; i++) {
                    for (int j = i + 1; j < this.NumberOfVertices; j++) {
                        if (graph[i][j] > 0) {
                            giStr += "\n" + vertices.get(i).getLabel() + " " + vertices.get(j).getLabel() + " " + graph[i][j];
                            ++countEdge;
                        }
                    }

                }
                giStr = this.NumberOfVertices + " " + countEdge + giStr;
            }
        }
        this.txtGraphInfo.setText(giStr);
    }

    /**
     * tim dinh theo vi tri con tro chuot
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    public int findVertexByLocation(int mouseX, int mouseY) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (vertices.get(i).isInside(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * tim dinh theo gia tri
     *
     * @param v
     * @return
     */
    public int findVertexByValue(int v) {
        for (int i = 0; i < this.vertices.size(); i++) {
            if (vertices.get(i).getValue() == v) {
                return i;
            }
        }
        return -1;
    }

    /**
     * tim canh theo vi tri con tro chuot
     *
     * @param mouseX
     * @param mouseY
     * @return
     */
    public int findEdgeByLocation(int mouseX, int mouseY) {
        for (int i = 0; i < edges.size(); i++) {
            if (this.edges.get(i).isInside(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * tim canh theo dinh (dung de xoa dinh thi cac canh cung se bi xoa theo)
     *
     * @param from
     * @param to
     * @return
     */
    public int findEdgeByVertex(int from, int to) {
        GEdge edge;
        for (int i = 0; i < edges.size(); i++) {
            edge = edges.get(i);
            if ((edge.getStart().getValue() == from && edge.getEnd().getValue() == to)
                    || (edge.getStart().getValue() == to && edge.getEnd().getValue() == from)) {
                return i;
            }
        }
        return -1;
    }

    private void moveVertex_start() {
        selectedVertexIndex = findVertexByLocation(mouseX, mouseY);
    }

    private void moveVertex_dragged() {
        if (selectedVertexIndex > -1) {
            this.vertices.get(selectedVertexIndex).setX(mouseX);
            this.vertices.get(selectedVertexIndex).setY(mouseY);
            repaint();
        }
    }

    /**
     * Chon mot diem
     */
    public void selectVertex() {
        selectedVertexIndex = findVertexByLocation(mouseX, mouseY);
        if (selectedVertexIndex > -1) {
            if (startIndex == -1) {
                startIndex = selectedVertexIndex;
                this.vertices.get(startIndex).setSelected(true);
            } else if (startIndex == selectedVertexIndex) {
                this.vertices.get(startIndex).setSelected(false);
                startIndex = -1;
            } else {
                this.vertices.get(startIndex).setSelected(false);
                stopIndex = selectedVertexIndex;
                addEdge();
            }
        }
    }

    public void resetSelected() {
        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).setSelected(false);
        }
    }

    private void updateEdge() {
        resetSelected();
        GEdge edge = this.edges.get(selectedEdgeIndex);
        this.startIndex = edge.getStart().getValue();
        this.stopIndex = edge.getEnd().getValue();

        try {
            this.edgeValue = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter new value for this edge", edge.getValue() + ""));
            edge.setValue(this.edgeValue);
            graph[startIndex][stopIndex] = edgeValue;
            graph[stopIndex][startIndex] = edgeValue;
            startIndex = stopIndex = -1;
            updateGraphInfo();
        } catch (NumberFormatException e) {
            //System.err.println(e);
            JOptionPane.showMessageDialog(this, "Input number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * chon mot canh
     */
    public void selectEdge() {
        selectedEdgeIndex = findEdgeByLocation(mouseX, mouseY);
        if (selectedEdgeIndex > -1) {
            this.edges.get(selectedEdgeIndex).setSelected(true);
            repaint();

            updateEdge();
            this.edges.get(selectedEdgeIndex).setSelected(false);
            selectedEdgeIndex = -1;
        }
    }

    /**
     * xoa cac canh o tai vi tri con tro chuot
     */
    public void removeEdge() {
        int edgeIndex = findEdgeByLocation(mouseX, mouseY);
        if (edgeIndex > -1) {
            GEdge edge = this.edges.get(edgeIndex);
            String edgeLabel = edge.getStart().getLabel() + "-" + edge.getEnd().getLabel();

            edge.setSelected(true);

            repaint();
            if (JOptionPane.showConfirmDialog(this, "Do you really want to delete the edge '" + edgeLabel + "'?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.removeEdge(edgeIndex);
            } else {
                edge.setSelected(false);
            }

        }
    }

    /**
     * sau khi tim ra canh thi chung ta xoa
     *
     * @param index
     */
    public void removeEdge(int index) {
        GEdge edge = this.edges.get(index);
        int from = edge.getStart().getValue();
        int to = edge.getEnd().getValue();

        graph[from][to] = 0;
        graph[to][from] = 0;
        this.edges.remove(index);
        updateGraphInfo();
    }

    /**
     * xoa dinh dang o vi tri con tro chuot
     */
    public void removeVertex() {
        int vertexIndex = findVertexByLocation(mouseX, mouseY);
        if (vertexIndex > -1) {
            String vertexLabel = this.vertices.get(vertexIndex).getLabel();

            vertices.get(vertexIndex).setSelected(true);
            repaint();
            if (JOptionPane.showConfirmDialog(this,
                    "Do you really want to delete the vertex '" + vertexLabel + "'?",
                    "Warning", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                this.removeVertex(vertexIndex);
            } else {
                vertices.get(vertexIndex).setSelected(false);
            }
        }
    }

    /**
     * xoa cac dinh da duoc tim that
     *
     * @param vertexIndex
     */
    public void removeVertex(int vertexIndex) {
        for (int from = vertexIndex; from < this.NumberOfVertices - 1; from++) {
            for (int to = 0; to < this.NumberOfVertices; to++) {
                graph[from][to] = graph[from + 1][to];
                graph[to][from] = graph[to][from + 1];
            }
        }
        this.NumberOfVertices--;

        GEdge edge;
        for (int i = this.edges.size() - 1; i >= 0; i--) {
            edge = this.edges.get(i);
            if (edge.getStart().getValue() == vertexIndex || edge.getEnd().getValue() == vertexIndex) {
                this.edges.remove(i);
            }
        }
        this.vertices.remove(vertexIndex);

        for (int i = vertexIndex; i < this.NumberOfVertices; i++) {
            this.vertices.get(i).setValue(this.vertices.get(i).getValue() - 1);
        }
        updateGraphInfo();
    }

    //Kiem tra luc con tro chuot click co xai phim khac hong
    public void checkMouseClicked() {
        if (isCtrl) {
            addVertex();
        } else if (isShift) {
            removeVertex();
            removeEdge();
        } else if (isClear) {
            toDefault();
        } else if (isRightClicked) {
            nextPath();
        } else {
            selectVertex();
            selectEdge();
        }
        repaint();
    }

    public void setTxtGraphInfo(JTextArea txtGraphInfo) {
        this.txtGraphInfo = txtGraphInfo;
    }

    public void clear() {
        for (int i = 0; i < NumberOfVertices; i++) {
            for (int j = 0; j < NumberOfVertices; j++) {
                graph[i][j] = 0;
            }
        }
        this.vertices.clear();
        this.edges.clear();
        NumberOfVertices = 0;
        updateGraphInfo();
        repaint();
    }

    public int getNumberOfVertices() {
        return NumberOfVertices;
    }

    public int[][] getGraph() {
        return graph;
    }

    public ArrayList<GVertex> getVertices() {
        return vertices;
    }

    //Doc file ma tran
    void readMatrixDataFile(File openFile) {
        try (Scanner sc = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = sc.nextInt();
            int x, y;
            for (int i = 0; i < this.NumberOfVertices; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                this.vertices.add(new GVertex(i, x, y));
            }
            for (int i = 0; i < NumberOfVertices; i++) {
                for (int j = 0; j < NumberOfVertices; j++) {
                    this.graph[i][j] = sc.nextInt();
                    if (i < j && this.graph[i][j] > 0) {
                        this.edges.add(new GEdge(this.graph[i][j], this.vertices.get(i), this.vertices.get(j)));
                    }
                }
            }
            updateGraphInfo();
            repaint();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    //Doc file duoi dang list
    void readListDataFile(File openFile) {
        try (Scanner sc = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = sc.nextInt();
            int x, y, NumberOfEdges;
            NumberOfEdges = sc.nextInt();
            for (int i = 0; i < this.NumberOfVertices; i++) {
                x = sc.nextInt();
                y = sc.nextInt();
                this.vertices.add(new GVertex(i, x, y));
            }
            int from, to, value;
            for (int i = 0; i < NumberOfEdges; i++) {
                from = sc.nextInt();
                to = sc.nextInt();
                value = sc.nextInt();
                graph[from][to] = value;
                graph[to][from] = value;
                this.edges.add(new GEdge(value, this.vertices.get(from), this.vertices.get(to)));
            }
            updateGraphInfo();
            repaint();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    //Duyet DFS
    public void DFS() {
        TraversalReset();
        try {
            int startVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the start of vertex", "0"));
            int from;
            s.push(startVertex);
            result = "The DFS traversal from the vertex " + startVertex + " is: ";
            while (!s.isEmpty()) {
                from = s.pop();
                //Khac BFS o cho nay!
                if (isVisited[from] == false) {
                    result += from + ", ";
                    isVisited[from] = true;
                    for (int i = NumberOfVertices - 1; i >= 0; i--) {
                        if (isVisited[i] == false && graph[from][i] != 0) {
                            s.push(i);
                        }
                    }
                }
            }
            repaint();
        } catch (NumberFormatException e) {

        }

    }

    //Duyet BFS
    public void BFS() {
        TraversalReset();
        try {
            int startVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the start of vertex", "0"));
            isVisited[startVertex] = true;
            int from;
            q.add(startVertex);
            result = "The BFS traversal from the vertex " + startVertex + " is: ";
            while (!q.isEmpty()) {
                from = q.poll();
                result += from + ", ";
                for (int i = 0; i < NumberOfVertices; i++) {
                    if (isVisited[i] == false && graph[from][i] != 0) {
                        isVisited[i] = true;
                        q.add(i);
                    }
                }
            }
            repaint();
        } catch (NumberFormatException e) {
        }
    }

    public String getResult() {
        int tmp = result.length();
        return result.substring(0, tmp - 2) + ".";
    }

    int[] distance;
    int[] theVertexBefore;  //BeforeVertex -> currentVertex
    public static final int Infinity = MAX_VALUE;
    boolean isGraphConnected;
    boolean isDrawPrimPath = false;
    boolean isFindAllShortest = false;
    String dijkstraPath = "", dijkstraMessage = "";

    ArrayList<Integer> theAllVertexBefore[];
    ArrayList<String> dijkstraListPath;
    int pathIndex = 0;

    private void nextPath() {
        pathIndex = (pathIndex + 1) % dijkstraListPath.size();
        repaint();
    }

    //Reset lai bien de tim Prim
    public void PrimReset() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            theVertexBefore[i] = i;
            isVisited[i] = false;
        }
        isDrawPrimPath = true;
    }

    public void DijkstraReset() {
        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            theVertexBefore[i] = i;
            isVisited[i] = false;
        }
        isDrawPrimPath = true;
        dijkstraPath = "";
        dijkstraMessage = "";
    }

    public void DijkstraAllReset() {
        theAllVertexBefore = new ArrayList[MAX_VERTEX];

        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            isVisited[i] = false;
            theAllVertexBefore[i] = new ArrayList<>();
            theAllVertexBefore[i].add(i);

        }

        dijkstraListPath = new ArrayList<>();
        isDrawPrimPath = true;
        dijkstraMessage = "";
        pathIndex = 0;
    }

    public void Prim() {
        PrimReset();
        distance[0] = 0;
        int vertex;
        isGraphConnected = true;
        for (int i = 0; i < NumberOfVertices; i++) {
            vertex = findNearestVertex();
            if (vertex == -1) {   //Do thi khong lien thong -> khong tim thay cay khung lon nhat
                isGraphConnected = false;
                return;
            } else {
                isVisited[vertex] = true;
                for (int j = 0; j < NumberOfVertices; j++) {
                    if (isVisited[j] == false && graph[vertex][j] != 0 && graph[vertex][j] < distance[j]) {
                        distance[j] = graph[vertex][j];
                        theVertexBefore[j] = vertex;
                    }
                }
            }
        }
        repaint();
    }

    private int findNearestVertex() {
        int minIndex = -1, minValue = Infinity;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isVisited[i] == false && distance[i] < minValue) {
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void displayPath(String path, int currentVertex, int startVertex, int endVertex) {
        if (currentVertex != endVertex) {
            path = currentVertex + " -> " + path;
        }
        if (currentVertex == startVertex) {
            dijkstraListPath.add(path);
        } else {
            for (int i = 0; i < theAllVertexBefore[currentVertex].size(); i++) {
                displayPath(path, theAllVertexBefore[currentVertex].get(i), startVertex, endVertex);
            }
        }
    }

    //tim kiem 1 duong di ngan nhat
    public void Dijkstra() {
        try {
            int startVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the start of vertex", "0"));
            if (CheckInput(startVertex)) {
                JOptionPane.showMessageDialog(this, "Error input startVertex");
                return;
            }
            int endVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the end of vertex", (NumberOfVertices - 1) + ""));
            if (CheckInput(endVertex)) {
                JOptionPane.showMessageDialog(this, "Error input endVertex");
                return;
            }
            DijkstraReset();
            distance[startVertex] = 0;
            int currentVertex;
            isGraphConnected = true;
            for (int i = 0; i < NumberOfVertices; i++) {
                currentVertex = findNearestVertex();

                if (currentVertex == -1) {
                    isGraphConnected = false;
                    break;
                } else {
                    isVisited[currentVertex] = true;
                    for (int to = 0; to < NumberOfVertices; to++) {
                        if (isVisited[to] == false && graph[currentVertex][to] != 0
                                && (distance[currentVertex] + graph[currentVertex][to] < distance[to])) {
                            distance[to] = distance[currentVertex] + graph[currentVertex][to];
                            theVertexBefore[to] = currentVertex;
                        }
                    }
                }
            }
            if (isGraphConnected) {
                dijkstraPath = "" + endVertex;

                currentVertex = endVertex;
                while (currentVertex != startVertex) {
                    currentVertex = theVertexBefore[currentVertex];
                    dijkstraPath = currentVertex + " -> " + dijkstraPath;
                }
                dijkstraMessage = "The length of the shortest path from " + startVertex + " to " + endVertex + " is " + distance[endVertex] + ": " + dijkstraPath;

            } else {
                dijkstraMessage = "Can't find part from" + startVertex + " to " + endVertex + "!";
            }
            isDrawPrimPath = false;
            repaint();
        } catch (NumberFormatException e) {

        }
    }

    //Tim kiem tat ca cac duong di ngan nhat
    public void DijkstraAllShortest() {
        try {
            int startVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the start of vertex", "0"));
            if (CheckInput(startVertex)) {
                JOptionPane.showMessageDialog(this, "Error input startVertex");
                return;
            }
            int endVertex = Integer.parseInt(JOptionPane.
                    showInputDialog(this, "Please enter the end of vertex", (NumberOfVertices - 1) + ""));
            if (CheckInput(endVertex)) {
                JOptionPane.showMessageDialog(this, "Error input endVertex");
                return;
            }
            DijkstraAllReset();
            isFindAllShortest = true;
            distance[startVertex] = 0;
            int currentVertex;
            isGraphConnected = true;
            for (int i = 0; i < NumberOfVertices; i++) {
                currentVertex = findNearestVertex();

                if (currentVertex == -1) {
                    isGraphConnected = false;
                    break;
                } else {
                    isVisited[currentVertex] = true;
                    for (int to = 0; to < NumberOfVertices; to++) {
                        if ((isVisited[to] == false || to == endVertex)
                                && graph[currentVertex][to] != 0
                                && (distance[currentVertex] + graph[currentVertex][to] <= distance[to])) {
                            if (distance[currentVertex] + graph[currentVertex][to] < distance[to]) {
                                theAllVertexBefore[to].clear();
                            }
                            distance[to] = distance[currentVertex] + graph[currentVertex][to];
                            theAllVertexBefore[to].add(currentVertex);
                        }
                    }
                }
            }
            if (isGraphConnected) {
                dijkstraListPath.clear();
                String path = "" + endVertex;
                currentVertex = endVertex;
                displayPath(path, currentVertex, startVertex, endVertex);

                dijkstraMessage = "The length of the shortest path from " + startVertex + " to " + endVertex + " is " + distance[endVertex] + ": ";

            } else {
                dijkstraMessage = "Can't find part from" + startVertex + " to " + endVertex + "!";
            }
            isDrawPrimPath = false;
            repaint();
        } catch (NumberFormatException e) {

        }
    }

    //Chinh tat ca ve nhu cu
    public void toDefault() {
        dijkstraMessage = "";
        isFindAllShortest = false;
    }

    public boolean CheckInput(int x) {
        if (0 <= x && x < NumberOfVertices) {
            return false;
        }
        return true;
    }

    @Override
    public void paint(Graphics g1) {
        super.paint(g1);
        this.g = (Graphics2D) g1;

        this.g.setColor(Color.white);
        this.g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).draw(g);
        }

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).draw(g);
        }

        if (!"".equals(result)) {
            g.setColor(Color.red);
            g.drawString(getResult(), 10, 20);
            result = "";
        }

        if (!dijkstraMessage.equals("") && isFindAllShortest == false) {
            if (!dijkstraPath.equals("")) {
                String[] v = dijkstraPath.split(" -> ");

                int from, to, edgeIndex, vertexIndex;
                for (int i = 1; i < v.length; i++) {
                    from = Integer.parseInt(v[i - 1]);
                    to = Integer.parseInt(v[i]);
                    edgeIndex = findEdgeByVertex(from, to);
                    this.edges.get(edgeIndex).setSelected(true);
                    this.edges.get(edgeIndex).draw(g);
                }

                for (int i = 0; i < v.length; i++) {
                    vertexIndex = Integer.parseInt(v[i]);
                    this.vertices.get(vertexIndex).setSelected(true);
                    this.vertices.get(vertexIndex).draw(g);
                }

                for (int i = 1; i < v.length; i++) {
                    from = Integer.parseInt(v[i - 1]);
                    to = Integer.parseInt(v[i]);
                    edgeIndex = findEdgeByVertex(from, to);
                    this.edges.get(edgeIndex).setSelected(false);
                }

                for (int i = 0; i < v.length; i++) {
                    vertexIndex = Integer.parseInt(v[i]);
                    this.vertices.get(vertexIndex).setSelected(false);
                }
            }
            g.setColor(Color.red);
            g.drawString(dijkstraMessage, 10, 20);

            dijkstraMessage = "";
            dijkstraPath = "";
        }

        if (!dijkstraMessage.equals("") && isFindAllShortest == true) {
            g.setColor(Color.red);
            g.drawString(dijkstraMessage, 10, 20);

            if (dijkstraListPath.size() > 0) {
                for (int i = 0; i < dijkstraListPath.size(); i++) {
                    g.drawString("#" + (i + 1) + ". " + dijkstraListPath.get(i), 10, 40 + i * 20);
                }
                String[] v = dijkstraListPath.get(pathIndex).split(" -> ");

                int from, to, edgeIndex, vertexIndex;
                for (int i = 1; i < v.length; i++) {
                    from = Integer.parseInt(v[i - 1]);
                    to = Integer.parseInt(v[i]);
                    edgeIndex = findEdgeByVertex(from, to);
                    this.edges.get(edgeIndex).setSelected(true);
                    this.edges.get(edgeIndex).draw(g);
                    this.edges.get(edgeIndex).setSelected(false);
                }

                for (int i = 0; i < v.length; i++) {
                    vertexIndex = Integer.parseInt(v[i]);
                    this.vertices.get(vertexIndex).setSelected(true);
                    this.vertices.get(vertexIndex).draw(g);
                    this.vertices.get(vertexIndex).setSelected(false);
                }

                /*for (int i = 1; i < v.length; i++) {
                    from = Integer.parseInt(v[i - 1]);
                    to = Integer.parseInt(v[i]);
                    edgeIndex = findEdgeByVertex(from, to);
                    this.edges.get(edgeIndex).setSelected(false);
                }

                for (int i = 0; i < v.length; i++) {
                    vertexIndex = Integer.parseInt(v[i]);
                    this.vertices.get(vertexIndex).setSelected(false);
                }*/
            }

            //dijkstraMessage = "";
        }

        if (isDrawPrimPath == true) {
            String str = "";
            if (isGraphConnected) {
                int sum = 0;
                for (int i = 0; i < NumberOfVertices; i++) {
                    sum += distance[i];
                }
                str = "The value of minium spanning tree is: " + sum;
                int from, to;
                int edgeIndex;
                for (int i = 0; i < NumberOfVertices; i++) {
                    to = i;
                    from = theVertexBefore[i];
                    if (from != to) {
                        edgeIndex = findEdgeByVertex(from, to);
                        if (edgeIndex != -1) {
                            edges.get(edgeIndex).setSelected(true);
                            edges.get(edgeIndex).draw(g);
                        }
                    }
                }
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).setSelected(true);
                    vertices.get(i).draw(g);
                }

                for (int i = 0; i < NumberOfVertices; i++) {
                    to = i;
                    from = theVertexBefore[i];
                    if (from != to) {
                        edgeIndex = findEdgeByVertex(from, to);
                        if (edgeIndex != -1) {
                            edges.get(edgeIndex).setSelected(false);
                        }
                    }
                }
                for (int i = 0; i < vertices.size(); i++) {
                    vertices.get(i).setSelected(false);
                }
            } else {
                str = "The graph is not connected";
                //JOptionPane.showMessageDialog(this, "The graph is null", "Error", JOptionPane.OK_OPTION);
            }
            g.setColor(Color.red);
            g.drawString(str, 10, 20);
            g.dispose();
            isDrawPrimPath = false;
        }
    }

    public void setNumVertex(int n) {
        NumberOfVertices = n;
    }

    public void addVertex(int from, int to) {
        //addVertex();
        graph[from][to] = 1;
        graph[to][from] = 1;

    }

    public void BFS(int startVertex) {
        TraversalReset();
        isVisited[startVertex] = true;
        int from;
        q.add(startVertex);
        while (!q.isEmpty()) {
            from = q.poll();
            result += from + ",";
            for (int i = 0; i < NumberOfVertices; i++) {
                if (isVisited[i] == false && graph[from][i] != 0) {
                    isVisited[i] = true;
                    q.add(i);
                }
            }
        }
    }

    public void DFS(int startVertex) {
        TraversalReset();

        int from;
        s.push(startVertex);
        while (!s.isEmpty()) {
            from = s.pop();
            //Khac BFS o cho nay!
            if (isVisited[from] == false) {
                result += from + ",";
                isVisited[from] = true;
                for (int i = NumberOfVertices - 1; i >= 0; i--) {
                    if (isVisited[i] == false && graph[from][i] != 0) {
                        s.push(i);
                    }
                }
            }
        }
    }

    public String printTravel() {
        return result;
    }
}
