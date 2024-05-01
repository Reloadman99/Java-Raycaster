package reloadman;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Main extends JFrame implements Runnable, KeyListener{
	private static final long serialVersionUID = 1L;
	public boolean left, right, forward, back;
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
	public static int[][] map = 
		{
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
	public Main() {
		theme = new Audio("Assets/Theme.wav", -30.0f);
		thread = new Thread(this);
		image = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		textures = new ArrayList<Texture>();
		textures.add(Texture.wood);
		textures.add(Texture.brick);
		textures.add(Texture.bluestone);
		textures.add(Texture.stone);
		camera = new Camera(4.5, 4.5, 1, 0, 0, -.66);
		screen = new Screen(map, mapWidth, mapHeight, textures, 640, 480);
		theme.playSound();
		addKeyListener(this);
		setSize(640, 480);
		setResizable(false);
		setTitle("Raycaster");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLocationRelativeTo(null);
		setVisible(true);
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
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(3);
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
		case KeyEvent.VK_UP:
			forward = true;
			break;
		case KeyEvent.VK_DOWN:
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
		case KeyEvent.VK_UP:
			forward = false;
			break;
		case KeyEvent.VK_DOWN:
			back = false;
			break;
		}
	}
	public void run() {
		long lastTime = System.nanoTime();
		final double fps = 1000000000.0 / 60.0;
		double delta = 0;
		requestFocus();
		while(running) {
			long now = System.nanoTime();
			delta += ((now-lastTime) / fps);
			lastTime = now;
			while (delta >= 1)
			{
				screen.update(camera, pixels);
				if(left) {camera.turnLeft(map);}
				if(right) {camera.turnRight(map);}
				if(forward) {camera.moveForward(map);}
				if(back) {camera.moveBackwards(map);}
				delta--;
			}
			render();//displays to the screen unrestricted time
		}
	}
	public static void main(String [] args) {
		Main game = new Main();
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}