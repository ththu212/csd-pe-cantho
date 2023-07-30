
package graph_b2;

import com.sun.javafx.geom.Edge;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;
import javax.accessibility.AccessibleContext;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.event.EventListenerList;
import javax.swing.plaf.ComponentUI;

/**
 *
 * GRAPH 1.PRIME 2.KRUSAL
 *
 */
public class GPaper extends JPanel {

    //Const, attributes
    public static final int MAX_VERTEX = 50;
    public static final String SEPARATOR = " ";

    private JTextArea txtGraphInfo = null;
    private int graphType = 0;

    private int NumberOfVertices = 0;
    private int[][] graph;
    private ArrayList<GVertex> vertices;
    private ArrayList<GEdge> edges;
    private int StartIndex = -1, stopIndex = -1;
    private int edgeValue = 1;

    private Graphics2D g = null;
    private int mouseX, mouseY, selectedVertexIndex = -1, selectEdgeIndex = -1;
    private boolean isShift = false, isCtrl = false, isRightClick = false;

    private String result = "";
    boolean[] isVisited;
    Queue<Integer> q;
    Stack<Integer> s;

    //Constructors
    public GPaper() {
        distance = new int[MAX_VERTEX];
        theVertexBefore = new int[MAX_VERTEX];

        q = new LinkedList<>();
        s = new Stack<>();
        isVisited = new boolean[MAX_VERTEX];

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
        this.selectEdgeIndex = -1;
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
                isRightClick = me.getModifiers() == MouseEvent.BUTTON3_MASK;

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

    public ArrayList<GEdge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<GEdge> edges) {
        this.edges = edges;
    }

    public int getStartIndex() {
        return StartIndex;
    }

    public void setStartIndex(int StartIndex) {
        this.StartIndex = StartIndex;
    }

    public int getStopIndex() {
        return stopIndex;
    }

    public void setStopIndex(int stopIndex) {
        this.stopIndex = stopIndex;
    }

    public int getEdgeValue() {
        return edgeValue;
    }

    public void setEdgeValue(int edgeValue) {
        this.edgeValue = edgeValue;
    }

    public Graphics2D getG() {
        return g;
    }

