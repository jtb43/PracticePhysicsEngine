
/**
 * Physics Engine Class
 * Handles calculating/assigning new positions based on positional attributes and movement vectors
 * Receives Actor object via movement specific methods (ie moveRight, jump)
 * These methods then apply a set force that affects and updates the Actor's 
 * position and velocity variables
 * 
 * @author justinbergkamp
 */


public class Engine {
	
	
	//These Variable values are arbitrary, chosen by trial/error
	private int    timeStep         =  1;    //Arbitrary timeStep, will be set to the time provided by step()
	private double friction         = -.05;  //Horizontal acceleration dampening (friction) coefficient
	private double gravity          = .11 ;  //Falling acceleration coefficient
	private double maxHorizVelocity = 50;    //maximum horizontal velocity
	private double maxVertVelocity  = -50;   //maximum vertical velocity 
	private double horizontalForce  = 5;     //Force applied to Actors on horizontal movements
	private double jumpForce        = -5;    //Vertical Force applied to Actors on jump movements
	private double floorHeight      =  300;  
	
	public Engine(){
		
	}
	
	
	/**
	 * This method updates a given velocity by applying a given force
	 * @param velo
	 * @param force
	 * @return updated velo
	 */
	private double applyForce(double velo, double force){  //Applies a force(acceleration because mass =1) to a velocity
		double nextVelo = velo + force*timeStep;
		return nextVelo;
	}
	
	/**
	 * This method updates a given position by adding a velocity value scaled by a timeStep
	 * @param pos
	 * @param velo
	 * @return updated position
	 */
	private double changePos(double pos, double velo){
		double nextPos = pos + velo*timeStep;
		return nextPos;
	}
	
	
	/**
	 * Only want friction to occur on the ground, only want gravity to apply in the air
	 * @param a
	 * @param forceX
	 * @param forceYupward
	 * @param forceYdownward
	 * @param friction
	 */
	private void update(Actor a,double forceX, double forceYupward, double forceYdownward, double friction){
		double xVelo     = a.getXVelo();
		double yVelo     = a.getYVelo();
		double xPos      =  a.getXPos();      
		double yPos      =  a.getYPos();
		double nextHorzVelo;
		double nextVertVelo;
		double nextXPos;
		double nextYPos;
		
		nextHorzVelo = xVelo;  
		
		if (!a.isInAir()) {       //If the Actor is not in the air, gravity is null
			forceYdownward = 0;
		}else{
			friction       = 0;
			forceX         = 0;
			forceYupward   = 0;
		}
				

		nextVertVelo = applyForce(yVelo, forceYupward);           			 // Apply  y force from movement action to y velocity
		nextVertVelo = applyForce(nextVertVelo, forceYdownward);    		//Apply gravitational force		
		
		nextYPos     = changePos(yPos, nextVertVelo);                       //Update y position with y velocity
		nextVertVelo = maxLimit(nextVertVelo, maxVertVelocity);
			
//		if(nextYPos > floorHeight){                   						 //Collision detection for the actor and the ground
//			nextYPos = floorHeight;											//TODO: delete this if statement after the floor is implemented as an actor
//			nextVertVelo = 0;
//			a.setInAir(false);
//		}
		
		nextHorzVelo = applyForce(xVelo, forceX); 							// Apply  y force from movement action to y velocity
		nextHorzVelo = applyForce(nextHorzVelo, (friction*(nextHorzVelo))); //Apply frictional force
		nextXPos  = changePos(xPos,nextHorzVelo);
		nextHorzVelo = maxLimit(nextHorzVelo, maxHorizVelocity);
		setValues(a,  nextHorzVelo,  nextVertVelo,  nextXPos,  nextYPos );	
	}
	
	/**
	 * Sets position and velocity variables for an Actor
	 * 
	 * @param a
	 * @param nextHorzVelo
	 * @param nextVertVelo
	 * @param nextXPos
	 * @param nextYPos
	 */
	private void setValues(Actor a, double nextHorzVelo, double nextVertVelo, double nextXPos, double nextYPos){
		a.setXVelo(nextHorzVelo);
		a.setYVelo(nextVertVelo);
		a.setXPos(nextXPos);
		a.setYPos(nextYPos);	
	}
	
	private double maxLimit(double vector, double limit){
		double v = Math.abs(vector);
		if( v > Math.abs(limit)){
			return limit;
		}
		return vector;
	}
	
	//These methods correspond to Actions that call them
	//They differ in the force applied to the Actor
	
	public void moveRight(Actor a1) {
		update(a1,horizontalForce, 0, gravity, friction);
	}

	public void moveLeft(Actor a1) {
		update(a1,-horizontalForce, 0, gravity, friction); //Do we still want friction to be a variable in the actor?
	}
	
	public void jump(Actor a1){
		update(a1,0,jumpForce, gravity, friction);
	}
	
	//gliding methods for when force and gravity aren't applied
	
	public void glideRight(Actor a1) {
		a1.setXVelo(5);
		//a1.setXPos(a1.getXPos()+5);
	}

	public void glideLeft(Actor a1) {
		a1.setXPos(a1.getXPos()-.5);
	}
	
	public void glideUp(Actor a1 ){
		a1.setYPos(a1.getYPos()-5);
	}
	
	public void tick(Actor a1) {
		update(a1,0.0,0.0, gravity, friction);
	}
	
	public void staticHorizontalCollision(Actor a1, Actor a2) {
		if(a1.getXVelo() != 0){     					//If the object is moving 
			if(a1.getXPos() <  a2.getXPos()){ 			 //if the collision is occuring on the left side
				a1.setXPos(a2.getXPos()-a1.getBounds().getWidth());  //Offset x value to the left
			}else{ 										//If collision is happening from right
				a1.setXPos(a2.getBounds().getMaxX());	//Offset x value to the right		
			}
			a1.setXVelo(0);                             //Stop movement
		}
	}

	public void staticVerticalCollision(Actor a1, Actor a2) {
		if(a1.getYVelo() != 0){     					//If the object is moving 
			if(a1.getYPos() <= a2.getYPos()){ 			 //if the collision is occuring on the top side
				a1.setYPos(a2.getYPos()-a1.getBounds().getWidth());  //Offset y value up
			}else{ 										//If collision is happening from right
				a1.setYPos(a2.getBounds().getMaxY());	//Offset x value to the right		
			}
			a1.setYVelo(0);                             //Stop movement
		}
	}
	
	public void elasticVerticalCollision(Actor a1, Actor a2){
		staticVerticalCollision(a1,a2);
		a1.setYVelo(-5);
	}

	public void elasticHorizontalCollision(Actor a1, Actor a2){
		staticHorizontalCollision(a1,a2);
		a1.setXVelo(-3);
	}
	
}
	

