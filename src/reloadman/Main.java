package reloadman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main implements Runnable, KeyListener {
    private static final long serialVersionUID = 1L;
    public static BufferStrategy bs;
    public boolean left, right, forward, back;
    public Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public int mapWidth = 15;
    public int mapHeight = 15;
    private Thread thread;
    private boolean running;
    private BufferedImage image;
    public int[] pixels;
    public ArrayList<Texture> textures;
    public Camera camera;
    public Screen screen;
    public Audio theme;
    public static int[][] map = {
        {1,1,1,1,1,1,1,1,2,2,2,2,2,2,2},
        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
        {1,0,3,3,3,3,3,0,0,0,0,0,0,0,2},
        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
        {1,0,3,0,0,0,3,0,2,2,2,0,2,2,2},
        {1,0,3,0,0,0,3,0,2,0,0,0,0,0,2},
        {1,0,3,3,0,3,3,0,2,0,0,0,0,0,2},
        {1,0,0,0,0,0,0,0,2,0,0,0,0,0,2},
        {1,1,1,1,1,1,1,1,4,4,4,0,4,4,4},
        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
        {1,0,0,0,0,0,1,4,0,0,0,0,0,0,4},
        {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
        {1,0,0,0,0,0,1,4,0,3,3,3,3,0,4},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,4},
        {1,1,1,1,1,1,1,4,4,4,4,4,4,4,4}
    };
    private JFrame frame;

    public Main() {
        int width = (int) screenSize.getWidth();
        int height = (int) screenSize.getHeight();
        thread = new Thread(this);
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        textures = new ArrayList<Texture>();
        textures.add(Texture.wood);
        textures.add(Texture.brick);
        textures.add(Texture.bluestone);
        textures.add(Texture.stone);
        camera = new Camera(4.5, 4.5, 1, 0, 0, -.66, .08, .025);
        screen = new Screen(map, mapWidth, mapHeight, textures, width, height);
        frame = new JFrame();
        frame.addKeyListener(this);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setTitle("Raycaster");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(true);
        frame.setVisible(true);
        
        start();
    }

 
    private synchronized void start() {
        running = true;
        thread.start();
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void render() {
        bs = frame.getBufferStrategy();
        if(bs == null) {
            frame.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        bs.show();
    }

    public void keyPressed(KeyEvent key) {
        switch(key.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            left = true;
            break;
        case KeyEvent.VK_RIGHT:
            right = true;
            break;
        case KeyEvent.VK_W:
            forward = true;
            break;
        case KeyEvent.VK_S:
            back = true;
            break;
        }
    }

    public void keyReleased(KeyEvent key) {
        switch(key.getKeyCode()) {
        case KeyEvent.VK_LEFT:
            left = false;
            break;
        case KeyEvent.VK_RIGHT:
            right = false;
            break;
        case KeyEvent.VK_W:
            forward = false;
            break;
        case KeyEvent.VK_S:
            back = false;
            break;
        case KeyEvent.VK_ESCAPE:
            System.exit(1);
        }
    }

    public void run() {
        long lastTime = System.nanoTime();
        final double fps = 1000000000.0 / 60.0;
        double delta = 0;
        frame.requestFocus();
        while(running) {
            long now = System.nanoTime();
            delta += ((now - lastTime) / fps);
            lastTime = now;
            while(delta >= 1) {
                screen.update(camera, pixels);
                if(left) { camera.turnLeft(map); }
                if(right) { camera.turnRight(map); }
                if(forward) { camera.moveForward(map); }
                if(back) { camera.moveBackwards(map); }
                delta--;
            }
            render(); // displays to the screen unrestricted time
        }
    }

    public static void main(String[] args) {
        Main game = new Main();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // TODO Auto-generated method stub
    }
    
}
