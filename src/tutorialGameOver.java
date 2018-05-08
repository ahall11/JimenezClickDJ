import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class tutorialGameOver extends GraphicsPane{
	private WindowApplication program;
	private GImage background;
	private GImage playGameButton;
	public static final String IMG_PATH = "imgs/";
	private GImage back_to_start;
	private GLabel message;
	private GLabel message2;
	
	public tutorialGameOver(WindowApplication app){
		program = app;
		playGameButton = new GImage(IMG_PATH + "playGameButton.png");
		back_to_start = new GImage(IMG_PATH + "StartMenuButton.png");
		background = new GImage(IMG_PATH + "background.png");
		message = new GLabel("CONGRATULATIONS!", 205, 200);
		message.setFont(new Font("Ariel", Font.BOLD, 40));
		message.setColor(Color.YELLOW);
		message2 = new GLabel("You now know how to play!", 165, 250);
		message2.setFont(new Font("Ariel", Font.BOLD, 38));
		message2.setColor(Color.YELLOW);
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(playGameButton, 256, 300);
		program.add(back_to_start, 256, 390);
		program.add(message);
		program.add(message2);
	}
	
	@Override
	public void hideContents() {
		program.remove(playGameButton);
		program.remove(background);
		program.remove(back_to_start);
		program.remove(message);
		program.remove(message2);
	}
	
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == playGameButton){
			program.switchToGameSettingsPane();
		}
		if(obj == back_to_start){
			program.switchToStartMenu();
		}
	}
}
