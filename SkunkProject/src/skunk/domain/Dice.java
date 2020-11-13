package skunk.domain;

import java.util.LinkedList;

import edu.princeton.cs.introcs.StdOut;
 
public class Dice
{

	private final int DEFAULT_DIE_COUNT = 2;
	private LinkedList<Die> die;
	private LinkedList<Integer> lastDiceRoll;
	
	public Dice()
	{
		die = new LinkedList<>();
		
		for (int i = 0; i < DEFAULT_DIE_COUNT; i++)
		{
			die.add(new Die());
		}
		
		saveLastRoll();
	}
	
	
	public Dice(LinkedList<LinkedList<Integer>> rollOrders)
	{
		die = new LinkedList<>();
		
		for (int i = 0; i < rollOrders.size(); i++)
		{
			die.add(new Die(rollOrders.get(i)));
		}

		saveLastRoll();
	}
	
	public LinkedList<Integer> getLastRoll()
	{
		return lastDiceRoll;
	}

	
	private void saveLastRoll()
	{
		lastDiceRoll = new LinkedList<>();
		
	
		for (int i = 0; i < die.size(); i++)
		{
			lastDiceRoll.add(die.get(i).getLastRoll());
		}
	}

	public void roll()
	{		
		for (int i = 0; i < die.size(); i++)
		{
			die.get(i).roll();
		}
		
		saveLastRoll();
	}

	public String toString()
	{
		String returnString = "Dice with last roll: ";
		
		for (int i = 0; i < die.size(); i++)
		{
			returnString += ((i == 0) ? "" : " + ") + lastDiceRoll.get(i);
		}		
		
		return returnString;
	}}