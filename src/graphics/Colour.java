package graphics;

public class Colour {
	
	public static Colour WHITE = new Colour(1.0f, 1.0f, 1.0f);
	public static Colour BLACK = new Colour(0.0f, 0.0f, 0.0f);
	public static Colour RED = new Colour(1.0f, 0.0f, 0.0f);
	public static Colour GREEN = new Colour(0.0f, 1.0f, 0.0f);
	public static Colour BLUE = new Colour(0.0f, 0.0f, 1.0f);
	
	public int r;
	public int g;
	public int b;
	
	public Colour(int r, int g, int b){
		this.r = r;
		this.g = g;
		this.b = b;
	}
	
	public Colour(float r, float g, float b){
		this.r = (int) (r * 255);
		this.g = (int) (g * 255);
		this.b = (int) (b * 255);
	}
}
