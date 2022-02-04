public class Node {
    private int cellType;
    private int hops;
    private int x;
    private int y;
    private int lastX;
    private int lastY;
    private double dToEnd;
    Pathfinding parent;

    public Node(int type, int x, int y, Pathfinding parent) {
        this.cellType = type;
        this.x = x;
        this.y = y;
        this.hops = -1;
        this.parent = parent;
    }

    public int getType() {
        return cellType;
    }

    public int getHops() {
        return hops;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public void setType(int type) {
        cellType = type;
    }

    public void setLastNode(int x, int y) {
        this.lastX = x;
        this.lastY = y;
    }

    public double getEuclideanDist() {
        int xDif = Math.abs(x-parent.finishx);
        int yDif = Math.abs(y-parent.finishy);
        dToEnd = Math.sqrt((xDif*xDif)+(yDif*yDif));
        return dToEnd;
    }

    public void setHops(int hops) {
        this.hops = hops;
    }
}
