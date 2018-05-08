import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sound.sampled.*;
import javax.swing.Timer;

import acm.graphics.GImage;
import acm.graphics.GLabel;
//import Circle.changeDisplay;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Game extends GraphicsPane {
	private WindowApplication program;
	
	//Helper variables
	public static final String SONG_PATH = "wavs/";
	public static final String IMG_PATH = "imgs/";
	
	//Images and Labels
	private GImage background;
	private GImage circle;
	private GImage scoreLabel;
	public int SCORE;
	private GLabel scoreDisplay;
	private GImage p_100;
	private GImage p_50;
	//Pause menu GImages
	private GImage pauseButton;
	private GRect back_shade;
	private GRect p_menu_box;
	private GImage pause_label;
	private GImage resume;
	private GImage back_to_start;
		
	//Audio
	AudioInputStream audioStream = null;//Audio stream file we use to stream the music
	File soundFile = null;
	Clip clip;//Clip used to play the songs
	private long currentPositionInSong;
	
	//For the Game
	Timer timer;
	Settings settings;
	private int counterForAddingCircles;
	private int counterForRemovingCircles;
	private int counterForGettingRandomSection;
	private int section;
	private int difficulty;
	boolean gameOver;
	private boolean paused;
	//for pattern stuff
	private boolean runningPattern;
	private int patternChoice;
	private int counterForSquare;
	private double squareX;
	private double squareY;
	private int counterForStar;
	private double starX;
	private double starY;
	private int counterForW;
	private double wX;
	private double wY;
	private int counterForQuads;
	private int counterForZ;
	private double zX;
	private double zY;
	
	
	public Game(WindowApplication app) {
		program = app;
		//Images and Labels
		background = new GImage(IMG_PATH + "background.png");
		circle = new GImage(IMG_PATH + "circle.png"); 
		scoreLabel = new GImage(IMG_PATH + "ScoreLabel.png");
		scoreLabel.setLocation(0, 0);
		scoreDisplay = new GLabel("000", scoreLabel.getWidth(), 0);
		scoreDisplay.setFont(new Font("Game Over Regular", Font.BOLD, 48));
		scoreDisplay.setColor(Color.WHITE);
		pauseButton = new GImage(IMG_PATH + "SmallPauseLabel.png", 0, 572);
		back_shade = new GRect(800, 600);
		back_shade.setFillColor(new Color(0, 0, 0, 100));
		p_menu_box = new GRect(400, 500, 200, 100);
		p_menu_box.setFillColor(Color.BLACK);
		pause_label = new GImage(IMG_PATH + "PauseLabel.png", 328, 120);
		resume = new GImage(IMG_PATH + "resumeButton.png", 256, 200);
		back_to_start = new GImage(IMG_PATH + "StartMenuButton.png", 256, 300);
		p_100 = new GImage(IMG_PATH + "+100.png");
		p_50 = new GImage(IMG_PATH + "-50.png");
		
		//Audio
		currentPositionInSong = 0;
		
		//For the game
		counterForAddingCircles = 0;
		counterForRemovingCircles = 0;
		counterForGettingRandomSection = 0;
		section = 1;
		difficulty = 3;
		paused = false;
		gameOver = false;
		SCORE = 0;
		//For pattern 
		runningPattern = false;
		patternChoice = 0;
		squareX = squareX();
		squareY = squareY();
		counterForSquare = 0;
		counterForStar = 0;
		starX = starX();
		starY = starY();
		counterForW = 0;
		wX = wX();
		wY = wY();
		counterForQuads = 0;
		counterForZ = 0;
		zX = zX();
		zY = zY();
		
		//Ticks every second and calls the action performed section
		timer = new Timer(1, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				//set difficulty
				if(settings.getDifficulty() == 2){
					difficulty = 5;
				}
				else if(settings.getDifficulty() == 3){
					difficulty = 7;
				}
				else{
					difficulty = 3;
				}
				//set clip
				if(clip == null && !gameOver){
					startSong();
				}
				//add counter for circle after score is increased or decreased
				//adds circle through choice of pattern
				if(counterForAddingCircles == (getDelayOfCircle()/difficulty) && clip.isRunning()){
					counterForAddingCircles = 0;
					if(!runningPattern){
						patternChoice =  1 + (int)(Math.random() * ((7-1)+1));
						runningPattern = true;
					}
					//For Random Generated Circles
					if(patternChoice == 1 || patternChoice == 2 || patternChoice == 3 && runningPattern){
						if(counterForQuads == 0){
							section = 1 + (int)(Math.random() * ((4-1)+1));
						}
						addCircleAtQuadRandomly(section);
						counterForQuads++;
						if(counterForQuads == 6){
							counterForQuads = 0;
							runningPattern = false;
						}
					}
					//For Z Pattern
					if(patternChoice == 4 && runningPattern){
						zPattern(counterForZ, zX, zY);
						counterForZ++;
						if (counterForZ == 5){
							counterForZ = 0;
							zX = zX();
							zX = zY();
							runningPattern = false;
						}
					}
					//For W Pattern
					if(patternChoice == 5 && runningPattern){
						wPattern(counterForW, wX, wY);
						counterForW++;
						if(counterForW == 5){
							counterForW = 0;
							wX = wX();
							wY = wY();
							runningPattern = false;
						}
					}
					//For Star Pattern
					if(patternChoice == 6 && runningPattern){
						starPattern(counterForStar, starX, starY);
						counterForStar++;
						if(counterForStar == 6){
							counterForStar = 0;
							starX = starX();
							starY = starY();
							runningPattern = false;
						}
					}
					//For Square Pattern
					if(patternChoice == 7 && runningPattern){
						squarePattern(counterForSquare, squareX, squareY);
						counterForSquare++;
						if (counterForSquare == 4){
							counterForSquare = 0;
							squareX = squareX();
							squareY = squareY();
							runningPattern = false;
						}
					}
				}
				//remove circle
				if(counterForAddingCircles == ((getDelayOfCircle() + 500)/difficulty) && clip.isRunning()){
					counterForRemovingCircles = 0;
					program.remove(circle);
					program.add(p_50, circle.getX(), circle.getY());
					SCORE = SCORE - 50;
					displayScore();
				}
				//if game is over.
				if(clip.getMicrosecondLength() == clip.getMicrosecondPosition()){
					gameOver = true;
					program.switchToGameOverPane(SCORE);
				}
				counterForAddingCircles++;
				counterForRemovingCircles++;
				counterForGettingRandomSection++;
			}
		});
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(scoreLabel);
		program.add(scoreDisplay);
		program.add(pauseButton);
		timer.start();
		paused = false;
	}

	@Override
	public void hideContents() {
		program.remove(pauseButton);
		program.remove(scoreLabel);
		program.remove(scoreDisplay);
		program.remove(background);
		timer.stop();
		stopSong();
		gameOver = false;
		SCORE = 0;
		scoreDisplay.setLabel("" + SCORE);
	}
	
	public double zX(){
		return Math.random() * 610;
	}
	public double zY(){
		return Math.random() * 335;
	}
	public void zPattern(int circleNum, double xCord, double yCord){
		if(circleNum == 0){
			addCircleAtXY(xCord, yCord);
		}
		else if (circleNum == 1){
			addCircleAtXY(xCord + 125, yCord);
		}
		else if (circleNum == 2){
			addCircleAtXY(xCord + 62.5, yCord + 100);
		}
		else if (circleNum == 3){
			addCircleAtXY(xCord , yCord + 200);
		}
		else if (circleNum == 4){
			addCircleAtXY(xCord + 125, yCord + 200);
		}
	}
	public double wX(){
		return Math.random() * 510;
	}
	public double wY(){
		return Math.random() * 385;
	}
	public void wPattern(int circleNum, double xCord, double yCord){
		if(circleNum == 0){
			addCircleAtXY(xCord, yCord);
		}
		else if (circleNum == 1){
			addCircleAtXY(xCord + 75, yCord + 150);
		}
		else if (circleNum == 2){
			addCircleAtXY(xCord + 112.5, yCord + 75);
		}
		else if (circleNum == 3){
			addCircleAtXY(xCord + 150, yCord + 150);
		}
		else if (circleNum == 4){
			addCircleAtXY(xCord + 225, yCord);
		}
	}
	
	public double starX(){
		return ((Math.random() *385)+ 100);
	}
	public double starY(){
		return ((Math.random() *235) + 100);
	}
	public void starPattern(int circleNum, double xCord, double yCord){
		if(circleNum == 0){
			addCircleAtXY(xCord + 50, yCord - 100);
		}
		else if (circleNum == 1){
			addCircleAtXY(xCord + 200, yCord + 100);
		}
		else if (circleNum == 2){
			addCircleAtXY(xCord, yCord);
		}
		else if (circleNum == 3){
			addCircleAtXY(xCord + 250, yCord);
		}
		else if (circleNum == 4){
			addCircleAtXY(xCord + 50, yCord + 100);
		}
		else if (circleNum == 5){
			addCircleAtXY(xCord + 200, yCord - 100);
		}
	}
	
	public double squareX(){
		return Math.random() * 470;
	}
	public double squareY(){
		return Math.random() * 270;
	}
	public void squarePattern(int circleNum, double xCord, double yCord){
		if(circleNum == 0){
			addCircleAtXY(xCord, yCord);
		}
		else if (circleNum == 1){
			addCircleAtXY(xCord, yCord + 200);
		}
		else if (circleNum == 2){
			addCircleAtXY(xCord + 200 , yCord + 200);
		}
		else if (circleNum == 3){
			addCircleAtXY(xCord + 200, yCord);
		}
	}
	
	public void startSong(){
		changeSong(settings);
		changeVolume(settings);
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
			return Math.random() * 235;
		}
		else if(quad == 3 || quad == 4){
			return ((Math.random() * 235)+300);
		}
		return Math.random() * 235;
	}
	
	public void setSettings(Settings set){
		settings = set;
	}
	
	//Adjusts the volume of the music
	public void changeVolume(Settings set){		
		FloatControl gainControl = 
				(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		//5 different volume settings that can be clicked
		if(set.getVolume() == 1){
			gainControl.setValue(-15.0f);
		}
		if(set.getVolume() == 2){
			gainControl.setValue(-8.0f);
		}
		if(set.getVolume() == 3){
			gainControl.setValue(-2.0f);
		}
		if(set.getVolume() == 4){
			gainControl.setValue(2.0f);
		}
		if(set.getVolume() == 5){
			gainControl.setValue(6.0f);
		}
	}
	
	public void changeSong(Settings set){
		//Change song
		if(set.getSong() == "Positive Vibe") {
			PlaySong(SONG_PATH + "Positive Vibe.wav");
		}
		if(set.getSong() == "Off The Hook") {
			PlaySong(SONG_PATH + "Off The Hook.wav");
		}
		if(set.getSong() == "Vamos A La Playa") {
			PlaySong(SONG_PATH + "Vamos A La Playa.wav");
		}
		if(set.getSong() == "One Time (Louis Futon Remix)") {
			PlaySong(SONG_PATH + "One Time (Louis Futon Remix).wav");
		}
	}
	
	public int getDelayOfCircle() {
		int bpm = 0;
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
			bpm = 135;
		}
		int delay = (bpm/60)*1000;
		return delay;
	}
	

	public void PlaySong(String songName) {
		File soundFile = new File(songName);//Creating new song file
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
			clip.close();
			clip = null;
		}
	}
	
	public Color pickRandomColor() {
		return new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
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
	
	public void displayScore() {
		program.remove(scoreDisplay);
		scoreDisplay = new GLabel("" + SCORE, scoreLabel.getWidth(), 22);
		scoreDisplay.setFont(new Font("", Font.BOLD, 28));
		scoreDisplay.setColor(Color.YELLOW);
		program.add(scoreDisplay);
	}
	
	private void launchPauseMenu() {
		program.add(back_shade);
		program.add(p_menu_box);
		program.add(pause_label);
		program.add(resume);
		program.add(back_to_start);
	}
	
	@Override
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
