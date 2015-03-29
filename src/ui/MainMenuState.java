package ui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import com.sun.xml.internal.bind.v2.model.annotation.Quick;

import engine.GameState;
import engine.GameStateManager;
import graphics.Renderer;

public class MainMenuState implements GameState {

	private int backgroundID;
	private int testID;
	
	private boolean mouseDown;

	private GameStateManager gsm;

	private Button play;
	private Button exit;

	private int tick = 0;

	private ArrayList<Button> buttons;

	public MainMenuState(GameStateManager gsm) {
		this.gsm = gsm;
		buttons = new ArrayList<Button>();
		backgroundID = Renderer.uploadTexture("resources/mainBackground.png");
		// testID = Renderer.uploadTextAsTexture("helloworld", new Font
		// ("Garamond", Font.BOLD , 11));
		play = new Button(300, 200, 200, 100, "resources/play.png",
				"resources/playMO.png");
		exit = new Button(300, 325, 200, 100, "resources/quit.png",
				"resources/quitMO.png");
	}

	@Override
	public void input() {
		play.input();
		exit.input();
		
		if (play.isClicked()) {
			gsm.pushState(new PlayState(gsm));
		}
		
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
		Renderer.drawTextureRectangle(backgroundID, 0, 0, 800, 600);
		// Renderer.drawTextureRectangle(testID, 0, 0, 200, 100);
		play.render();
		exit.render();
	}

	@Override
	public void destroy() {

	}

}
