
package graph_b1;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * GRAPH 1.DFS 2.BFS
 */
public class GEdge {

    private static final int PADDING_X = 12;
    private static final int PADDING_Y = 8;
    private static final int LABEL_W = PADDING_X * 2;
    private static final int LABEL_H = PADDING_Y * 2;
    private static final int LABEL_R = 10;
    public static final Font FONT = new Font("Courier", Font.PLAIN, 12);
    private int x, y;       //trung tam cua cap canh
    private int value;
    private GVertex start;
    private GVertex end;
    private boolean isSelected = false;

    public GEdge() {
    }

    public GEdge(int value, GVertex start, GVertex end) {
        this.value = value;
        this.start = start;
        this.end = end;
        //calculateCenterLocation();
    }

    private void calculateCenterLocation() {
        this.x = (start.getX() + end.getX()) / 2;
        this.y = (start.getY() + end.getY()) / 2;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getLabel() {
        return value + "";
    }

    public GVertex getStart() {
        return start;
    }

    public void setStart(GVertex start) {
        this.start = start;
    }

    public GVertex getEnd() {
        return end;
    }

    public void setEnd(GVertex end) {
        this.end = end;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public boolean isInside(int mousex, int mousey) {
        int x1 = this.x - PADDING_X;
        int y1 = this.y - PADDING_Y;
        int x2 = this.x + PADDING_X;
        int y2 = this.y + PADDING_Y;
        return x1 <= mousex && mousex <= x2 && y1 <= mousey && mousey <= y2;
    }

    public void draw(Graphics2D g) {
        g.setColor(isSelected ? Color.RED : Color.BLACK);
        g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());

        calculateCenterLocation();
        g.setColor(isSelected ? Color.YELLOW : Color.WHITE);
        g.fillRoundRect(this.x - PADDING_X, this.y - PADDING_Y, LABEL_W, LABEL_H, LABEL_R, LABEL_R);

        g.setColor(isSelected ? Color.RED : Color.BLACK);
        GVertex.drawCenteredString(g, getLabel(), this.x - PADDING_X, this.y - PADDING_Y, LABEL_W, LABEL_H, FONT);
    }
}
