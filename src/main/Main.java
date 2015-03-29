package main;

import engine.Engine;
import graphics.Renderer;

public class Main {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT =700;
	public static final int UPS = 60;
	public static final long NPU = 1000000000 / 60;
	
	public static void main(String[] args) {
		
		//Init window and renderer
		Renderer.createWindow();
		Renderer.init();
		Renderer.reshape();
		
		Engine engine = new Engine();
		
		System.out.println("Welcome to Disconnect The Net!");
		
	
		
		long timeP = System.nanoTime();
		long lagAcc = 0;
		
		while (!engine.isRequestingClose()) {
			
			long timeC = System.nanoTime();
			long timeF = timeC - timeP;
			timeP = timeC;
			
			lagAcc += timeF;

			engine.input();
			
			while (lagAcc >= NPU) {
				engine.update();
				lagAcc -= NPU;
			}
			engine.render();
		}
		Renderer.destroy();
	}
	
}
