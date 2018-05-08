import java.awt.*;

import acm.graphics.*;
import acm.program.*;

@SuppressWarnings("serial")
public class WindowApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 600;
	
	private StartMenu startMenuPane;
	private GameSettings gameSettingsPane;
	private Tutorial tutorialPane;
	private Game gamePane;
	private Tutorial_Prep tutorialPrepPane;
	private tutorialGameOver tutorialGameOverPane;
	private GameOver gameOverPane;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}
	
	public void run() {
		startMenuPane = new StartMenu(this);
		gameSettingsPane = new GameSettings(this);
		tutorialPane = new Tutorial(this);
		gamePane = new Game(this);
		tutorialPrepPane = new Tutorial_Prep(this);
		gameOverPane = new GameOver(this);
		tutorialGameOverPane = new tutorialGameOver(this);
		
		setupInteractions();
		switchToStartMenu();
		//switchToGamePane();
	}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	private void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	public void switchToTutorialGameOverPane(){
		switchToScreen(tutorialGameOverPane);
	}
	
	public void switchToTutorialPrepPane(){
		switchToScreen(tutorialPrepPane);
	}
	public void switchToStartMenu() {
		switchToScreen(startMenuPane);
	}
	
	public void switchToGameSettingsPane(){
		switchToScreen(gameSettingsPane);
	}
	
	public void switchToTutorialPane(){
		switchToScreen(tutorialPane);
	}
	
	public void switchToGamePane(Settings s){
		switchToScreen(gamePane);
		gamePane.setSettings(s);
	}
	
	public void switchToGameOverPane(int s){
		switchToScreen(gameOverPane);
		gameOverPane.setScore(s);
	}
	
}
