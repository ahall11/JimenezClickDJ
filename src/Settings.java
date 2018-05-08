import javax.sound.sampled.Clip;

public class Settings {
	int difficulty; // 1 easy, 2 medium, 3 hard
	String song; //songs name
	int volume; // volume from 1 to 5
	
	public Settings(){
		difficulty = 1;
		song = "One Time (Louis Futon Remix).wav";
		volume = 3;
	}
	
	public Settings(int d, String s, int v){
		difficulty = d;
		song = s;
		volume = v;
	}
	
	public int getDifficulty(){
		return difficulty;
	}
	
	public String getSong(){
		return song;
	}
	
	public int getVolume(){
		return volume;
	}
	
	public void setDifficulty(int diff){
		difficulty = diff;
	}
	
	public void setSong(String s){
		song = s;
	}
	
	public void setVolume(int v){
		volume = v;
	}
}