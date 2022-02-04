import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Map extends JPanel implements MouseListener, MouseMotionListener {

    Pathfinding parent;

    public Map(Pathfinding parent) {
        addMouseListener(this);
        addMouseMotionListener(this);
        this.parent = parent;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int x = 0; x < parent.cells; x++) {
            for (int y = 0; y < parent.cells; y++) {
                switch (parent.map[x][y].getType()) {
                    case 0:
                        g.setColor(Color.GREEN);
                        break;
                    case 1:
                        g.setColor(Color.RED);
                        break;
                    case 2:
                        g.setColor(Color.BLACK);
                        break;
                    case 3:
                        g.setColor(Color.WHITE);
                        break;
                    case 4:
                        g.setColor(Color.CYAN);
                        break;
                    case 5:
                        g.setColor(Color.YELLOW);
                        break;
                }
                g.fillRect(x * parent.CSIZE, y * parent.CSIZE, parent.CSIZE, parent.CSIZE);
                g.setColor(Color.BLACK);
                g.drawRect(x * parent.CSIZE, y * parent.CSIZE, parent.CSIZE, parent.CSIZE);
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        try {
            int x = e.getX() / parent.CSIZE;
            int y = e.getY() / parent.CSIZE;
            Node current = parent.map[x][y];
            if ((parent.tool == 2 || parent.tool == 3) && (current.getType() != 0 && current.getType() != 1))
                current.setType(parent.tool);
            parent.Update();
        } catch (Exception z) {
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        parent.resetMap();
        parent.resetMap();
        try {
            int x = e.getX() / parent.CSIZE;
            int y = e.getY() / parent.CSIZE;
            Node current = parent.map[x][y];
            switch (parent.tool) {
                case 0: {
                    if (current.getType() != 2) {
                        if (parent.startx > -1 && parent.starty > -1) {
                            parent.map[parent.startx][parent.starty].setType(3);
                            parent.map[parent.startx][parent.starty].setHops(-1);
                        }
                        current.setHops(0);
                        parent.startx = x;
                        parent.starty = y;
                        current.setType(0);
                    }
                    break;
                }
                case 1: {
                    if (current.getType() != 2) {
                        if (parent.finishx > -1 && parent.finishy > -1)
                            parent.map[parent.finishx][parent.finishy].setType(3);
                        parent.finishx = x;
                        parent.finishy = y;
                        current.setType(1);
                    }
                    break;
                }
                default:
                    if (current.getType() != 0 && current.getType() != 1)
                        current.setType(parent.tool);
                    break;
            }
            parent.Update();
        } catch (Exception z) {
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}