
package graph_b8;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * GRAPH CUT VERTEX
 */
public class GVertex {

    public static final int RADIUS = 12;
    public static final int DIAMETER = RADIUS * 2;
    public final Font FONT = new Font("Courier", Font.PLAIN, 12);

    private int x, y;
    private int value;
    private boolean isSelected = false;

    public GVertex() {
    }

    public GVertex(int value, int x, int y) {
        this.x = x;
        this.y = y;
        this.value = value;
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

    public String getLabel() {
        return value + "";
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        int _x = x1 - x2;
        int _y = y1 - y2;
        return Math.sqrt(_x * _x + _y * _y);
    }

    public boolean isInside(int mousex, int mousey) {
        return distance(x, y, mousex, mousey) <= RADIUS;
    }

    //Code tham khao de hien chu o trong hinh tron
    public static void drawCenteredString(Graphics g, String text, int x, int y, int w, int h, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int _x = x + (w - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int _y = y + ((h - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, _x, _y);
    }

    public void draw(Graphics2D g) {
        g.setColor(isSelected ? Color.RED : Color.WHITE);
        g.fillOval(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER);

        g.setColor(isSelected ? Color.YELLOW : Color.BLACK);
        g.drawOval(x - RADIUS, y - RADIUS, DIAMETER, DIAMETER);

        //g.setColor(isSelected ? Color.YELLOW : Color.BLACK);
        drawCenteredString(g, getLabel(), x - RADIUS, y - RADIUS, DIAMETER, DIAMETER, FONT);
    }

}
