import javafx.scene.canvas.GraphicsContext;

public class bossLoop{
	
	private DisplayGraphics display;
	

	public void step(DisplayGraphics display, GraphicsContext gc, int h, int w) {
		display.refreshScreen(gc, h, w);
		
	}
	
	
	
}