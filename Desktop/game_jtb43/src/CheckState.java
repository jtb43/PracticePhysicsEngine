import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class CheckState{
	
	private int Score = 0;
	private int winningScore = 500; //the score the player must reach in order to advance
	private int Lives  = 3;
	private boolean paused = false;
	private boolean invincible = false;
	DisplayGraphics display = new DisplayGraphics();
	
	/**
	 * Returns true if player reaches necessary score to advance levels
	 * @return
	 */
	public boolean checkForAdvanceStage(){
		return(Score >= winningScore );
	}
	/**
	 * Returns true if player has lost all lives
	 * @return
	 */
	public boolean checkForGameOver(GraphicsContext gc){
		if (Lives == 0){
			display.gameOver(gc);
			return true;
		}
		return false;
	}
	
	
	public boolean checkIfPaused(){
		return paused;
	}
	
	public void togglePaused(){
		paused = !paused;
		
	}
	public boolean checkIfInvincible(){
		return invincible;
	}
	
	public void toggleInvincible(){
		invincible = !invincible;
		
	}
	
	public void newScreen(GraphicsContext gc, int h, int w){ 
		display.refreshScreen(gc, h, w);
		showDisplays(gc);
	}
	
	public void decreaseLives(){
		if(!invincible){
			Lives--;
		}
	}
	public void increaseScore(){
		Score = Score+10;
	}
	
	public void showDisplays(GraphicsContext gc){
		display.displayLives(gc, Lives);
		display.displayScore(gc, Score);
	}
	
	
	
	
}