import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class Game {
    public static final String TITLE = "Escape the Black Flag!";
    public static final int KEY_INPUT_SPEED = 10;
    private static final int HEIGHT = 500;
    private static final int WIDTH = 500;
    private Sprite ship;
    private Sprite drone;
    private Sprite cannonball;
    private Sprite boss;
    private GraphicsContext gc;
    private DisplayGraphics display;
    private CheckState state;


    
 
    
    public String getTitle () {
        return TITLE;
    }
    
    public void setUp(Stage stage){
    	
        Group root = new Group();
        Scene scene = new Scene (root,HEIGHT,WIDTH);
        stage.setScene(scene);
       
        
        Canvas canvas = new Canvas( HEIGHT, WIDTH );
        root.getChildren().add( canvas );
             
        gc = canvas.getGraphicsContext2D();
        ship = new Sprite();
        ship.setFileName("ship.png");
        drone = new Sprite();
        cannonball = new Sprite();
        cannonball.setFileName("cannonball.png");
        boss = new Sprite();
        boss.setFileName("boss.png");
        //creates and sets file names for instances of the Sprite class
        display = new DisplayGraphics();
        state = new CheckState();
        //instances of other classes
   
         
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode())); 
        
        stage.show();
    }

    public void step (double elapsedTime) {
    	//Clear all graphics from screen
    	display.refreshScreen(gc, HEIGHT, WIDTH);
    	//checks if player has won
    	if(!state.checkForAdvanceStage()){
    		//checks if player has lost
    		if(!state.checkForGameOver(gc)){
    	
    			if(!state.checkIfPaused()){
       
    				ship.updatePosition(gc);   
    				cannonball.updateCannonballs(gc); 
    					if(drone.updateDrones(gc)){  //if a drone gets past player, decrease their lives
    						state.decreaseLives();
    					}
    					if(drone.isHit(gc,cannonball.getCannonBalls())){
    						state.increaseScore();
    					}
    					state.showDisplays(gc); //displays score and lives
    			}
    		}
    	}else{ //if the player advances, this is the start of a boss level game loop
    		if(!state.checkForGameOver(gc)){
    			if(!state.checkIfPaused()){
    				ship.updatePosition(gc);
    				cannonball.updateCannonballs(gc);
    				
    				boss.addBoss(gc);
    				state.showDisplays(gc);
    			}
    		}
    	}
    }

    private void handleKeyInput (KeyCode code) {
        switch (code) {
            case RIGHT:
            	ship.inBounds();
                ship.setX(ship.getX() + KEY_INPUT_SPEED);
            	
                break;
            case LEFT:
            	ship.inBounds();
            	ship.setX(ship.getX() - KEY_INPUT_SPEED);
            	
                break;
            case UP:
            	ship.inBounds();
            	ship.setY(ship.getY() - KEY_INPUT_SPEED);
            	
                break;
            case DOWN:
            	ship.inBounds();
                ship.setY(ship.getY() + KEY_INPUT_SPEED);
            	
                break;
            case SPACE:  //creates two new sprites, adds them to a arraylist
            	Sprite c = new Sprite();
            	c.setFileName("cannonball.png");
            	cannonball.addCannonball(c, ship.getX(), ship.getY());
            	Sprite c2 = new Sprite();
            	c2.setFileName("cannonball.png");
            	cannonball.addCannonball(c2, ship.getX()+64, ship.getY());

                break;
            case P:
            	state.togglePaused();
            	
                break;
            case I:
            	state.toggleInvincible();
            	
                break;
            case Q:
            	drone.setDroneSpeed(drone.getDroneSpeed()-1);
                break;
            case W:
            	drone.setDroneSpeed(drone.getDroneSpeed()+1);
                break;
                
            default:
        }
    }
}
