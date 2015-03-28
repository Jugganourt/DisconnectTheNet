package engine;

import graphics.Colour;
import graphics.Renderer;
import main.Main;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class Engine {

	private boolean requestClose;
	int id = 0;
	
	public Engine(){
		this.requestClose = false;
		id = Renderer.uploadTexture("/resources/test.png");
	}
	
	
	
	public void input(){
		
	}
	
	public void update(){
		
	}
	
	public void render(){
		Renderer.clear();

		
		Renderer.drawTextureRectangle(id, Mouse.getX() - 10,  Main.WINDOW_HEIGHT - (Mouse.getY() + 10), 20, 20);
		
		
		
		
		Display.update();
	}
	
	public boolean isRequestingClose(){
		return this.requestClose || Display.isCloseRequested();
	}
}
