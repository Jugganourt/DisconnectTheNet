package engine;

import graphics.Renderer;
import org.lwjgl.opengl.Display;

import ui.Button;

public class Engine {
	private Button b;
	private boolean requestClose;
	int id = 0;
	
	public Engine(){
		this.requestClose = false;
		b = new Button(100, 100, 40, 40, "/resources/test.png","/resources/test2.png");
	}
	
	
	
	public void input(){
		
	}
	
	public void update(){
		
	}
	
	public void render(){
		Renderer.clear();

		
		//Renderer.drawTextureRectangle(id, Mouse.getX() - 10,  Main.WINDOW_HEIGHT - (Mouse.getY() + 10), 20, 20);
		
		
		b.render();
		 
		Display.update();
	}
	
	public boolean isRequestingClose(){
		return this.requestClose || Display.isCloseRequested();
	}
}
