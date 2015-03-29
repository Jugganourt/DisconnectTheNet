package engine;

import graphics.Renderer;
import org.lwjgl.opengl.Display;


public class Engine {
	private boolean requestClose;
	private GameStateManager gsm;
	
	public Engine(){
		this.requestClose = false;
		this.gsm = new GameStateManager();
		
	}
	
	
	
	public void input(){
		gsm.input();
	}
	
	public void update(){
		gsm.update();
	}
	
	public void render(){
		Renderer.clear();
		gsm.render();
		Display.update();
	}
	
	public boolean isRequestingClose(){
		return this.requestClose || Display.isCloseRequested();
	}
}
