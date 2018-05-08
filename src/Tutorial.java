import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;

public class Tutorial extends GraphicsPane {
	private WindowApplication program; //you will use program to get access to all of the GraphicsProgram calls
	private GRect rect;
	private GImage background;
	private GImage circle;
	public static final String IMG_PATH = "imgs/";
	//Audio variables
	public static final String SONG_PATH = "wavs/";//Songs folder path
	AudioInputStream audioStream = null;//Audio stream file we use to stream the music
	File soundFile = null;
	Clip clip;//Clip used to play the songs
	Timer timer;
	private GLabel scoreMessage;
	private GLabel scoreMessage2;
	private GImage scoreLabel;
	public int SCORE;
	private GLabel scoreDisplay;
	private GImage pauseButton;
	private GRect back_shade;
	private GRect p_menu_box;
	private GImage pause_label;
	private GImage resume;
	private GImage back_to_start;
	private long currentPositionInSong;
	private int counter;
	private int counter2;
	private int counter4;
	private int section;
	private int difficulty;
	boolean gameOver1;
	private Settings settings;
	private GImage p_100;
	private GImage p_50;
	private boolean paused;
	//Game over GObjects
	private GImage quit;
	//For tutorial Messages
	private GLabel tutorialM1;
	private GLabel tutorialM2;
	private GLabel tutorialM3;
	private GLabel tutorialM4;
	private int counterForTutorialMsg;
	private boolean m1Done;
	private boolean m2Done;
	private boolean m3Done;
	
		
	public Tutorial(WindowApplication app) {
		program = app;
		rect = new GRect(200, 200, 200, 200);
		rect.setFilled(true);
		background = new GImage(IMG_PATH + "background.png");
		circle = new GImage(IMG_PATH + "circle.png");
		scoreLabel = new GImage(IMG_PATH + "ScoreLabel.png");
		scoreLabel.setLocation(0, 0);
		SCORE = 0;
		scoreDisplay = new GLabel("000", scoreLabel.getWidth(), 0);
		scoreDisplay.setFont(new Font("Game Over Regular", Font.BOLD, 48));
		scoreDisplay.setColor(Color.WHITE);
		pauseButton = new GImage(IMG_PATH + "SmallPauseLabel.png", 0, 572);
		currentPositionInSong = 0;
		settings = new Settings();
		counter = 0; //for displaying circles
		counter2 = 0;
		counter4 = 0;
		section = 1;
		difficulty = 3;
		gameOver1 = false;
		paused = false;
		
		back_shade = new GRect(800, 600);
		back_shade.setFillColor(new Color(0, 0, 0, 100));
		p_menu_box = new GRect(400, 500, 200, 100);
		p_menu_box.setFillColor(Color.BLACK);
		pause_label = new GImage(IMG_PATH + "PauseLabel.png", 328, 120);
		resume = new GImage(IMG_PATH + "resumeButton.png", 256, 200);
		back_to_start = new GImage(IMG_PATH + "StartMenuButton.png", 256, 300);
		//gameOver = new GImage(IMG_PATH + "GameOverLabel.png");
		quit = new GImage(IMG_PATH + "quitButton.png");
		p_100 = new GImage(IMG_PATH + "+100.png");
		p_50 = new GImage(IMG_PATH + "-50.png");
		//gameover screen
		scoreMessage = new GLabel("Congratulations!!", 175, 170);
		scoreMessage2 = new GLabel("Your score was ", 190, 250);
		scoreDisplay = new GLabel("" + SCORE, 350, 295);
		//for Tutorial Messages
		tutorialM1 = new GLabel("Click the circles to increase your score by 100", 0, 50);
		tutorialM2 = new GLabel("Misclicking will deduct 50 points from your score", 0 ,50);
		tutorialM3 = new GLabel("Flow with the music and get the highest score you can!", 0, 50);
		tutorialM1.setFont(new Font("Game Over Regular", Font.BOLD, 36));
		tutorialM1.setColor(Color.YELLOW);
		tutorialM2.setFont(new Font("Game Over Regular", Font.BOLD, 36));
		tutorialM2.setColor(Color.YELLOW);
		tutorialM3.setFont(new Font("Game Over Regular", Font.BOLD, 36));
		tutorialM3.setColor(Color.YELLOW);
		counterForTutorialMsg = 0;
		m1Done = false;
		m2Done = false;
		m3Done = false;
		
		//Initialize clip to empty wav file 
		String songName = (SONG_PATH + "empty.wav");
		soundFile = new File(songName);
		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);
			clip = AudioSystem.getClip();//set song file to clip
			clip.open(audioStream);//open the audio file
			clip.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//retrieve the song file		
		catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		timer = new Timer(1, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//set difficulty
					difficulty = 3;
				//set clip
				if(clip == null){
					playSong();
				}
				//setting section after every 5 circles
				if(counter4 == ((getDelayOfCircle()/difficulty)*5) && clip.isRunning()){
					section = 1 + (int)(Math.random() * ((4-1)+1));
					counter4 = 0;
				}
				//add circle
				if(counter == (getDelayOfCircle()/difficulty) && clip.isRunning()){
					counter = 0;
					addCircleAtQuadRandomly(section);
				}
				//remove circle
				if(counter == ((getDelayOfCircle() + 500)/difficulty) && clip.isRunning()){
					counter2 = 0;
					program.remove(circle);
					program.add(p_50, circle.getX(), circle.getY());
					SCORE = SCORE - 50;
					displayScore();
				}
				//if game is over.
				if(clip.getMicrosecondLength() == clip.getMicrosecondPosition()){
					counter = 0;
					counter2 = 0;
					counter4 = 0;
					program.switchToTutorialGameOverPane();
				}
				//for tutorial msgs
				if(counterForTutorialMsg == 100 && !m1Done){
					program.add(tutorialM1);
					m1Done = true;
				}
				if(counterForTutorialMsg == 6500 && !m2Done){
					program.remove(tutorialM1);
					program.add(tutorialM2);
					m2Done = true;
				}
				if(counterForTutorialMsg == 10500 && !m3Done){
					program.remove(tutorialM2);
					program.add(tutorialM3);
					m3Done = true;
				}
				counter++;
				counter2++;
				counter4++;
				counterForTutorialMsg++;
			}
		});
	}		
	@Override
	public void showContents() {
		program.add(rect);
		program.add(background);
		program.add(scoreLabel);
		program.add(pauseButton);
		counterForTutorialMsg = 0;
		timer.start();
		playSong();
	}
	
	private void launchPauseMenu() {
		program.add(back_shade);
		program.add(p_menu_box);
		program.add(pause_label);
		program.add(resume);
		program.add(back_to_start);
	}

	@Override
	public void hideContents() {
		program.remove(rect);
		program.remove(pauseButton);
		program.remove(scoreLabel);
		program.remove(scoreDisplay);
		program.remove(background);
		timer.stop();
		stopSong();
		program.remove(tutorialM3);
		m1Done = false;
		m2Done = false;
		m3Done = false;
		SCORE = 0;
		scoreDisplay.setLabel("" + SCORE);
	}
	
	public void playSong() {
		File soundFile = new File(SONG_PATH + "preivew.wav");//Creating new song file
		try {
			audioStream = AudioSystem.getAudioInputStream(soundFile);//retrieve the song file
			clip = AudioSystem.getClip();//set song file to clip
			clip.open(audioStream);//open the audio file
			clip.start();//play son
		//catching exceptions
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	public void stopSong(){
		if(clip != null){
			clip.stop();
			clip = null;
		}
	}
	
	
	
	private int getDelayOfCircle() {
		int bpm = 145;
		if(settings.getSong() == "Positive Vibe"){
			bpm = 122;
		}
		if(settings.getSong() == "Off The Hook"){
			bpm = 130;
		}
		if(settings.getSong() == "Vamos A La Playa"){
			bpm = 133;
		}
		if(settings.getSong() == "One Time (Louis Futon Remix)"){
			bpm = 145; //not correct
		}
		int delay = (bpm/60)*1000;
		return delay;
	}
	
	public void addCircleAtQuadRandomly(int section){ //between 1-4
		if(section == 1){
			addCircleAtXY(generateRandomXCordAtQuad(1),generateRandomYCordAtQuad(1));
		}
		else if (section == 2){
			addCircleAtXY(generateRandomXCordAtQuad(2),generateRandomYCordAtQuad(2));
		}
		else if (section == 3){
			addCircleAtXY(generateRandomXCordAtQuad(3),generateRandomYCordAtQuad(3));
		}
		else{
			addCircleAtXY(generateRandomXCordAtQuad(4),generateRandomYCordAtQuad(4));
		}
	}
	
	public double generateRandomXCordAtQuad(int quad){
		if(quad == 1 || quad == 3){
			return Math.random() * 335;
		}
		if(quad == 2 || quad == 4){
			return ((Math.random() * 335) + 400);
		}
		return Math.random() * 335;
	}
	
	public double generateRandomYCordAtQuad(int quad){
		if(quad == 1 || quad == 2){
			return ((Math.random() * 135) + 100);
		}
		else if(quad == 3 || quad == 4){
			return ((Math.random() * 235)+300);
		}
		return Math.random() * 235;
	}
	
	private void displayScore() {
		program.remove(scoreDisplay);
		scoreDisplay = new GLabel("" + SCORE, scoreLabel.getWidth(), 22);
		scoreDisplay.setFont(new Font("", Font.BOLD, 28));
		scoreDisplay.setColor(Color.YELLOW);
		program.add(scoreDisplay);
	}
	
	public void addCircleAtXY(double x, double y) {
		circle.setLocation(x,y);
		int circleNum = 1 + (int)(Math.random() * ((7-1)+1));
		if(circleNum == 1){
			circle.setImage(IMG_PATH + "circle.png");
		}
		else if(circleNum == 2){
			circle.setImage(IMG_PATH + "circle1.png");
		}
		else if(circleNum == 3){
			circle.setImage(IMG_PATH + "circle2.png");
		}
		else if(circleNum == 4){
			circle.setImage(IMG_PATH + "circle3.png");
		}
		else if(circleNum == 5){
			circle.setImage(IMG_PATH + "circle4.png");
		}
		else if(circleNum == 6){
			circle.setImage(IMG_PATH + "circle5.png");
		}
		else if(circleNum == 7){
			circle.setImage(IMG_PATH + "circle6.png");
		}
		program.add(circle);
	}
	
	public Color pickRandomColor() {
		return new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
	}
	
	public void mousePressed(MouseEvent e) {
		program.remove(p_100);
		program.remove(p_50);
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == circle) {
			SCORE = SCORE + 100;
			program.add(p_100, e.getX(), e.getY());
			program.remove(circle);
			displayScore();
		}
		else if(obj == resume) {
			program.remove(back_to_start);
			program.remove(resume);
			program.remove(pause_label);
			program.remove(p_menu_box);
			program.remove(back_shade);
			timer.start();
			clip.start();
			clip.setMicrosecondPosition(currentPositionInSong);
			paused = false;
		}
		else if(obj == back_to_start) {
			gameOver1 = false;
			SCORE = 0;
			program.switchToStartMenu();
		}
		else if(obj == pauseButton) {
			timer.stop();
			launchPauseMenu();
			currentPositionInSong = clip.getMicrosecondPosition();
			clip.stop();
			paused = true;
		}
		else {
			if(!paused) {
				SCORE = SCORE - 50;
				program.add(p_50, e.getX(), e.getY());
				displayScore();
			}
		}
	}
}
