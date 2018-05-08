public class Score {
	int totalScore;
	
	public Score(){
		totalScore = 0;
	}
	
	// If player hits circle on the right time
	public void addFullPoints(){ 
		totalScore += 100;
	}
	
	// If player hits circle early
	public void addHalfPoints(){
		totalScore += 50;
	}
	
	public int getTotalScore(){
		return totalScore;
	}
}