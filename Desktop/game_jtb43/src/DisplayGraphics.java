import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class DisplayGraphics{

	private Image water= new Image("tex_Water.jpg");
	private int displayX = 300; //points to which the texts are added
	private int displayY1 = 400;
	private int displayY2 = 420;
	
	
	 
	public void gameOver(GraphicsContext gc){   
		gc.setFont(new Font(60));
		gc.setFill(Color.RED);
		gc.fillText("GAME OVER!",70 , 200);
		
	}
	public void win(GraphicsContext gc){
		gc.setFont(new Font(60));
		gc.setFill(Color.GREEN);
		gc.fillText("You Win!",70 , 200);
		
	}
	
	public void refreshScreen(GraphicsContext gc, int h, int w){
		gc.clearRect(0, 0, h, w);
		gc.drawImage(water, 0,0); //covers screen with image of water texture
		
	}
	
	public void displayLives(GraphicsContext gc, int Lives) {
		gc.setFont(new Font(20));
		gc.setFill(Color.BLACK);
		gc.fillText("Lives Remaining: "+Lives,displayX , displayY1);
		
	}
	public void displayScore(GraphicsContext gc, int Score) {
		gc.setFont(new Font(20));
		gc.setFill(Color.BLACK);
		gc.fillText("Score: "+Score,displayX , displayY2);
		
	}	
}