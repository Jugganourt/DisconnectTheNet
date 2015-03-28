package engine;

public interface GameState {

	public void input();
	public void update();
	public void render();
	public void destroy();
	
}
