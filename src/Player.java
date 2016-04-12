import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Player {

	private int screenSize = 1200;
    private Scene myScene;
    private List<Actor> actors;
    private Engine myEngine;
    private Actor a1;
    //private Actor a2;
    private CollisionDetection myCollisionDetector;
    private Group root;
    
    
    public void setUp(Stage stage){
        root = new Group();
        myScene = new Scene (root,screenSize,screenSize, Color.BLACK);
        stage.setScene(myScene);
        myEngine = new Engine();
        myCollisionDetector = new CollisionDetection(myEngine,this);
        actors = new ArrayList<Actor>();
        
        Image image = new Image("circ.png");
        a1 = new Actor(400,300, image,"green");
        
        for(int count = 0 ; count < 6; count++){
        	 Image image2 = new Image("red_circ.png");
             Actor a2 = new Actor(500,300, image2,"red");
             actors.add(a2);
        }
       
        
        for(int count = 0 ; count< 10; count++){
        	Image i  = new Image("floorTile.png");
        	Actor a = new Actor(50*count,400,i,"floor");
        	actors.add(a);
        }
        
        actors.add(a1);
        //actors.add(a2);
        for(Actor a: actors){
        	root.getChildren().add(a.getImageView());
        }
        
        
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        
        stage.show();
    }

	private void handleKeyInput(KeyCode key) {
		switch (key) {
        	case RIGHT:
        		myEngine.moveRight(a1);
   
        		break;
        	case LEFT:
        		myEngine.moveLeft(a1);
        		
        		break;
        		
        	case SPACE:
        		myEngine.jump(a1);
        		a1.setInAir(true);
        		break;
        	case W:
        		myEngine.glideUp(a1);
        		break;
        	case A:
        		myEngine.glideLeft(a1);
        		break;
        		
        	case D:
        		myEngine.glideRight(a1);
        		break;
        	default:
        		break;
		}
		step(1);
	}

	

	public Object step(double secondDelay) {
		System.out.print(a1.isInAir()+"\n");
		myEngine.tick(a1);
		myCollisionDetector.detection(actors);
		return null;
	}

	public void destroy(Actor a22) {
		System.out.print("Actor "+a22.getName()+" was destroyed");
		//actors.remove(a22);
		root.getChildren().remove(a22);
		
	}
	
}