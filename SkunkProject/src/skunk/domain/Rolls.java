package skunk.domain;

import java.util.LinkedList;

public class Rolls
{
	private Dice dice;
	private LinkedList<Integer> rolledValues;	
	private InterpretedRoll result;

	
	public Rolls()
	{
		dice = new Dice();
		dice.roll();
		rolledValues = dice.getLastRoll();
		result = RollInterpreter.interpretRoll(rolledValues);

	}
	
	
	public Rolls(Dice inputDice, boolean rollThem)
	{
		dice = inputDice;
		
		
		if (rollThem != false)
		{
			dice.roll();
		}
		rolledValues = dice.getLastRoll();
		result = RollInterpreter.interpretRoll(rolledValues);
	}
	
	public LinkedList<Integer> getRolledValues() {
		return rolledValues;
	}
	
	public int getRollScore() {
		return rolledValues.get(0) + rolledValues.get(1);
	}
	
	public InterpretedRoll getResult() {
		return result;
	}
	
	public String toString()
	{

		String returnString = "Dice with last roll: ";
		
		for (int i = 0; i < rolledValues.size(); i++)
		{
			returnString += ((i == 0) ? "" : " + ") + rolledValues.get(i);
		}
		
		returnString += "...interpreted as: " + result;
		
		return returnString;
	}}
	