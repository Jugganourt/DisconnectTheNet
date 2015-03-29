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
	
	public NodeButton(String url, Node node, int x, int y) {
		super(x, 600 - y, 20, 20, "resources/dot.png", "resources/dot.png");
		
		this.url = url;
		this.node = node;
		urlImage = Renderer.uploadTextAsTexture(url, new Font("Verdana", Font.PLAIN, 8));
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
		if (isMouseOver()) {
			Renderer.drawTextureRectangle(onMouseOverID, x - width/2, y- height / 2, width, height);
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

	public Node getNode() {
		return node;
	}

}
