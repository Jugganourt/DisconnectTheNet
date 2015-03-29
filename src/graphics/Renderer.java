package graphics;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_MODULATE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_RGBA;
import static org.lwjgl.opengl.GL11.GL_RGBA8;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_ENV_MODE;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_BYTE;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColor3d;
import static org.lwjgl.opengl.GL11.glColor3f;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glTexEnvf;
import static org.lwjgl.opengl.GL11.glTexEnvi;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.opengl.GL11.glViewport;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class Renderer {

	private static Map<String, Integer> textureIdMap;
	
	public static void clearTextures(){
		textureIdMap.forEach((filePath, texID) -> glDeleteTextures(texID));
		textureIdMap.clear();
	}
	
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
			Display.setDisplayMode(new DisplayMode(800, 700));
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
		clearTextures();
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
	
	public static void drawTextureRectangleOp(int id, int x, int y, int width, int height, float op){
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
		
		
		GL11.glColor4f(1f, 1f, 1f, op);
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
	
	
	public static void drawRectangle(int x, int y, int width, int height, Colour c){
		
		int tlX = x;
		int tlY = y;
		int trX = x + width;
		int trY = y;
		int blX = x;
		int blY = y + height;
		int brX = x + width;
		int brY = y + height;
		glColor3f((c.r) / 255f, (c.g) / 255f, (c.b) / 255f);
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
		glColor3f(1.0f, 1.0f, 1.0f );
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);	// Clean the screen and the depth buffer
		glLoadIdentity();	
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
			image = ImageIO.read(new File(filePath));
		} catch (IOException e1) {
			System.out.println("File not found");
			e1.printStackTrace();
		}

		int texID;
		texID = uploadTexture(image);

		textureIdMap.put(filePath, texID);
		
		
		return texID;
	}
	
	public static BufferedImage uploadTextAsTexture(String text, Font font){
		
		FontMetrics fm = new Canvas().getFontMetrics(font);

		final int minimumWidth = 2;

		int width = fm.stringWidth(text) + minimumWidth;
		int height = fm.getHeight();

		final double ALLOWANCE_FOR_CHARACTER_TAILS = 1.5;

		BufferedImage image = new BufferedImage(width,
				(int) (height * ALLOWANCE_FOR_CHARACTER_TAILS),
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics = image.createGraphics();
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

		graphics.setFont(font);
		graphics.setColor(Color.WHITE);
		graphics.drawString(text, 0, height);
		
		return image;
        
	}

	public static void deleteTexture(int texID) {
		glDeleteTextures(texID);
		
	}
	
}
