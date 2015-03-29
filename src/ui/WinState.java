package ui;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import engine.GameState;
import engine.GameStateManager;
import graphics.Renderer;

public class WinState implements GameState {

	private int backgroundID;
	private int testID;
	private int testW;
	private int testH;
	
	private boolean mouseDown;

	private GameStateManager gsm;

	private Button play;
	private Button exit;

	private int tick = 0;

	private ArrayList<Button> buttons;

	public WinState(GameStateManager gsm, int lives) {
		this.gsm = gsm;
		buttons = new ArrayList<Button>();
		backgroundID = Renderer.uploadTexture("resources/mainBackground.png");
		BufferedImage tps = Renderer.uploadTextAsTexture("You Won! With " + lives + " lives remaining.", new Font("Verdana", Font.BOLD, 28));
		int testID = Renderer.uploadTexture(tps);
		testW = tps.getWidth();
		testH = tps.getHeight();
		
		
		
		// testID = Renderer.uploadTextAsTexture("helloworld", new Font
		// ("Garamond", Font.BOLD , 11));
		
		exit = new Button(300, 325, 200, 100, "resources/quit.png",
				"resources/quitMO.png");
	}

	@Override
	public void input() {
		exit.input();

		
		if (exit.isClicked()) {
			System.exit(0);
		}
	}

	@Override
	public void update() {
		Random ran = new Random();

	}

	@Override
	public void render() {
		Renderer.drawTextureRectangle(backgroundID, 0, 0, 800, 700);
		Renderer.drawTextureRectangle(testID, 400 - testW/2, 200, testW, testH);
		
		exit.render();
	}

	@Override
	public void destroy() {

	}

}
