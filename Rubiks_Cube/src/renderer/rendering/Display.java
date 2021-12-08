package renderer.rendering;

import renderer.input.Mouse;
import renderer.cubes.Cube;
import renderer.cubes.FastCube;
import renderer.solvers.MattSolver;
import renderer.solvers.Randomizer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Display extends Canvas implements Runnable{

    public Thread thread; // Rendering is ran on separate single thread
    private JFrame frame;
    private static String title = "Rubik's Cube";

    private Cube cube;
    private Mouse mouse;
    private static FastCube fastCube;

    // Solvers
    private static MattSolver mattSolver;

    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
    private static final double MOUSE_SENS = 2.5;
    private static final double NANO_SECOND = 1000000000.0 / 60;
    private static final double SECOND = 1000;

    private static boolean running = false;
    private int prevMouse = -1;
    private int mouseX;
    private int mouseY;
    private boolean isDragging = false;
    private boolean isShifting = false;

    // Data structures used to track Cube rotation inorder to reverse and turn faces
    public static ArrayList degreeList = new ArrayList<Double>();
    public static ArrayList axisList = new ArrayList<Boolean>();
    public static double currentDegree;
    public static boolean isYAxis = false;


    public Display() {
        this.frame = new JFrame();

        Dimension size = new Dimension(WIDTH, HEIGHT);
        this.setPreferredSize(size);

        // This adds ability to interact with display through my Mouse class
        this.mouse = new Mouse();
        this.addMouseListener(this.mouse);
        this.addMouseMotionListener(this.mouse);
        this.addMouseWheelListener(this.mouse);

        // Initial values of cube rotation, makes it easier to reverse and un-reverse rotation
        degreeList.add(0.0);
        axisList.add(false);

        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 16) {
                    isShifting = true;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 16) {
                    isShifting = false;
                }

                char keyChar = e.getKeyChar();

                try {
                    switch (keyChar) {
                        case 'u':
                            getCube().performMoves("U");
                            break;
                        case 'f':
                            getCube().performMoves("F");
                            break;
                        case 'r':
                            getCube().performMoves("R");
                            break;
                        case 'l':
                            getCube().performMoves("L");
                            break;
                        case 'b':
                            getCube().performMoves("B");
                            break;
                        case 'd':
                            getCube().performMoves("D");
                            break;
                        case 'z':
                            System.out.println("r u B l u r l f r R B d U R d u D D R L ");
                            getCube().performMoves("r u B l u r l f r R B d U R d u D D R L ");
                            break;
                        case 'x':
                            String moves = Randomizer.getMoves(20);
                            System.out.println(moves);
                            getCube().performMoves(moves);
                            break;
                        case 'm':
                            mattSolver.solve(getCube());
                            getCube().performMoves(mattSolver.getSolution());
                            break;
                    }
                } catch (Exception e1) {

                }

            }
        });
    }

    public synchronized void start() {
        running = true;
        this.thread = new Thread(this, "Display");
        this.thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            this.thread.join(); // quits the thread
        } catch (Exception e) {
        }
    }

    // This method renders the simulation and updates it
    // The update function is compressed to give smooth animations
    public void run() {

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = NANO_SECOND;
        double delta = 0;
        int frames = 0;

        // Initializes objects in display
        init();

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {

                if (Cube.isCalculating) {
                    continue;
                }

                update();
                delta--;
                render();
                frames++;
            }

            // Updates frame title every second to display Frames Per Second
            if (System.currentTimeMillis() - timer > SECOND) {
                timer += SECOND;
                this.frame.setTitle(title + " | " + frames + " fps");
                frames = 0; // resets frames to 0 to properly calculate updated fps
            }

        }
        stop();
    }

    private void render() {

        // This buffer creates a good mechanism to organize all memory required for this program
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        // Draws background of display

        drawBackground(g);

        //  renders 3d objects
        cube.render(g);
        drawFastCube(g);

        g.dispose();
        bs.show();
    }

    private void drawBackground(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;

        Image image = Toolkit.getDefaultToolkit().getImage("cubebackground.jpg");
        g.drawImage(image, 0, 0, this);


    }

    private Color findColorFastCube(short value) {

        switch (value) {
            case 0:
                return new Color(250, 30, 10);
            case 1:
                return new Color(252, 254, 255);
            case 2:
                return new Color(255, 162, 0);
            case 3:
                return new Color(245, 245, 32);
            case 4:
                return new Color(63, 252, 20);
            case 5:
                return new Color(0, 145, 255);
        }
        return null;
    }

    private void drawFastCube(Graphics g) {
        short[][][] cube = this.fastCube.getCube();

        int cubieSize = 20;
        int xPlacement = 700;
        int yPlacement = 75;

        for (int face = 0; face < cube.length; face++) {

            // GREEN FACE
            if (face == 4) {
                xPlacement = 765;
                yPlacement = 15;
            }

            // BLUE FACE
            if (face == 5) {
                xPlacement = 765;
                yPlacement = 135;
            }

            for (int row = 0; row < cube[0].length; row++) {
                for (int col = 0; col < cube[0][0].length; col++) {
                    g.setColor(findColorFastCube(cube[face][row][col]));
                    g.fillRect(xPlacement + (cubieSize * col), yPlacement + (cubieSize * row), cubieSize/2, cubieSize/2);
                }
            }
            xPlacement += cubieSize * 3.25;
        }
    }

    private void init() {
        this.cube = new Cube(50, 8);
        this.fastCube = new FastCube();
        this.mattSolver = new MattSolver(fastCube.getCube());
    }

    private void update() {
        updateMouse();
    }

    // Keeps track if user is dragging -- to move objects. And scrolling -- to zoom
    private void updateMouse() {
        int x = this.mouse.getMouseX();
        int y = this.mouse.getMouseY();

        if (this.mouse.getMouseButton() == 1) {
            int xDif = x - mouseX;
            int yDif = y - mouseY;
            double yDegree = -yDif / MOUSE_SENS;
            double zDegree = -xDif / MOUSE_SENS;


            if (isYAxis) {
                currentDegree += yDegree;
            } else {
                currentDegree += zDegree;
            }

            if (!isDragging) {
                isDragging = true;

            }

            // Test if user is shifting to change axis to rotate
            if (isShifting) {
                isYAxis = true;
                this.cube.rotate(true, 0, yDegree, 0);

            } else {
                isYAxis = false;
                this.cube.rotate(true, 0, 0, zDegree);
            }


            this.cube.addY(yDegree);
            this.cube.addZ(zDegree);

        } else {
            if (isDragging) {
                isDragging = false;
                degreeList.add(currentDegree);

                axisList.add(isYAxis);
                currentDegree = 0;
            }
        }

        if (this.mouse.isScrollingUp()) {
            PointConverter.zoomIn();
        } else if (this.mouse.isScrollingDown()) {
            PointConverter.zoomOut();
        }
        this.mouse.resetScroll();

        mouseX = x;
        mouseY = y;
    }

    public Cube getCube() {
        return this.cube;
    }

    public static FastCube getFastCube() {
        return fastCube;
    }

    public static MattSolver getMattSolver() {
        return mattSolver;
    }

    public static void main(String[] args) {
        Display display = new Display();

        display.frame.setTitle(title);
        display.frame.add(display); // Adding display to frame
        display.frame.pack();
        display.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        display.frame.setLocationRelativeTo(null); // This centers display on user's screen

        display.frame.setVisible(true);
        display.start();
    }

    public Thread getThread() {
        return this.thread;
    }

}
