import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import acm.graphics.*;
import javax.sound.sampled.*;
import java.io.*;

public class GameSettings extends GraphicsPane {
	private WindowApplication program; //Our foundation that inserts everything to the pane
	public static final String IMG_PATH = "imgs/";//Images folder path
	public static final String SONG_PATH = "wavs/";//Songs folder path
	AudioInputStream audioStream = null;//Audio stream file we use to stream the music
	File soundFile = null;
	Clip clip;//Clip used to play the songs
	
	//Declaring all of the images
	private GImage background;
	private GImage volume1;
	private GImage volume2;
	private GImage volume3;
	private GImage volume4;
	private GImage volume5;
	private GImage backButton;
	private GImage offTheHookSong;
	private GImage oneTimeSong;
	private GImage playGameButton;
	private GImage positiveVibesSong;
	private GImage vamosALaPlayaSong;
	private GImage easyButton;
	private GImage mediumButton;
	private GImage hardButton;
	private Settings settings;
	private GLabel songPreviewMessage;
	private GRect oneTimeButtonOutline;
	private GRect offTheHookButtonOutline;
	private GRect positiveVibesButtonOutline;
	private GRect playaButtonOutline;
	private GRect volume1outline;
	private GRect volume2outline;
	private GRect volume3outline;
	private GRect volume4outline;
	private GRect volume5outline;
	private GRect easyButtonOutline;
	private GRect mediumButtonOutline;
	private GRect hardButtonOutline;
	
	public GameSettings(WindowApplication app) {
		this.program = app;
		
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
		
		//creating all GImages for the pane
		background = new GImage(IMG_PATH + "background.png");
		volume1 = new GImage(IMG_PATH + "1.png");
		volume2 = new GImage(IMG_PATH + "2.png");
		volume3 = new GImage(IMG_PATH + "3.png");
		volume4 = new GImage(IMG_PATH + "4.png");
		volume5 = new GImage(IMG_PATH + "5.png");
		backButton = new GImage(IMG_PATH + "backButton.png");
		offTheHookSong = new GImage(IMG_PATH + "offTheHookSong.png");
		oneTimeSong = new GImage(IMG_PATH + "oneTimeSong.png");
		playGameButton = new GImage(IMG_PATH + "playGameButton.png");
		positiveVibesSong = new GImage(IMG_PATH + "positiveVibesSong.png");
		vamosALaPlayaSong = new GImage(IMG_PATH + "vamosLaPlayaSong.png");
		easyButton = new GImage(IMG_PATH + "easyButton.png");
		mediumButton = new GImage(IMG_PATH + "mediumButton.png");
		hardButton = new GImage(IMG_PATH + "hardButton.png");
		//UI: Song Preview Message
		songPreviewMessage = new GLabel("**Click on a song to preview it", 10, 130);
		songPreviewMessage.setFont(new Font("Ariel", Font.BOLD, 16));
		songPreviewMessage.setColor(Color.YELLOW);
		//UI: Song button outlines
		oneTimeButtonOutline = new GRect(7, 147, 340, 100);
		oneTimeButtonOutline.setColor(Color.RED);
		offTheHookButtonOutline = new GRect(7, 247, 340, 100);
		offTheHookButtonOutline.setColor(Color.RED);
		positiveVibesButtonOutline = new GRect(7, 347, 340, 100);
		positiveVibesButtonOutline.setColor(Color.RED);
		playaButtonOutline = new GRect(7, 447, 340, 100);
		playaButtonOutline.setColor(Color.RED);
		//UI: Volume Button outlines
		volume1outline = new GRect(452, 367, 56, 67);
		volume1outline.setColor(Color.RED);
		volume2outline = new GRect(522, 367, 56, 67);
		volume2outline.setColor(Color.RED);
		volume3outline = new GRect(592, 367, 56, 67);
		volume3outline.setColor(Color.RED);
		volume4outline = new GRect(662, 367, 56, 67);
		volume4outline.setColor(Color.RED);
		volume5outline = new GRect(732, 367, 56, 67);
		volume5outline.setColor(Color.RED);
		
		//UI: Difficulty button outlines
		easyButtonOutline = new GRect(453, 48, 338, 98);
		easyButtonOutline.setColor(Color.RED);
		mediumButtonOutline = new GRect(453, 148, 338, 98);
		mediumButtonOutline.setColor(Color.RED);
		hardButtonOutline = new GRect(453, 248, 338, 98);
		hardButtonOutline.setColor(Color.RED);
		//default settings
		settings = new Settings(1, "One Time (Louis Futon Remix)", 3); 
		
	}
	@Override
	//Shows all contents on screen
	public void showContents() {
		program.add(background);
		program.add(backButton, 10, 10);
		program.add(playGameButton, 470, 450);
		program.add(oneTimeSong, 10, 150);
		program.add(offTheHookSong, 10, 250);
		program.add(positiveVibesSong, 10, 350);
		program.add(vamosALaPlayaSong, 10, 450);
		program.add(easyButton, 455, 50);
		program.add(mediumButton, 455, 150);
		program.add(songPreviewMessage);
		program.add(hardButton, 455, 250);
		program.add(volume1, 455, 370);
		program.add(volume2, 525, 370);
		program.add(volume3, 595, 370);
		program.add(volume4, 665, 370);
		program.add(volume5, 735, 370);
		program.add(oneTimeButtonOutline);
		program.add(volume3outline);
		program.add(easyButtonOutline);
		playPreview(SONG_PATH + "One Time (Louis Futon Remix).wav");
	}

