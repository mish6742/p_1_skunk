package skunk.domain;
import java.util.LinkedList;

public class Player
{
	private int playerScore;
	private int chips;
	private String name;
	private LinkedList<Turn> turns;
	private Turn currentTurn;
	private String rollDecision;
	
	public Player(String inputName)
	{
		name = inputName;
		playerScore = GameConstants.STARTING_POINTS;
		chips = GameConstants.STARTING_CHIPS;
		
		turns = new LinkedList<>();
	}
	
	public void clearTurns()
	{
		turns = new LinkedList<>();
	}
	
	public void startTurn() {
		Turn newTurn = new Turn();
		currentTurn = newTurn;
		turns.add(newTurn);
	}
	
	public int continueTurn() {
		if (rollAgain()) {
			currentTurn.addRoll();
			int scoreResult = currentTurn.getPointsWon();
			switch(scoreResult) {
				case -3: 
					playerScore = 0;
//					chips -= 4;
					return 0;
				case -1: 
//					chips -= 1;
					return 1;
				case -2: 	
//					chips -= 2;
					return 2;
				default: 
					return 3;
			}
		}
		else {
			playerScore += currentTurn.getPointsWon();
			return 4;
		}
	}
	
	public int getPlayerScore()
	{
		return playerScore;
	}
	
	public void setPlayerScore(int value)
	{
		playerScore = value;
	}
	
	public Turn getCurrentTurn() { 
		return currentTurn;
	}
	
	public int getChips()
	{
		return chips;
	}
	
	public void setChips(int value)
	{
		chips = value;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setRollDecision(String input)
	{
		rollDecision = input;
	}
	
	public boolean rollAgain()
	{
		if (rollDecision.equals("y"))
			return true;
		else 
			return false;
	}}