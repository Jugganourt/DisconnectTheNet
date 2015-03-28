package graphics;

import static org.lwjgl.opengl.GL11.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL30;

import sun.java2d.loops.DrawRect;

public class Renderer {

	private static Map<String, Integer> textureIdMap;
	
	
	public static void init(){
		textureIdMap = new HashMap<String, Integer>();
		glDepthMask(false);
		glEnable(GL_BLEND);
		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

	}
	
	public static void reshape() {
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 0, 1);
		glMatrixMode(GL_MODELVIEW);
		glViewport(0, 0, Display.getWidth(), Display.getHeight());
	}
	
	public static void createWindow(){
		try {
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setResizable(false);
			Display.setTitle("Disconnect The Net");
			Display.setVSyncEnabled(true);
			Display.create();
		} catch (LWJGLException e) {
			System.out.println("Failed to create window");
			e.printStackTrace();
		}

	}

	public static void destroy() {
		Display.destroy();
		System.exit(0);
	}
	
	public static void setColour(double r, double g, double b){
		glColor3d(r, g, b);
	}
	
	public static void setColour(float r, float g, float b){
		glColor3f(r, g, b);
	}
	
	public static void setColour(Colour c){
		glColor3f(c.r, c.g, c.b);
	}
	
	public static void drawTextureRectangle(int id, int x, int y, int width, int height){
		glEnable(GL_TEXTURE_2D);

		glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		glBindTexture(GL_TEXTURE_2D, id);
		
		int tlX = x;
		int tlY = y;
		int trX = x + width;
		int trY = y;
		int blX = x;
		int blY = y + height;
		int brX = x + width;
		int brY = y + height;
		
		glBegin(GL_QUADS);

		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(tlX, tlY, 0.0f);

		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(blX, blY, 0.0f);

		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(brX, brY, 0.0f);

		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(trX, trY, 0.0f);

		glEnd();
		
		
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void drawRectangle(int x, int y, int width, int height){
		
		int tlX = x;
		int tlY = y;
		int trX = x + width;
		int trY = y;
		int blX = x;
		int blY = y + height;
		int brX = x + width;
		int brY = y + height;
		
		glBegin(GL_QUADS);

		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(tlX, tlY, 0.0f);

		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(blX, blY, 0.0f);
		
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(brX, brY, 0.0f);

		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(trX, trY, 0.0f);
		
		glEnd();
	}

	public static void clear() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		Renderer.setColour(Colour.WHITE);
	}
	
	
	public static int uploadTexture(BufferedImage image) {

		ByteBuffer buffer = byteBufferFromBufferedImage(image);

		int tex = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, tex);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexEnvi(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_BLEND);
		GL30.glGenerateMipmap(GL_TEXTURE_2D);

		glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, image.getWidth(),
				image.getHeight(), 0, GL_RGBA, GL_UNSIGNED_BYTE, buffer);

		return tex;
	}

	private static ByteBuffer byteBufferFromBufferedImage(BufferedImage image) {
		// The following boilerplate code found from BufferedImage
		// To ByteBuffer was adapted from
		// http://www.java-gaming.org/index.php?topic=25516.0

		int[] pixels = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0,
				image.getWidth());

		ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth()
				* image.getHeight() * 4);

		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				int pixel = pixels[y * image.getWidth() + x];
				buffer.put((byte) ((pixel >> 16) & 0xFF));// red
				buffer.put((byte) ((pixel >> 8) & 0xFF));// green
				buffer.put((byte) (pixel & 0xFF)); // blue
				buffer.put((byte) ((pixel >> 24) & 0xFF));// alpha
			}
		}

		buffer.flip();

		return buffer;
	}


	public static int uploadTexture(String filePath){
		
		if (textureIdMap.containsKey(filePath)) {
			return textureIdMap.get(filePath);
		}

		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("resources/test.png"));
		} catch (IOException e1) {
			System.out.println("File not found");
			e1.printStackTrace();
		}

		int texID;
		texID = uploadTexture(image);

		textureIdMap.put(filePath, texID);

		return texID;
	}
}
