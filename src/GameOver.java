import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class GameOver extends GraphicsPane  {
	private WindowApplication program; 
	public static final String IMG_PATH = "imgs/";
	private GImage newSong;
	private GImage quit;	
	public int SCORE;
	private GLabel scoreMessage;
	private GLabel scoreMessage2;
	private GLabel scoreDisplay;
	private GImage background;
	Game game;
	
	public GameOver(WindowApplication app) {
		this.program = app;
		SCORE = 0;
		background = new GImage(IMG_PATH + "background.png");
		newSong = new GImage(IMG_PATH + "newSongButton.png");
		quit = new GImage(IMG_PATH + "quitButton.png");
		scoreMessage = new GLabel("Congratulations!!", 175, 150);
		scoreMessage2 = new GLabel("Your score was ", 190, 230);
		scoreDisplay = new GLabel("" + SCORE, 365, 292);
		scoreMessage.setFont(new Font("", Font.BOLD, 60));
		scoreMessage.setColor(Color.YELLOW);
		scoreMessage2.setFont(new Font("", Font.BOLD, 60));
		scoreMessage2.setColor(Color.YELLOW);
		scoreDisplay.setFont(new Font("Game Over Regular", Font.BOLD, 80));
		scoreDisplay.setColor(Color.YELLOW);
	}
	
	public void setScore(int s){
		SCORE = s;
		scoreDisplay.setLabel("" + SCORE);
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(scoreMessage);
		program.add(scoreMessage2);
		program.add(scoreDisplay);
		program.add(newSong, 256, 300);
		program.add(quit, 256, 390);
		
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(scoreMessage);
		program.remove(scoreMessage2);
		program.remove(scoreDisplay);
		program.remove(newSong);
		program.remove(quit);
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == newSong){
			program.switchToGameSettingsPane();
		}
		if(obj == quit){
			program.switchToStartMenu();
		}
	}
}
