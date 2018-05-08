import java.awt.event.MouseEvent;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import acm.graphics.*;

public class StartMenu extends GraphicsPane {
	private WindowApplication program; //you will use program to get access to all of the GraphicsProgram calls
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	public static final String IMG_PATH = "imgs/";
	private GImage background;
	private GImage logo;
	private GImage playGameButton;
	private GImage tutorialButton;
	private GImage quitButton;
	
	public StartMenu(WindowApplication app) {
		this.program = app;
		background = new GImage(IMG_PATH + "background.png");
		logo = new GImage(IMG_PATH + "logo.png");
		playGameButton = new GImage(IMG_PATH + "playGameButton.png");
		tutorialButton = new GImage(IMG_PATH + "tutorialButton.png");
		quitButton = new GImage(IMG_PATH + "quitButton.png");
	}
	@Override
	public void showContents() {
		program.add(background);
		program.add(logo);
		program.add(playGameButton,WINDOW_WIDTH/2 - 144,WINDOW_HEIGHT/2 - 42);
		program.add(tutorialButton,WINDOW_WIDTH/2 - 144,WINDOW_HEIGHT/2 - 42 + 100);
		program.add(quitButton,WINDOW_WIDTH/2 - 144,WINDOW_HEIGHT/2 - 42 + 200);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(logo);
		program.remove(playGameButton);
		program.remove(tutorialButton);
		program.remove(quitButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == playGameButton){
			program.switchToGameSettingsPane();
		}
		if(obj == tutorialButton){
			program.switchToTutorialPrepPane();
		}
		if(obj == quitButton){
			System.exit(0);//exits program
		}
	}
}
