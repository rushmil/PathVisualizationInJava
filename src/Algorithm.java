import java.util.ArrayList;
public class Algorithm {

    public Pathfinding parent;

    public Algorithm(Pathfinding parent) {
        this.parent = parent;
    }

    public void AStar() {
        ArrayList<Node> priority = new ArrayList<Node>();
        priority.add(parent.map[parent.startx][parent.starty]);
        while(parent.solving) {
            if(priority.size() <= 0) {
                parent.solving = false;
                break;
            }
            int hops = priority.get(0).getHops()+1;
            ArrayList<Node> explored = exploreNeighbors(priority.get(0),hops);
            if(explored.size() > 0) {
                priority.remove(0);
                priority.addAll(explored);
                parent.Update();
                parent.delay();
            } else {
                priority.remove(0);
            }
            sortQueue(priority);
        }
    }

    public ArrayList<Node> exploreNeighbors(Node current, int hops) {
        ArrayList<Node> explored = new ArrayList<Node>();
        for(int a = -1; a <= 1; a++) {
            for(int b = -1; b <= 1; b++) {
                int xbound = current.getX()+a;
                int ybound = current.getY()+b;
                if((xbound > -1 && xbound < parent.cells) && (ybound > -1 && ybound < parent.cells)) {
                    Node neighbor = parent.map[xbound][ybound];
                    if((neighbor.getHops()==-1 || neighbor.getHops() > hops) && neighbor.getType()!=2) {
                        explore(neighbor, current.getX(), current.getY(), hops);
                        explored.add(neighbor);
                    }
                }
            }
        }
        return explored;
    }

    public void explore (Node current, int lastx, int lasty, int hops) {
        if(current.getType()!=0 && current.getType() != 1)
            current.setType(4);
        current.setLastNode(lastx, lasty);
        current.setHops(hops);
        if(current.getType() == 1) {
            backtrack(current.getLastX(), current.getLastY(),hops);
        }
    }

    public ArrayList<Node> sortQueue(ArrayList<Node> sort) {
        int c= 0;
        while (c < sort.size()) {
            int sm = c;
            for (int i = c+1; i < sort.size(); i++) {
                if (sort.get(i).getEuclideanDist()+sort.get(i).getHops() < sort.get(sm).getEuclideanDist()+sort.get(sm).getHops()) {
                    sm = i;
                }
            }
            if (c != sm) {
                Node temp = sort.get(c);
                sort.set(c, sort.get(sm));
                sort.set(sm, temp);
            }
            c++;
        }
        return sort;
    }

    public void backtrack(int lx, int ly, int hops) {
        parent.length = hops;
        while(hops > 1) {
            Node current = parent.map[lx][ly];
            current.setType(5);
            lx = current.getLastX();
            ly = current.getLastY();
            hops--;
        }
        parent.solving = false;
    }
}
