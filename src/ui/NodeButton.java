package ui;

import java.awt.Font;
import java.awt.image.BufferedImage;

import org.lwjgl.input.Mouse;

import graphics.Colour;
import graphics.Renderer;
import main.Main;
import map.Node;

public class NodeButton extends Button {

	private String url;
	private Node node;
	private int urlID;
	private BufferedImage urlImage;
	private boolean focussed;
	private int focussedTexId;
	private int dangerTexId;
	
	private boolean danger;
	
	public NodeButton(String url, Node node, int x, int y) {
		super(x, 600 - y, 25, 25, "resources/dot.png", "resources/dot.png");
		this.focussed = false;
		this.focussedTexId = Renderer.uploadTexture("resources/dotR.png");
		this.dangerTexId = Renderer.uploadTexture("resources/dotO.png");
		this.url = url.substring(0, url.length()-4);
		this.node = node;
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
			Renderer.drawTextureRectangle(focussedTexId, x - width/2, y- height / 2, width, height);
			
		} else if (isMouseOver()) {
			Renderer.drawTextureRectangle(onMouseOverID, x - width/2, y- height / 2, width, height);
		} else if(danger){
			Renderer.drawTextureRectangle(dangerTexId, x- width/2 , y - height / 2 , width, height);
		} else {
			Renderer.drawTextureRectangle(normalID, x- width/2 , y - height / 2 , width, height);
		}
		
		Renderer.drawRectangle(x - urlImage.getWidth()/2, y + height/2,  urlImage.getTileWidth(), urlImage.getHeight(), new Colour(30, 30, 30));
		Renderer.setColour(new Colour(255, 255, 255));
		Renderer.drawTextureRectangle(urlID, x - urlImage.getWidth()/2, y + height/2, urlImage.getTileWidth(), urlImage.getHeight());
		
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

}
