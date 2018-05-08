import java.awt.*;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class Tutorial_Prep extends GraphicsPane{
	private WindowApplication program;
	private GImage background;
	public static final String IMG_PATH = "imgs/";
	public static final String SONG_PATH = "wavs/";
	private GImage playGameButton;
	private GLabel message;
	private GLabel message2;
	private GLabel message3;
	private GLabel message4;
	private GLabel message5;
	private GImage backButton;
	
	
	
	public Tutorial_Prep(WindowApplication app){
		this.program = app;
		background = new GImage(IMG_PATH + "background.png");
		playGameButton = new GImage(IMG_PATH + "playGameButton.png");
		message = new GLabel("Welcome to Jimenez Click DJ! The most exciting game", 50, 50);
		message.setFont(new Font("Ariel", Font.BOLD, 28));
		message.setColor(Color.YELLOW);
		message2 = new GLabel("you will ever play! You will be taken through a short", 50, 90);
		message2.setFont(new Font("Ariel", Font.BOLD, 28));
		message2.setColor(Color.YELLOW);
		message3 = new GLabel("tutorial that will teach you how to play. To play the", 50, 130);
		message3.setFont(new Font("Ariel", Font.BOLD, 28));
		message3.setColor(Color.YELLOW);
		message4 = new GLabel("game, go back to main menu and go to play game.", 50, 170);
		message4.setFont(new Font("Ariel", Font.BOLD, 28));
		message4.setColor(Color.YELLOW);
		message5 = new GLabel("ENJOY!",300, 300);
		message5.setFont(new Font("Ariel", Font.BOLD, 70));
		message5.setColor(Color.YELLOW);
		backButton = new GImage(IMG_PATH + "backButton.png");
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(playGameButton, 470, 450);
		program.add(message);
		program.add(message2);
		program.add(message3);
		program.add(message4);
		program.add(message5);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(playGameButton);
	}
	
	public void mousePressed(MouseEvent e){
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == playGameButton){
			program.switchToTutorialPane();
		}
		else if(obj == backButton){
			program.switchToStartMenu();
		}
	}

}
