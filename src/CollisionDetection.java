
import java.util.List;

/**
 * Collision Detection class handles checking for collisions among a list of Actors
 * It also handles resolving said collision should one be found
 * 
 * @author justinbergkamp
 *
 */
public class CollisionDetection {

	Engine myPhysicsEngine;
	Player myPlayer;
	
	public CollisionDetection( Engine physicsEngine, Player player){
		myPhysicsEngine = physicsEngine;
		myPlayer = player;
	}
	
	/**
	 * Called on list of actors in Level to detect any collisions between unique actors
	 * @return List of actors with updated position variables
	 */
	public List<Actor> detection(List<Actor> actors){
		
		
		for (Actor a1 : actors){
			a1.setInAir(true);
			for(Actor a2 : actors){
				if((a1 != a2) ){  //Checks that each actor in the pair is unique
					if(isCollision(a1,a2))
						resolveCollision(a1,a2);
				}
			}
		}
		return actors;
	}
		
	/**
	 * Determines if a collision is occurring by checking for intersecting Bounds. 
	 * 
	 * @param a1
	 * @param a2
	 * @return True = Is Collision, False = No Collision
	 */
	private boolean isCollision(Actor a1, Actor a2){
		return a1.getBounds().intersects(a2.getBounds());
	}
	
	
	private String getCollisionType(Actor a1, Actor a2){
		
		double xOverlap = 0;
		double yOverlap = 0;
		
		if(a1.getBounds().getMaxX() <= a2.getBounds().getMaxX()){
			xOverlap = a1.getBounds().getMaxX() -  a2.getXPos();
		}else{
			xOverlap = a2.getBounds().getMaxX() -  a1.getXPos();
		}
		
		if(a1.getBounds().getMaxY() <= a2.getBounds().getMaxY()){
			yOverlap = a1.getBounds().getMaxY() -  a2.getYPos();
		}else{
			yOverlap = a2.getBounds().getMaxY() -  a1.getYPos();
		}
		
		
		if(xOverlap <= yOverlap){
			return "SideCollision";
		}else{  
			if(a1.getYPos() <= a2.getYPos()){
				a1.setInAir(false);
				return "BottomCollision";
			}else{
				return "TopCollision";
			}
			
		}
	}
	
	private void resolveCollision(Actor a1, Actor a2){
		String collisionType = getCollisionType(a1,a2);
		String triggerString = a1.getName() + collisionType + a2.getName();
		//System.out.print(triggerString+"\n");
		if(triggerString.equals("greenBottomCollisionred")){
			System.out.print(triggerString+"\n");
			myPhysicsEngine.elasticVerticalCollision(a1, a2);
		}
		if(collisionType == "SideCollision"){
			myPhysicsEngine.elasticHorizontalCollision(a1,a2);
		}else if(collisionType == "BottomCollision"){
			myPhysicsEngine.staticVerticalCollision(a1,a2);
		}

	}	
	
}