	@Override
	//Removes content from screen
	public void hideContents() {
		program.remove(background);
		program.remove(playGameButton);
		program.remove(backButton);
		program.remove(oneTimeSong);
		program.remove(offTheHookSong);
		program.remove(positiveVibesSong);
		program.remove(vamosALaPlayaSong);
		program.remove(songPreviewMessage);
		program.remove(easyButton);
		program.remove(mediumButton);
		program.remove(hardButton);
		program.remove(volume1);
		program.remove(volume2);
		program.remove(volume3);
		program.remove(volume4);
		program.remove(volume5);
	}

	@Override
	//Keeps track of when the mouse is pressed and executes an action depending on what is clicked 
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if(obj == oneTimeSong){
			settings.setSong("One Time (Louis Futon Remix)");
			stopPreview();
			program.remove(playaButtonOutline);
			program.remove(positiveVibesButtonOutline);
			program.remove(offTheHookButtonOutline);
			program.add(oneTimeButtonOutline);
			playPreview(SONG_PATH + "One Time (Louis Futon Remix).wav");
		}
		if(obj == vamosALaPlayaSong){
			settings.setSong("Vamos A La Playa");
			stopPreview();
			program.remove(positiveVibesButtonOutline);
			program.remove(offTheHookButtonOutline);
			program.remove(oneTimeButtonOutline);
			program.add(playaButtonOutline);
			playPreview(SONG_PATH + "Vamos A La Playa.wav");
		}
		if(obj == positiveVibesSong){
			settings.setSong("Positive Vibe");
			stopPreview();
			program.remove(playaButtonOutline);
			program.remove(oneTimeButtonOutline);
			program.remove(offTheHookButtonOutline);
			program.add(positiveVibesButtonOutline);
			playPreview(SONG_PATH + "Positive Vibe.wav");
		}
		if(obj == offTheHookSong){
			settings.setSong("Off The Hook");
			stopPreview();
			program.remove(playaButtonOutline);
			program.remove(positiveVibesButtonOutline);
			program.remove(oneTimeButtonOutline);
			program.add(offTheHookButtonOutline);
			playPreview(SONG_PATH + "Off The Hook.wav");
		}
		if(obj == easyButton){
			program.remove(mediumButtonOutline);
			program.remove(hardButtonOutline);
			program.add(easyButtonOutline);
			settings.setDifficulty(1);
		}
		if(obj == mediumButton){
			program.remove(easyButtonOutline);
			program.remove(hardButtonOutline);
			program.add(mediumButtonOutline);
			settings.setDifficulty(2);
		}
		if(obj == hardButton){
			program.remove(easyButtonOutline);
			program.remove(mediumButtonOutline);
			program.add(hardButtonOutline);
			settings.setDifficulty(3);
		}
		if(obj == volume1){
			program.remove(volume2outline);
			program.remove(volume3outline);
			program.remove(volume4outline);
			program.remove(volume5outline);
			program.add(volume1outline);
			changeVolume(1);
			settings.setVolume(1);
		}
		if(obj == volume2){
			program.remove(volume1outline);
			program.remove(volume3outline);
			program.remove(volume4outline);
			program.remove(volume5outline);
			program.add(volume2outline);
			changeVolume(2);
			settings.setVolume(2);
		}
		if(obj == volume3){
			program.remove(volume1outline);
			program.remove(volume2outline);
			program.remove(volume4outline);
			program.remove(volume5outline);
			program.add(volume3outline);
			changeVolume(3);
			settings.setVolume(3);
		}
		if(obj == volume4){
			program.remove(volume1outline);
			program.remove(volume2outline);
			program.remove(volume3outline);
			program.remove(volume5outline);
			program.add(volume4outline);
			changeVolume(4);
			settings.setVolume(4);
		}
		if(obj == volume5){
			program.remove(volume1outline);
			program.remove(volume2outline);
			program.remove(volume3outline);
			program.remove(volume4outline);
			program.add(volume5outline);
			changeVolume(5);
			settings.setVolume(5);
		}
		if(obj == backButton){
			stopPreview();
			program.switchToStartMenu();
		}
		if(obj == playGameButton){
			stopPreview();
			
			program.switchToGamePane(settings);
			//How can we play the game with the settings from here?
		}
	}
	//Adjusts the volume of the music
	public void changeVolume(int num){
		FloatControl gainControl = 
				(FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		//5 different volume settings that can be clicked
		if(num == 1){
			gainControl.setValue(-15.0f);
		}
		if(num == 2){
			gainControl.setValue(-8.0f);
		}
		if(num == 3){
			gainControl.setValue(-2.0f);
		}
		if(num == 4){
			gainControl.setValue(2.0f);
		}
		if(num == 5){
			gainControl.setValue(6.0f);
		}
	}
	//Plays the music file
	public void playPreview(String songName) {
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
	//Stops the audio file
	public void stopPreview(){
		if(clip != null) clip.stop();
	}
}