    public void setG(Graphics2D g) {
        this.g = g;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    public int getSelectedVertexIndex() {
        return selectedVertexIndex;
    }

    public void setSelectedVertexIndex(int selectedVertexIndex) {
        this.selectedVertexIndex = selectedVertexIndex;
    }

    public int getSelectEdgeIndex() {
        return selectEdgeIndex;
    }

    public void setSelectEdgeIndex(int selectEdgeIndex) {
        this.selectEdgeIndex = selectEdgeIndex;
    }

    public boolean isIsShift() {
        return isShift;
    }

    public void setIsShift(boolean isShift) {
        this.isShift = isShift;
    }

    public boolean isIsCtrl() {
        return isCtrl;
    }

    public void setIsCtrl(boolean isCtrl) {
        this.isCtrl = isCtrl;
    }

    public boolean isIsRightClick() {
        return isRightClick;
    }

    public void setIsRightClick(boolean isRightClick) {
        this.isRightClick = isRightClick;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public boolean[] getIsVisited() {
        return isVisited;
    }

    public void setIsVisited(boolean[] isVisited) {
        this.isVisited = isVisited;
    }

    public Queue<Integer> getQ() {
        return q;
    }

    public void setQ(Queue<Integer> q) {
        this.q = q;
    }

    public Stack<Integer> getS() {
        return s;
    }

    public void setS(Stack<Integer> s) {
        this.s = s;
    }

    public int[] getDistance() {
        return distance;
    }

    public void setDistance(int[] distance) {
        this.distance = distance;
    }

    public int[] getTheVertexBefore() {
        return theVertexBefore;
    }

    public void setTheVertexBefore(int[] theVertexBefore) {
        this.theVertexBefore = theVertexBefore;
    }

    public boolean isIsGraphConnected() {
        return isGraphConnected;
    }

    public void setIsGraphConnected(boolean isGraphConnected) {
        this.isGraphConnected = isGraphConnected;
    }

    public boolean isIsDrawPrimPath() {
        return isDrawPrimPath;
    }

    public void setIsDrawPrimPath(boolean isDrawPrimPath) {
        this.isDrawPrimPath = isDrawPrimPath;
    }

    public ArrayList<Integer>[] getDijkstra_theVertexBefore() {
        return dijkstra_theVertexBefore;
    }

    public void setDijkstra_theVertexBefore(ArrayList<Integer>[] dijkstra_theVertexBefore) {
        this.dijkstra_theVertexBefore = dijkstra_theVertexBefore;
    }

    public ArrayList<String> getDijkstraPathString() {
        return dijkstraPathString;
    }

    public void setDijkstraPathString(ArrayList<String> dijkstraPathString) {
        this.dijkstraPathString = dijkstraPathString;
    }

    public String getDijkstraPath() {
        return dijkstraPath;
    }

    public void setDijkstraPath(String dijkstraPath) {
        this.dijkstraPath = dijkstraPath;
    }

    public String getDijkstraMsg() {
        return dijkstraMsg;
    }

    public void setDijkstraMsg(String dijkstraMsg) {
        this.dijkstraMsg = dijkstraMsg;
    }

    public String getDijkstraAllMsg() {
        return dijkstraAllMsg;
    }

    public void setDijkstraAllMsg(String dijkstraAllMsg) {
        this.dijkstraAllMsg = dijkstraAllMsg;
    }

    public int getPathIndex() {
        return pathIndex;
    }

    public void setPathIndex(int pathIndex) {
        this.pathIndex = pathIndex;
    }

    public ComponentUI getUi() {
        return ui;
    }

    public void setUi(ComponentUI ui) {
        this.ui = ui;
    }

    public EventListenerList getListenerList() {
        return listenerList;
    }

    public void setListenerList(EventListenerList listenerList) {
        this.listenerList = listenerList;
    }

    public AccessibleContext getAccessibleContext() {
        return accessibleContext;
    }

    public void setAccessibleContext(AccessibleContext accessibleContext) {
        this.accessibleContext = accessibleContext;
    }

    //find vertex by location
    public int findVertexByLocation(int mouseX, int mouseY) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).isInside(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    //find vertex by value
    public int findVertexByValue(int v) {
        for (int i = 0; i < vertices.size(); i++) {
            if (vertices.get(i).getValue() == v) {
                return i;
            }
        }
        return -1;
    }

    //find edge by location
    public int findEdgeByLocation(int mouseX, int mouseY) {
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).isInside(mouseX, mouseY)) {
                return i;
            }
        }
        return -1;
    }

    //find edge by start and end coordinates
    public int findEdegeByVertex(int from, int to) {
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

    //remove all selected 
    private void removeSelect() {
        for (GVertex v : vertices) {
            if (v.isSelected()) {
                v.setSelected(false);
            }
        }
    }

    public int getNumberOfVertices() {
        return NumberOfVertices;
    }

    public void setNumberOfVertices(int NumberOfVertices) {
        this.NumberOfVertices = NumberOfVertices;
    }

    public int[][] getGraph() {
        return graph;
    }

    public void setGraph(int[][] graph) {
        this.graph = graph;
    }

    public ArrayList<GVertex> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<GVertex> vertices) {
        this.vertices = vertices;
    }

    //clear graph
    void clear() {
        for (int i = 0; i < NumberOfVertices; i++) {
            for (int j = 0; j < NumberOfVertices; j++) {
                graph[i][j] = 0;
            }
        }
        this.vertices.clear();
        this.edges.clear();
        dijkstraAllMsg = "";
        dijkstraMsg = "";
        NumberOfVertices = 0;
        updateGraphInfo();
        txtGraphInfo.setText(" ");
        repaint();
    }

    //Set graph type for pain matrix or list
    public void setGraphType(int type) {
        this.graphType = type;
        updateGraphInfo();
    }

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

    //display Matrix and List
    private void updateGraphInfo() {
        String giStr = "";
        if (this.graphType == 0) { // Matrix
            updateGraph();
            giStr += this.NumberOfVertices + "";
            for (int i = 0; i < this.NumberOfVertices; i++) {
                giStr += "\n" + graph[i][0];
                for (int j = 1; j < this.NumberOfVertices; j++) {
                    giStr += SEPARATOR + graph[i][j];
                }
            }
        } else {
            int countEdge = 0; // List
            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = i + 1; j < this.NumberOfVertices; j++) {
                    if (graph[i][j] > 0) {
                        giStr += "\n" + vertices.get(i).getLabel() + " " + vertices.get(j).getLabel() + " " + graph[i][j];
                        ++countEdge;
                    }
                }
            }
            giStr = this.NumberOfVertices + " " + countEdge + giStr;
        }
        this.txtGraphInfo.setText(giStr);
    }

    //Add vertex into graph
    public void addVertex() {
        this.vertices.add(new GVertex(this.NumberOfVertices, mouseX, mouseY));
        this.NumberOfVertices++;
        for (int i = 0; i < this.NumberOfVertices; i++) {
            graph[i][this.NumberOfVertices - 1] = 0;
            graph[this.NumberOfVertices - 1][i] = 0;
        }
        updateGraphInfo();
    }

    //Add edge into graph
    public void addEdge() {
        selectEdgeIndex = findEdegeByVertex(StartIndex, stopIndex);
        if (selectEdgeIndex == -1) {
            try {
                this.edgeValue = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter the value for this edge", 1));
                this.edges.add(new GEdge(edgeValue, this.vertices.get(StartIndex), this.vertices.get(stopIndex)));
                graph[StartIndex][stopIndex] = edgeValue;
                graph[stopIndex][StartIndex] = edgeValue;
            } catch (Exception e) {
                vertices.get(StartIndex).setSelected(false);
                vertices.get(stopIndex).setSelected(false);

            }

        }
        StartIndex = stopIndex = -1;
        updateGraphInfo();
    }

    //Select a vertex on jfame
    public void selectVertex() {
        selectedVertexIndex = findVertexByLocation(mouseX, mouseY);
        if (selectedVertexIndex > -1) {
            if (StartIndex == -1) {
                StartIndex = selectedVertexIndex;
                this.vertices.get(StartIndex).setSelected(true);
            } else if (StartIndex == selectedVertexIndex) {
                this.vertices.get(StartIndex).setSelected(false);
                StartIndex = -1;
            } else {
                this.vertices.get(StartIndex).setSelected(false);
                stopIndex = selectedVertexIndex;
                addEdge();
            }
        }
    }

    //change value of Edge
    public void updateEdge() {
        removeSelect();
        GEdge edge = this.edges.get(selectEdgeIndex);
        this.StartIndex = edge.getStart().getValue();
        this.stopIndex = edge.getEnd().getValue();

        try {
            this.edgeValue = Integer.parseInt(JOptionPane.showInputDialog(this, "Please enter new value for this edge", edge.getValue() + ""));
            edge.setValue(this.edgeValue);
            graph[StartIndex][stopIndex] = this.edgeValue;
            graph[stopIndex][StartIndex] = this.edgeValue;
            StartIndex = stopIndex = -1;
            updateGraphInfo();
        } catch (NumberFormatException e) {
            System.err.println(e);
        }

    }

    //Select a Edge on jfame
    public void selectEdge() {
        selectEdgeIndex = findEdgeByLocation(mouseX, mouseY);
        if (selectEdgeIndex > -1) {
            this.edges.get(selectEdgeIndex).setSelected(true);
            repaint();

            updateEdge();
            this.edges.get(selectEdgeIndex).setSelected(false);
            selectEdgeIndex = -1;
        }
    }

    //Remove a Edge
    public void removeEdge() {
        int edgeIndex = findEdgeByLocation(mouseX, mouseY);
        if (edgeIndex > -1) {
            GEdge edge = this.edges.get(edgeIndex);
            String edgeLabel = edge.getStart().getLabel() + "-" + edge.getEnd().getLabel();

            edge.setSelected(true);
            repaint();

            if (JOptionPane.showConfirmDialog(this,
                    "Do you really want to delete the edge '" + edgeLabel + "'?",
                    "Warning",
                    JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                this.removeEdge(edgeIndex);
            } else {
                edge.setSelected(false);
            }
        }
    }

    //remove a vertex
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

    //remove a vertex by value (call back)
    public void removeVertex(int vertexIndex) {
        PrimReset();
        isDrawPrimPath = false;
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

    //remove a edge
    public void removeEdge(int index) {
        PrimReset();
        isDrawPrimPath = false;
        GEdge edge = this.edges.get(index);
        int from = edge.getStart().getValue();
        int to = edge.getEnd().getValue();

        graph[from][to] = 0;
        graph[to][from] = 0;
        this.edges.remove(index);
        updateGraphInfo();
    }

    //Mouse events
    public void checkMouseClicked() {
        if (isCtrl) {
            addVertex();
        } else if (isShift) {
            removeVertex();
            removeEdge();
        } else if (isRightClick) {
            if (dijkstraAllMsg != "") {
                nextPath();
            }
        } else {
            selectVertex();
            selectEdge();
        }
        repaint();
    }

    public void setTxtGraphInfo(JTextArea txtGraphInfo) {
        this.txtGraphInfo = txtGraphInfo;
    }

    @Override
    public void paint(Graphics grphcs) {

        super.paint(grphcs);
        this.g = (Graphics2D) grphcs;

        this.g.setColor(Color.white);
        this.g.fillRect(0, 0, this.getWidth(), this.getHeight());

        for (int i = 0; i < edges.size(); i++) {
            edges.get(i).draw(g);
        }

        for (int i = 0; i < vertices.size(); i++) {
            vertices.get(i).draw(g);
        }

        //paint traversal result
        if (!getTraverselResult().equals("")) {
            g.setColor(Color.red);
            g.drawString(getTraverselResult(), 10, 20);
            result = "";
        }

        if (dijkstraMsg != "") {
            if (dijkstraPath != "") {
                String[] v = dijkstraPath.split(" -> ");

                int fromVetex, toVertex, edgeIndex, vertexIndex;
                for (int i = 1; i < v.length; i++) {
                    fromVetex = Integer.parseInt(v[i - 1]);
                    toVertex = Integer.parseInt(v[i]);
                    edgeIndex = findEdegeByVertex(fromVetex, toVertex);
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
            }

            g.setColor(Color.red);
            g.drawString(dijkstraMsg, 10, 20);

            dijkstraPath = "";
            dijkstraMsg = "";
        }

        if (dijkstraAllMsg != "") {
            g.setColor(Color.red);
            g.drawString(dijkstraAllMsg, 10, 20);
            if (dijkstraPathString.size() > 0) {
                for (int i = 0; i < dijkstraPathString.size(); i++) {
                    g.drawString("#" + (i + 1) + ". " + dijkstraPathString.get(i), 10, 40 + i * 20);
                }
                String[] v = dijkstraPathString.get(pathIndex).split(" -> ");

                int fromVetex, toVertex, edgeIndex, vertexIndex;
                for (int i = 1; i < v.length; i++) {
                    fromVetex = Integer.parseInt(v[i - 1]);
                    toVertex = Integer.parseInt(v[i]);
                    edgeIndex = findEdegeByVertex(fromVetex, toVertex);
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
            }
        }

        //paint minimum spanning tree
        String str = "";

    }

    //get traversal result without end ", "
    public String getTraverselResult() {
        if (!result.equals("")) {
            return result.substring(0, result.length() - 2);
        } else {
            return result;
        }

    }

    //BFS traversal
    public void BFS() {
        TraversalReset();
        int startVertext = Integer.parseInt(
                JOptionPane.showInputDialog(this, "Please enter the start vertex", 0));
        int fromVertex;
        isVisited[startVertext] = true;
        q.add(startVertext);
        result = "The BFS traversal from the vertex " + startVertext + " is: ";
        while (!q.isEmpty()) {
            fromVertex = q.poll();
            result += fromVertex + ", ";
            for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                    q.add(toVertex);
                    isVisited[toVertex] = true;
                }
            }
        }
        repaint();
    }

    //DFS traversal
    public void DFS() {
        TraversalReset();
        int startVertext = Integer.parseInt(
                JOptionPane.showInputDialog(this, "Please enter the start vertex", 0));
        int fromVertex;
        s.push(startVertext);
        result = "The DFS traversal from the vertex " + startVertext + " is: ";
        while (!s.isEmpty()) {
            fromVertex = s.pop();
            if (isVisited[fromVertex] == false) {
                result += fromVertex + ", ";
                isVisited[fromVertex] = true;
                for (int toVertex = NumberOfVertices - 1; toVertex >= 0; toVertex--) {
                    if (isVisited[toVertex] == false && graph[fromVertex][toVertex] > 0) {
                        s.push(toVertex);
                    }
                }
            }
        }
        repaint();
    }

    int[] distance;
    int[] theVertexBefore;
    public static final int Infinity = 1000000000;
    boolean isGraphConnected;
    boolean isDrawPrimPath = false;

    ArrayList<Integer>[] dijkstra_theVertexBefore;
    ArrayList<String> dijkstraPathString;
    String dijkstraPath = "", dijkstraMsg = "";
    String dijkstraAllMsg = "";
    int pathIndex = 0;

    //reset Prim 
    public void PrimReset() {
        dijkstra_theVertexBefore = new ArrayList[MAX_VERTEX];

        for (int i = 0; i < MAX_VERTEX; i++) {
            distance[i] = Infinity;
            dijkstra_theVertexBefore[i] = new ArrayList<>();
            dijkstra_theVertexBefore[i].add(i);
            theVertexBefore[i] = i;
            isVisited[i] = false;
        }
        isDrawPrimPath = true;
        dijkstraPathString = new ArrayList<>();
        dijkstraPath = "";
        dijkstraMsg = "";
        dijkstraAllMsg = "";
        pathIndex = 0;
    }

    //find Nearest vertex 
    public int findNearestVertex() {
        int minIndex = -1, minValue = Infinity;
        for (int i = 0; i < NumberOfVertices; i++) {
            if (isVisited[i] == false && distance[i] < minValue) {
                minValue = distance[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    String primPath = "Prime: \n";

    //find prim
    public void prim() {
        ArrayList<GEdge> listEdges = new ArrayList();
        PrimReset();
        distance[0] = 0;
        int currentVertex;
        isGraphConnected = true;
        int sum = 0;
        for (int i = 0; i < NumberOfVertices; i++) {
            currentVertex = findNearestVertex();
            if (currentVertex == -1) {//do thi khong lien thong => khong tim thay cay khung co trong so nho nhat
                isGraphConnected = false;
                return;
            } else {
                isVisited[currentVertex] = true;
                for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                    if (isVisited[toVertex] == false && graph[currentVertex][toVertex] > 0
                            && graph[currentVertex][toVertex] < distance[toVertex]) {
                        distance[toVertex] = graph[currentVertex][toVertex];
                        theVertexBefore[toVertex] = currentVertex;
                    }
                }
            }
        }

        if (isGraphConnected) {
            for (int i = 0; i < NumberOfVertices; i++) {
                sum += distance[i];
            }
            primPath += sum + "\n";
            int fromVertex;
            int toVertex;
            int edgeIndex;
            for (int i = 0; i < NumberOfVertices; i++) {
                fromVertex = theVertexBefore[i];
                toVertex = i;
                primPath += fromVertex + " " + toVertex + " " + graph[fromVertex][toVertex] + "\n";
                if (fromVertex != toVertex) {
                    listEdges.add(new GEdge(graph[fromVertex][toVertex], vertices.get(fromVertex), vertices.get(toVertex)));
                }
            }
        }

        listEdges.sort(new Comparator<GEdge>() {
            @Override
            public int compare(GEdge o1, GEdge o2) {
                if (o1.getValue() > o2.getValue()) {
                    return 1;
                } else if (o1.getValue() == o2.getValue()) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        primPath += "Kruskal: \n" + sum + "\n";
        for (GEdge listEdge : listEdges) {
            primPath += listEdge.getStart().getValue() + " " + listEdge.getEnd().getValue() + " " + listEdge.getValue() + "\n";
        }
    }

    //dijkstra 
    public void dijkstra(int startVertext, int endVertext) {
        try {

            PrimReset();
            distance[startVertext] = 0;

            int currentVertex;
            isGraphConnected = true;
            for (int i = 0; i < NumberOfVertices; i++) {
                currentVertex = findNearestVertex();
                if (currentVertex == -1) {//do thi khong lien thong => khong tim thay cay khung co trong so nho nhat
                    isGraphConnected = false;
                    break;
                } else {
                    isVisited[currentVertex] = true;
                    for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                        if (isVisited[toVertex] == false
                                && graph[currentVertex][toVertex] > 0
                                && distance[currentVertex] + graph[currentVertex][toVertex] < distance[toVertex]) {
                            distance[toVertex] = distance[currentVertex] + graph[currentVertex][toVertex];
                            theVertexBefore[toVertex] = currentVertex;
                        }
                    }
                }
            }
            if (isGraphConnected) {
                dijkstraPath = "" + endVertext;

                currentVertex = endVertext;
                while (currentVertex != startVertext) {
                    currentVertex = theVertexBefore[currentVertex];
                    dijkstraPath = currentVertex + "->" + dijkstraPath;
                }
                dijkstraMsg = dijkstraPath + ": " + distance[endVertext];
            } else {
                dijkstraMsg = "";
            }
            isDrawPrimPath = false;
            repaint();

        } catch (Exception e) {
        }

    }

    //dijkstra all path possible
    public void dijkstraAllPath() {

        try {
            int startVertext = Integer.parseInt(
                    JOptionPane.showInputDialog(this, "Please enter the start vertex", 0));
            int endVertext = Integer.parseInt(
                    JOptionPane.showInputDialog(this, "Please enter the end vertex", (NumberOfVertices - 1) + ""));

            if ((startVertext > vertices.size() - 1) || (endVertext > vertices.size() - 1) || (startVertext < 0) || (endVertext < 0)) {
                JOptionPane.showMessageDialog(this, "Graph only have " + (vertices.size() - 1) + " vertices!");
            }

            PrimReset();
            distance[startVertext] = 0;

            int currentVertex;
            isGraphConnected = true;
            for (int i = 0; i < NumberOfVertices; i++) {
                currentVertex = findNearestVertex();
                if (currentVertex == -1) {//do thi khong lien thong => khong tim thay cay khung co trong so nho nhat
                    isGraphConnected = false;
                    break;
                } else {
                    isVisited[currentVertex] = true;
                    for (int toVertex = 0; toVertex < NumberOfVertices; toVertex++) {
                        if ((isVisited[toVertex] == false || toVertex == endVertext)
                                && graph[currentVertex][toVertex] > 0
                                && distance[currentVertex] + graph[currentVertex][toVertex] <= distance[toVertex]) {
                            if (distance[currentVertex] + graph[currentVertex][toVertex] < distance[toVertex]) {
                                dijkstra_theVertexBefore[toVertex].clear();
                            }
                            distance[toVertex] = distance[currentVertex] + graph[currentVertex][toVertex];
                            dijkstra_theVertexBefore[toVertex].add(currentVertex);
                        }
                    }
                }
            }
            if (isGraphConnected) {
                dijkstraPathString.clear();
                String path = "" + endVertext;
                currentVertex = endVertext;
                dijkstra_displayPath(path, currentVertex, startVertext, endVertext);
                dijkstraAllMsg = "The length of the shortest path form " + startVertext + " to " + endVertext
                        + " is " + distance[endVertext] + ": ";
            } else {
                dijkstraAllMsg = "Can't find path from " + startVertext + " to " + endVertext + "!";
            }
            isDrawPrimPath = false;
            repaint();
        } catch (Exception e) {
        }
    }

    //display path for all dijkstra
    public void dijkstra_displayPath(String path, int currentVertex, int startVertex, int endVertex) {
        if (currentVertex != endVertex) {
            path = currentVertex + " -> " + path;
        }
        if (currentVertex == startVertex) {
            dijkstraPathString.add(path);
        } else {
            for (int i = 0; i < dijkstra_theVertexBefore[currentVertex].size(); ++i) {
                dijkstra_displayPath(path, dijkstra_theVertexBefore[currentVertex].get(i), startVertex, endVertex);
            }
        }
    }

    //change path for all dijkstra
    public void nextPath() {
        pathIndex = (pathIndex + 1) % dijkstraPathString.size();
        repaint();
    }

    //read Matrix file
    void readMatrixDataFile(File openFile) {
        try (Scanner scan = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = scan.nextInt();
            int x, y;
            for (int i = 0; i < this.NumberOfVertices; i++) {
                x = scan.nextInt();
                y = scan.nextInt();
                this.vertices.add(new GVertex(i, x, y));
            }
            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = 0; j < this.NumberOfVertices; j++) {
                    this.graph[i][j] = scan.nextInt();
                    if (i < j && this.graph[i][j] > 0) {
                        this.edges.add(new GEdge(this.graph[i][j], this.vertices.get(i), this.vertices.get(j)));
                    }
                }
            }
            dijkstraAllMsg = "";
            dijkstraMsg = "";
            isDrawPrimPath = false;
            updateGraphInfo();
            repaint();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

    //read List file
    void readListDataFile(File openFile) {
        try (Scanner scan = new Scanner(openFile)) {
            this.edges.clear();
            this.vertices.clear();
            this.NumberOfVertices = scan.nextInt();
            int countEdge = scan.nextInt();
            int x, y;
            for (int i = 0; i < this.NumberOfVertices; i++) {
                x = scan.nextInt();
                y = scan.nextInt();
                this.vertices.add(new GVertex(i, x, y));
            }
            for (int i = 0; i < this.NumberOfVertices; i++) {
                for (int j = 0; j < this.NumberOfVertices; j++) {
                    this.graph[i][j] = 0;
                }
            }
            int start, end, value;
            for (int i = 0; i < countEdge; i++) {
                start = scan.nextInt();
                end = scan.nextInt();
                value = scan.nextInt();
                this.edges.add(new GEdge(value, this.vertices.get(start), this.vertices.get(end)));
                this.graph[start][end] = this.graph[end][start] = value;
            }
            dijkstraAllMsg = "";
            dijkstraMsg = "";
            isDrawPrimPath = false;
            updateGraphInfo();
            repaint();
        } catch (FileNotFoundException e) {
            System.err.println(e);
        }
    }

}
