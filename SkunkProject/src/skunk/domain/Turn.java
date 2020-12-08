package skunk.domain;
import java.util.LinkedList;

public class Turn
{
	private LinkedList<Rolls> rolls;
	private Rolls currentRoll;

	
	public Turn()
	{
		rolls = new LinkedList<>();
	}
	
	public void addRoll()
	{
		Rolls newRoll = new Rolls();
		rolls.add(newRoll);
		currentRoll = newRoll;
	}
	
	public Rolls addRoll(Dice inputDice, boolean rollThem)
	{
		Rolls newRoll = new Rolls(inputDice, rollThem);
		rolls.add(newRoll);
		return newRoll;
	}	

	public Rolls getCurrentRoll() {
		return currentRoll;
	}
	
	public int getPointsWon()
	{
		int returnValue = 0;
		
		for (int i = 0; i < rolls.size(); i++)
		{
			switch (rolls.get(i).getResult())
			{
				case skunk:
					return -1;
				case skunkDeuce:
					return -2;
				case doubleSkunk:
					return -3;
				case pointScoring:
					returnValue += (rolls.get(i).getRolledValues().get(0) + rolls.get(i).getRolledValues().get(1));
					break;
			}
		}
		
		return returnValue;
	}
	
	public int getChipsLost()
	{		
		for (int i = 0; i < rolls.size(); i++)
		{
			switch (rolls.get(i).getResult())
			{
				case skunk:
					return GameConstants.SKUNK_CHIPS_LOST;
				case skunkDeuce:
					return GameConstants.SKUNK_DEUCE_CHIPS_LOST;
				case doubleSkunk:
					return GameConstants.SKUNK_DOUBLE_CHIPS_LOST;
				case pointScoring:
					break;
			}
		}
		
		return 0; 
	}
	
	public String toString()
	{
		String returnString = "";
		
		for (int i = 0; i < rolls.size(); i++)
		{
			if (i != 0)
			{
				returnString += "\n";
			}
			returnString += rolls.get(i).toString();
		}
		
		returnString += "\npoints won = " + getPointsWon();
		returnString += "\nchips lost = " + getChipsLost();
		
		return returnString;
	}}