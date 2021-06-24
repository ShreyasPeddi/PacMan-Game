/*
 * This class is a template class. It is a scorecard class which has the properties of 
 * storing username and the user's score in the object
 */
public class ScoreCard {

	//Fields
	//Current User name
	private String name;
	
	//Score of the current game
	private int score;

	//Constructor method initializes the name and score
	public ScoreCard(String name, int score) {
		this.name = name;
		this.score = score;
	}

	//GETTERS AND SETTERS
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	//To string method, prints the object details
	@Override
	public String toString() {
		return "ScoreCard [name=" + name + ", score=" + score + "]";
	}

}	//End of class
