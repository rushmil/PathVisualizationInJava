import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Pathfinding {
    JFrame frame;

    public int cells = 50;
    public int length = 0;
    public int startx = -1;
    public int starty = -1;
    public int finishx = -1;
    public int finishy = -1;
    private int WIDTH = 800;
    private final int HEIGHT = 650;
    public int CSIZE = 10;
    public int tool = 0;
    int delay = 30;
    public boolean solving = false;
    JPanel main = new JPanel();

    private String[] tools = {"Start", "Finish", "Wall", "Eraser"};

    public Node[][] map;
    Algorithm alg = new Algorithm(this);

    //Buttons
    JButton resetB = new JButton("Reset");
    JButton runB = new JButton("Run");
    JButton clearB = new JButton("Clear");
    JButton startB = new JButton("Start");
    JButton wallB = new JButton("Wall");
    JButton eraserB = new JButton("Eraser");
    JButton endB = new JButton("End");

    //Map
    Map canvas;

    public static void main(String[] args) {
        new Pathfinding();
    }

    public Pathfinding() {
        clearMap();
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setSize(WIDTH, HEIGHT);
        frame.setTitle("Pathfinding Visualizer");
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        main.setLayout(null);
        main.setBounds(0, 0, WIDTH, HEIGHT);
        main.setBackground(new Color(55, 157, 180, 60)); //adding colours

        //adding buttons
        resetB.setBounds(179, 583, 100, 40);
        resetB.setBackground(new Color(55, 157, 180));
        resetB.setForeground(Color.WHITE);
        main.add(resetB);

        runB.setBounds(351, 583, 100, 40);
        runB.setBackground(new Color(55, 157, 180));
        runB.setForeground(Color.WHITE);
        main.add(runB);

        clearB.setBounds(521, 583, 100, 40);
        clearB.setBackground(new Color(55, 157, 180));
        clearB.setForeground(Color.WHITE);
        main.add(clearB);

        startB.setBounds(676, 93, 100, 40);
        startB.setBackground(new Color(55, 157, 180));
        startB.setForeground(Color.WHITE);
        main.add(startB);

        wallB.setBounds(676, 149, 100, 40);
        wallB.setBackground(new Color(55, 157, 180));
        wallB.setForeground(Color.WHITE);
        main.add(wallB);

        eraserB.setBounds(676, 205, 100, 40);
        eraserB.setBackground(new Color(55, 157, 180));
        eraserB.setForeground(Color.WHITE);
        main.add(eraserB);

        endB.setBounds(676, 261, 100, 40);
        endB.setBackground(new Color(55, 157, 180));
        endB.setForeground(Color.WHITE);
        main.add(endB);

        canvas = new Map(this);
        canvas.setBounds(151, 63, 500, 500);
        main.add(canvas);

        frame.getContentPane().add(main);

        //initializing buttons
        resetB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetMap();
                Update();
            }
        });
        clearB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearMap();
                Update();
            }
        });
        runB.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               reset();
               if ((startx > -1 && starty > -1) && (finishx > -1 && finishy > -1)) {
                   solving = true;
               }
           }
        });
        startB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tool = 0;
            }
        });
        wallB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tool = 2;
            }
        });
        eraserB.addActionListener(new ActionListener() {
            @Override
           public void actionPerformed(ActionEvent e) {
                tool = 3;
            }
        });
        endB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tool = 1;
            }
        });

        startSearch();
    }

    private void clearMap() {
        finishx = -1;
        finishy = -1;
        startx = -1;
        starty = -1;
        map = new Node[cells][cells];
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                map[x][y] = new Node(3,x,y, this);
            }
        }
        reset();
    }

    private void reset() {
        solving = false;
        length = 0;
    }

    public void resetMap() {
        for (int x = 0; x < cells; x++) {
            for (int y = 0; y < cells; y++) {
                Node current = map[x][y];
                if (current.getType() == 4 || current.getType() == 5) {
                    map[x][y] = new Node(3,x,y, this);
                }
            }
        }
        if (startx > -1 && starty > -1) {
            map[startx][starty] = new Node(0,startx,starty, this);
            map[startx][starty].setHops(0);
        }
        if (finishx > -1 && finishy > -1) {
            map[finishx][finishy] = new Node(1,finishx,finishy, this);
        }
        reset();
    }

    public void Update() {
        canvas.repaint();
    }

    public void startSearch() {
        if (solving) {
            alg.AStar();
        }
        pause();
    }

    public void pause() {
        int i = 0;
        while (!solving) {
            i++;
            if (i > 500) {
                i = 0;
            }
            try {
                Thread.sleep(1);
            } catch (Exception z) {};
        }
        startSearch();
    }

    public void delay() {
        try {
            Thread.sleep(delay);
        } catch(Exception e) {};
    }
}
