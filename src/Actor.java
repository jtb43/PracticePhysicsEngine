import javafx.geometry.Bounds;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Actor{

	private ImageView myImageView;
	private double xPos,yPos,xVelo,yVelo;
	private String name;
	private boolean isInAir;
	

	public boolean isInAir() {
		return isInAir;
	}

	public void setInAir(boolean isInAir) {
		this.isInAir = isInAir;
	}

	Actor(double x, double y, Image i, String myname){
		myImageView = new ImageView(i);
		myImageView.setFitHeight(50);
		myImageView.setFitWidth(50);
		setXPos(x);

		setYPos(y);

		setXVelo(0);
		setYVelo(0);
		
		isInAir = false;
		
		name = myname;
		
	}
	
	public ImageView getImageView(){
		return myImageView;
	}
	
	public Bounds getBounds(){
		return getImageView().getLayoutBounds();
	}
	public double getXPos() {
		return xPos;
	}

	public void setXPos(double xPos) {
		getImageView().setX(xPos);
		this.xPos = xPos;
	}

	public double getYPos() {
		return yPos;
	}

	public void setYPos(double yPos) {
		getImageView().setY(yPos);
		this.yPos = yPos;
	}

	public double getXVelo() {
		return xVelo;
	}

	public void setXVelo(double xVelo) {
		this.xVelo = xVelo;
	}

	public double getYVelo() {
		return yVelo;
	}

	public void setYVelo(double yVelo) {
		this.yVelo = yVelo;
	}

	public String getName() {
		return name;
	}
	
}
