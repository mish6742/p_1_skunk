package skunk.domain;

import java.util.LinkedList;

import edu.princeton.cs.introcs.StdOut;

public class Die
{
	private int lastRoll;
	private LinkedList<Integer> rollOrder;

	public Die()
	{
		rollOrder = new LinkedList<>();
		this.roll();
	}
	
	
	public Die(LinkedList<Integer> inputRollOrder)
	{
		
		
		rollOrder = (LinkedList<Integer>) inputRollOrder.clone();
		
	
		this.roll();
	}
	
	
	public void loadTheDie(LinkedList<Integer> inputRollOrder)
	{
	
		rollOrder = (LinkedList<Integer>) inputRollOrder.clone();
		this.roll();
	}

	public int getLastRoll() // getter or accessor method
	{
		return this.lastRoll;
	}

	public void roll() 
	{
		if (rollOrder.isEmpty())
		{
			this.lastRoll = (int) (Math.random() * 6 + 1);
		}
		else
		{
			
			this.lastRoll = rollOrder.pop();
			
			
			rollOrder.add(lastRoll);
		}
	}
	
	@Override
	public String toString() 
	{
		return "Die: " + this.getLastRoll();
	}
}