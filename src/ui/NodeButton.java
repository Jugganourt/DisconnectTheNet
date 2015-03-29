package ui;

import graphics.Colour;
import graphics.Renderer;

import java.awt.Font;
import java.awt.image.BufferedImage;

import main.Main;
import map.Node;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class NodeButton extends Button {

	private String url;
	private Node node;
	private int urlID;
	private BufferedImage urlImage;
	private boolean focussed;
	private int focussedTexId;
	private int dangerTexId;
	
	private int opacity = 255;
	private boolean remove;
	
	private boolean danger;
	
	public NodeButton(String url, Node node, int x, int y) {
		super(x, 600 - y, 25, 25, "resources/dot.png", "resources/dot.png");
		this.focussed = false;
		this.focussedTexId = Renderer.uploadTexture("resources/dotR.png");
		this.dangerTexId = Renderer.uploadTexture("resources/dotO.png");
		this.url = url.substring(0, url.length()-4);
		this.node = node;
		this.remove = false;
		this.danger = false;
		urlImage = Renderer.uploadTextAsTexture(this.url, new Font("Verdana", Font.PLAIN, 12));
		urlID = Renderer.uploadTexture(urlImage);
		
	}
	
	public boolean isMouseOver(){
		if (Mouse.getX() > x - width/2 && Mouse.getX() < x + width/2
				&& Main.WINDOW_HEIGHT - Mouse.getY() > y - height/2
				&& Main.WINDOW_HEIGHT - Mouse.getY() < y + height/2) {
			return true;
		}
		return false;
	}
	
	public void render(){
		
		
		if (focussed) {
			Renderer.drawTextureRectangleOp(focussedTexId, x - width/2, y- height / 2, width, height, (float)(opacity / 255.0));
			
		} else if (isMouseOver()) {
			Renderer.drawTextureRectangleOp(onMouseOverID, x - width/2, y- height / 2, width, height, (float)(opacity / 255.0));
		} else if(danger){
			Renderer.drawTextureRectangleOp(dangerTexId, x- width/2 , y - height / 2 , width, height, (float)(opacity / 255.0));
		} else {
			Renderer.drawTextureRectangleOp(normalID, x- width/2 , y - height / 2 , width, height, (float)(opacity / 255.0));
		}
		
		Renderer.drawRectangle(x - urlImage.getWidth()/2, y + height/2,  urlImage.getTileWidth(), urlImage.getHeight(), new Colour(30, 30, 30));
		Renderer.setColour(new Colour(255, 255, 255));
		Renderer.drawTextureRectangleOp(urlID, x - urlImage.getWidth()/2, y + height/2, urlImage.getTileWidth(), urlImage.getHeight(), (float)(opacity / 255.0));
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public int getX() {
		return x;
	}

	public void setX(int i) {
		this.x = i;
		
	}
	
	public void setDanger(boolean b){
		this.danger = b;
	}

	public Node getNode() {
		return node;
	}
	
	public void setFocussed(boolean b){
		this.focussed = b;
	}

	public void setY(int i) {
		this.y = i;
		
	}

	public int getY() {
	return this.y;
	}

	public void fade() {
		if (Math.random() < 0.4) {
			this.opacity = (int) (opacity - 0.05 * opacity);
		}
		
		System.out.println("Opacity " + opacity);
		if (opacity < 10) {
			remove = true;
		}
		
	}
	
	public boolean needsRemoving(){
		return remove;
	}

}
