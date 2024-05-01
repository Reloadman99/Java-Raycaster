package reloadman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Texture {
	public int[] pixels;
	private String loc;
	public final int SIZE;
	public static Texture wood = new Texture("Assets/wood.png", 64);
	public static Texture brick = new Texture("Assets/brick.png", 64);
	public static Texture bluestone = new Texture("Assets/bluestone.png", 64);
	public static Texture stone = new Texture("Assets/stone.png", 64);
	public Texture(String location, int size) {
		loc = location;
		SIZE = size;
		pixels = new int[SIZE * SIZE];
		try {
			BufferedImage image = ImageIO.read(new File(loc));
			image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}