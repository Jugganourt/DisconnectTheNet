package ui;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

import engine.GameState;
import engine.GameStateManager;
import graphics.Renderer;

public class LoseState implements GameState {

	private int backgroundID;
	private int testID;
	
	private boolean mouseDown;

	private GameStateManager gsm;

	private Button play;
	private Button exit;

	private int tick = 0;

	private ArrayList<Button> buttons;
	private Button text;

	public LoseState(GameStateManager gsm, int lives) {
		this.gsm = gsm;
		buttons = new ArrayList<Button>();
		backgroundID = Renderer.uploadTexture("resources/mainBackground.png");
		
		int testID = Renderer.uploadTexture("resources/lose.png");
		
		
		
		// testID = Renderer.uploadTextAsTexture("helloworld", new Font
		// ("Garamond", Font.BOLD , 11));
		
		exit = new Button(300, 325, 200, 100, "resources/quit.png",
				"resources/quitMO.png");
		text = new Button(300, 200, 200, 100, "resources/lose.png",
				"resources/lose.png");
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


		text.render();
		exit.render();
	}

	@Override
	public void destroy() {

	}

}
