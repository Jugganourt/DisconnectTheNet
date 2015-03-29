package engine;

import java.util.ArrayList;

import ui.MainMenuState;

public class GameStateManager {

	private GameState currState;
	private ArrayList<GameState> states;
	
	
	public GameStateManager(){
		
		states = new ArrayList<GameState>();
		GameState menu = new MainMenuState(this);
		pushState(menu);
	}
	
	public void pushState(GameState state){
		states.add(state);
		currState = state;
	}
	
	public void popState(){
		currState = states.get(states.size()-2);
		states.get(states.size()-1).destroy();
		states.remove(states.size()-1);
	}
	
	public void input(){
		currState.input();
	}
	
	public void update(){
		currState.update();
	}
	
	public void render(){
		
		currState.render();
	}
	
	
}
