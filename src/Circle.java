import acm.graphics.GOval;

public class Circle {
	public static final String IMG_PATH = "imgs/";
	double xCord;
	double yCord;
	
	public Circle() {
		xCord = 0;
		yCord = 0;
	}
	public void setCircle(GOval circle){
		circle.setLocation(xCord, yCord);
	}
	public void setX(double cord){
		xCord = cord;
	}
	public void setY(double cord){
		yCord = cord;
	}
	public double getX(){
		return xCord;
	}
	public double getY(){
		return yCord;
	}
}