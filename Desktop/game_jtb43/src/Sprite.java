import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;



public class Sprite {
	private String fileName;
	private double x_value = 200;
	private double y_value = 300;
	private ArrayList<Sprite> ownCannonballs =  new ArrayList<Sprite>();
	private ArrayList<Sprite> bossCannonballs =  new ArrayList<Sprite>();
	private ArrayList<Sprite> drones =  new ArrayList<Sprite>();
	private int cbSpeed = 5;
	private int droneSpeedRight  =  10;
	private int droneSpeedLeft  = -droneSpeedRight;
	private int droneVelo;
	private double WIDTH = 500;
	private double HEIGHT = 500;
	private double BOUND = HEIGHT*(.66);
	private int distanceBetweenDrones = 75;
	private double bossX = (WIDTH/2) - 50;
	
	
	public void setDroneSpeed(int s){
		droneSpeedRight = s;
		droneSpeedLeft = -s;
	}
	

	public int getDroneSpeed() {
		// TODO Auto-generated method stub
		return droneSpeedRight;
	}
	
	public void setFileName(String file){ //sets the file name for the sprite
		fileName = file;
	}
	
	public Image getImage(){              
		Image i = new Image(fileName);
		return i;
	}
	
	public void setX( Double x ){
		x_value = x;
	}
	
	public void setY(Double y){
		y_value = y;
	}
	
	public double getX(){
		return x_value;
	}
	
	public double getY(){
		return y_value;
	}
	
	public void updatePosition(GraphicsContext gc) {
		 gc.drawImage(this.getImage(), this.getX(), this.getY());
		
	}
	/**
	 * Updates and draws positions of all cannonballs
	 * removes cannonball it is off screen
	 * @param gc
	 */
	public void updateCannonballs(GraphicsContext gc) { 
        for(Sprite i:ownCannonballs){
        	double y = i.getY()-(cbSpeed);
        	
        	if( (i.getY()-(cbSpeed)) < 0 ){
        		ownCannonballs.remove(i);
        		break;
        	}else{
        		i.setY(y);
        	}
        	gc.drawImage(i.getImage(), i.getX(), i.getY());
        }
	}
	/**
	 * adds instances of Sprite to an arraylist
	 * only allows 2 cannonballs on the screen at once
	 * @param c
	 * @param x
	 * @param y
	 */
	public void addCannonball(Sprite c, double x, double y){
		if(ownCannonballs.size()<2){
    	c.setX(x);
    	c.setY(y);
    	ownCannonballs.add(c);
		}
	}
	/**
	 * checks is the ship is within the area it's allowed to move in
	 */
	public void inBounds() {
		if  (this.getY() < BOUND ){
			this.setY(BOUND);
		}
		if	(this.getY() +this.getImage().getHeight() > HEIGHT) {
			this.setY(HEIGHT-this.getImage().getHeight());
		}
		if(  (this.getX() > WIDTH)){
			this.setX(0.0);
		}
		if(  (this.getX() < 0)){
			this.setX(WIDTH);
		}

	}
	
	public boolean updateDrones(GraphicsContext gc) {
		
		//if there are no drones on screen, add one
		if(drones.isEmpty()){ 
			createDrones();
		}
        for(Sprite i:drones){
      	
        	double x;
        	if(i.getVelo()>0){ 
        		 x =i.getX()+(droneSpeedRight); //if the drone is moving right, update movement
        	}else{
        		 x =i.getX()+(droneSpeedLeft);
        	}
        	if( (i.getX()) > (WIDTH - (i.getImage().getWidth())) ){ //if drone reaches right side, switch directions
        		i.setVelo(droneSpeedLeft);

        	}if(  (i.getX()<= 0 ) && (i.getVelo()<0)){ //if drone reaches left side, change directions
        		i.setVelo(droneSpeedRight);	

        	}
        	i.setX(x);
        	i.setY(i.getY()+1);
        	gc.drawImage(i.getImage(), i.getX(), i.getY());
        }
        //creates a new drone if there is a large enough gap between them
		if(drones.get(drones.size()-1).getY() > distanceBetweenDrones){ 
			createDrones();
		}
		//removes drones that go off screen, also returns true so LIVES can be decreased
		if(drones.get(0).getY()>HEIGHT){
			drones.remove(0);
			return true;
		}
		return false;
	}

	private void setVelo(int v){
		droneVelo = v;
	}
	
	private int getVelo(){
		return droneVelo;
	}

	private void createDrones() { //creates new drones, adds them to an arraylist
		Sprite d = new Sprite();
		d.setFileName("drone.png");
		d.setX(0.0);
    	d.setY(0.0);
    	d.setVelo(droneSpeedRight);
    	drones.add(d);
	}
	//getter and setter for the arraylist of cannonballs
	public ArrayList<Sprite>  getCannonBalls(){		
		return ownCannonballs;
	}
	
	public void setCannonBalls(ArrayList<Sprite> c){
		 ownCannonballs = c;
	}
	/**
	 * returns true if there is a collision between a drone and a cannonball
	 * @param gc
	 * @param cbs
	 * @return
	 */
	public boolean isHit(GraphicsContext gc, ArrayList<Sprite> cbs){ 
		for(Sprite d : drones){
			double drone_x = d.getX();
			double drone_y = d.getY();
			for(Sprite c : cbs){
				double cb_x = c.getX();
				double cb_y = c.getY();
				if(		(drone_x <= cb_x)   &&
						(drone_x + d.getImage().getWidth()) >= (cb_x + c.getImage().getWidth()) &&
						(drone_y <= cb_y)   &&
						((drone_y + d.getImage().getWidth()) >= (cb_y+c.getImage().getHeight()))
				  ){
					gc.setFill(Color.CRIMSON);
					gc.fillRect(d.getX(), d.getY(), d.getImage().getWidth(), d.getImage().getHeight());
					drones.remove(d);
					cbs.remove(c);
					return true;
				   }
			}
		}
		return false;
	}	
	
	public void addBoss(GraphicsContext gc){
		gc.drawImage(this.getImage(), bossX, 0);
	}
	//This method would have implemented shooting for my boss sprite
	public void bossShooting(GraphicsContext gc){ 
		for(int x = 0; x < WIDTH/5; x++){
			
		}
		
		
		
	}

	
}
