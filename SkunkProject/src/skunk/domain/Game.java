package skunk.domain;
import java.util.LinkedList;

public class Game
{
	private LinkedList<Player> players;
	int kitty; 
	boolean endgame = false;
	private Player currentPlayer;
	int numberOfPlayers, currentPlayerIndex;
	private SkunkIO console = new SkunkIO();
	private int enterEndGame = 0;
	private Player endingPlayer;
	
	public Game()
	{
		players = new LinkedList<>();
		kitty = 0;
	}

	public void setupGame()
	{
		String seeRules = console.welcome();
		if (seeRules.contentEquals("y")) { 
			console.showRules();
		}
		int playerCount = console.requestNumberOfPlayers();
		for (int i = 1; i <= playerCount; i++)
		{
			this.addPlayer(console.requestPlayerName(i));
		}
	}
	
	public void initGameState()
	{
		setCurrentPlayer(0);
		currentPlayerIndex = -1;
		enterEndGame = 0;
		
		LinkedList<Player> tempPlayers = new LinkedList<>();
		
		for (int i = 0; i < players.size(); i++)
		{
			players.get(i).setPlayerScore(0);
			players.get(i).clearTurns(); 	
			
			if (players.get(i).getChips() == 0)
			{
				tempPlayers.add(players.get(i));
			}
		}
		
		
		for (int i = 0; i < tempPlayers.size(); i++)
		{
			removePlayer(tempPlayers.get(i));
		}
	}
	
	public void runGame()
	{
		
		while (true)
		{
			initGameState();
			
			// these can be considered "normal" turns
			while (enterEndGame == 0)
			{
				identifyNextPlayer();
				takeTurn();
				if (currentPlayer.getPlayerScore() >= GameConstants.WINNING_POINTS)
				{
					enterEndGame = 1;
					console.endgameMessage(currentPlayer);
				}
			}
			
			// identify the player that first reached >= 100 points
			endingPlayer = currentPlayer;
			
			// one more turn for every other player
			for (int i = 0; i < players.size(); i++)
			{
				identifyNextPlayer();
				
				if (players.get(currentPlayerIndex).equals(endingPlayer) == false)
				{
					takeTurn();
				}
			}
			
			
			Player winner = determineWinningPlayer();			
			endGameChipDistribution(winner);
			console.displayWinner(winner, kitty);
			
			
			if (console.requestPlayAgain(winner).equals("n"))
			{
				break;
			}
		}
	}
	
	public void identifyNextPlayer()
	{
		if (currentPlayerIndex < numberOfPlayers - 1)
		{
			currentPlayer = players.get(currentPlayerIndex + 1);
			currentPlayerIndex++;
		}
		else
		{
			currentPlayer = players.get(0);
			currentPlayerIndex = 0;
		}
	}
	
	public Player determineWinningPlayer()
	{
		int highScore = 0;
		Player winningPlayer = players.get(0);
		
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).getPlayerScore() > highScore)
			{
				highScore = players.get(i).getPlayerScore();
				winningPlayer = players.get(i);
			}
		}
		
		return winningPlayer;
	}
	
	public void endGameChipDistribution(Player winningPlayer)
	{
		int tempChips = 0;
		
		for (int i = 0; i < players.size(); i++)
		{
			if (players.get(i).equals(winningPlayer) == false)
			{
				if (players.get(i).getPlayerScore() == 0)
				{
					tempChips = players.get(i).getChips();
					if (tempChips >= GameConstants.LOSER_ZERO_SCORE_CHIP_PENALTY)
						tempChips = GameConstants.LOSER_ZERO_SCORE_CHIP_PENALTY;
					players.get(i).setChips(players.get(i).getChips() - tempChips);
					kitty += tempChips;
				}
				else
				{
					tempChips = players.get(i).getChips();
					if (tempChips >= GameConstants.LOSER_CHIP_PENALTY)
						tempChips = GameConstants.LOSER_CHIP_PENALTY;
					players.get(i).setChips(players.get(i).getChips() - tempChips);
					kitty += tempChips;
				}
			}
		}
		
		winningPlayer.setChips(winningPlayer.getChips() + kitty);
	}
	
	public void takeTurn() {
		if (currentPlayer.getChips() <= 0)
		{
			console.turnSkip(currentPlayer);
		}
		else 
		{
			console.startTurn(currentPlayer);
			console.printScoreboard(players);
			currentPlayer.startTurn();
			boolean rollAgain = true;
			while (rollAgain)
			{
				currentPlayer.setRollDecision(console.requestRollDecision(currentPlayer));
				int tempChips = 0;
				switch (currentPlayer.continueTurn())
				{
					case 0:
					{ // double skunk
						rollAgain = false;
						tempChips = currentPlayer.getChips();
						if (tempChips >= GameConstants.SKUNK_DOUBLE_CHIPS_LOST)
							tempChips = GameConstants.SKUNK_DOUBLE_CHIPS_LOST;
						currentPlayer.setChips(currentPlayer.getChips() - tempChips);
						this.addToKitty(tempChips);
						console.printDoubleSkunkResult(currentPlayer);
						break;
					}
					case 1:
					{ // skunk
						rollAgain = false;
						tempChips = currentPlayer.getChips();
						if (tempChips >= GameConstants.SKUNK_CHIPS_LOST)
							tempChips = GameConstants.SKUNK_CHIPS_LOST;
						currentPlayer.setChips(currentPlayer.getChips() - tempChips);
						this.addToKitty(tempChips);
						console.printSkunkResult(currentPlayer);
						break;
					}
					case 2:
					{ // skunk deuce
						rollAgain = false;
						tempChips = currentPlayer.getChips();
						if (tempChips >= GameConstants.SKUNK_DEUCE_CHIPS_LOST)
							tempChips = GameConstants.SKUNK_DEUCE_CHIPS_LOST;
						currentPlayer.setChips(currentPlayer.getChips() - tempChips);
						this.addToKitty(tempChips);
						console.printSkunkDeuceResult(currentPlayer);
						break;
					}
					case 3:
					{ // point scoring
						console.printScoringResult(currentPlayer);
						break;
					}
					case 4:
					{ // decline to roll
						rollAgain = false;
						console.printNoRollResult(currentPlayer);
						break;
					}
				}
			}
		}
	}
		
	public void addPlayer(String name) {
		Player newPlayer = new Player(name);
		players.add(newPlayer);
		numberOfPlayers = players.size();
	}
	
	public void removePlayer(Player player)
	{
		players.remove(player);
		numberOfPlayers = players.size();
	}
	
	public void setCurrentPlayer (int index) {
		currentPlayer = players.get(index);
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void addToKitty(int chipsLost) {
		kitty = kitty + chipsLost;
	}
	
	public int getKitty() {
		return kitty;
	}}