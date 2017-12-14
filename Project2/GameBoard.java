import java.util.*;

public class GameBoard
{
	private House[] houses;
	private int numHouses;
	private int player1Store;
	private int player2Store;
	public int playerMove;
	public boolean gameOver;
	public int winner;
	
	public GameBoard(boolean random, int holeNum, int seedNum, LinkedList<Integer> seedDistro) 
	{
		numHouses = holeNum * 2 + 2;
		player1Store = numHouses / 2 - 1;
		player2Store = numHouses - 1;
		houses = new House[numHouses];
		playerMove = 0;
		gameOver = false;
		
		//Construct all houses and add them to our map
		for (int i = 0; i < numHouses; i++)
		{
			House house = new House(seedNum);
			houses[i] = house;
		}
		
		if (seedDistro != null) 
		{
			for (int i = 0; i < holeNum; i++)
			{
				int seeds = seedDistro.poll();
				houses[i].setStones(seeds);
				houses[i + holeNum + 1].setStones(seeds);
			}
		}
		//Init the houses
		linkHouses();
		houses[player1Store].setStones(0);
		houses[player2Store].setStones(0);
	}
	
	public GameBoard(GameBoard copy)
	{
		this.numHouses = copy.numHouses;
		this.player1Store = copy.player1Store;
		this.player2Store = copy.player2Store;
		this.houses = new House[numHouses];
		this.gameOver = copy.gameOver;
		this.playerMove = copy.playerMove;
		
		for (int i = 0; i < numHouses; i++)
		{
			houses[i] = new House(copy.houses[i].getStones());
		}
		
		linkHouses();
		
	}
	
	public void linkHouses()
	{
		for (int i = 0; i < player1Store; i++)
		{
			House currHouse = houses[i];
			currHouse.setNext(houses[i + 1]);
			currHouse.setAcross(houses[numHouses - 2 - i]);
			currHouse.setStore(houses[player1Store]);
		}
		
		//7-12 refer to player2 store at 13
		for(int i = 7; i < player2Store; i++)
		{
			House currHouse = houses[i];
			currHouse.setNext(houses[i + 1]);
			currHouse.setAcross(houses[i - (2 * (i - (numHouses / 2 - 1)))]);
			currHouse.setStore(houses[player2Store]);
		}
		
		//init stores
		houses[player1Store].setNext(houses[player1Store + 1]);
		houses[player1Store].setAcross(null);
		
		houses[player2Store].setNext(houses[0]);
		houses[player2Store].setAcross(null);
	}
	
	public boolean makeMove(int loc) 
	{
		boolean movesAgain = false;
		if (loc != player1Store && loc != player2Store)
		{	
			if (!houses[loc].move())
			{
				playerMove = (playerMove + 1) % 2;				
			}
			else
			{
				movesAgain = true;
			}
			
			gameOver = player1Empty() || player2Empty();
			if (gameOver)
			{
				houses[0].end(0);
				houses[player1Store + 1].end(0);
				if (houses[player1Store].getStones() > houses[player2Store].getStones())
				{
					winner = 0;
				}
				else if (houses[player1Store].getStones() < houses[player2Store].getStones())
				{
					winner  = 1;
				}
				else
				{
					winner  = -1;
				}
			}
		}
		return movesAgain;
	}
	
	public int at(int loc)
	{
		return houses[loc].getStones();
	}
	
	public int size()
	{
		return numHouses;
	}
	
	public House[] getPlayerHouses()
	{
		int endIndex = houses.length;
		endIndex = (endIndex / 2) - 1;
		return Arrays.copyOfRange(houses,0,endIndex);
	}
	
	public House[] getEnemyHouses()
	{
		int startIndex = houses.length - 1;
		startIndex = (startIndex / 2);		
		return Arrays.copyOfRange(houses,startIndex,houses.length - 2);
	}
	
	private boolean player1Empty() 
	{
		for (int i = 0; i < player1Store; i++)
		{
			if(houses[i].getStones() != 0)
			{
				return false;
			}
		}
		return true;
	}
	
	private boolean player2Empty() 
	{
		for (int i = player1Store + 1; i < player2Store; i++)
		{
			if(houses[i].getStones() != 0)
			{
				return false;
			}
		}
		return true;
	}
	
	public String winner()
	{
		if (houses[player1Store].getStones() > houses[player2Store].getStones())
		{
			return "Player 1 Wins!";
		}
		else if (houses[player1Store].getStones() < houses[player2Store].getStones())
		{
			return "Player 2 Wins!";
		}
		else
		{
			return "Tie!";
		}
	}
	
	public boolean didPlayerWin() {
		int halfHouses = houses.length / 2;
		return houses[halfHouses - 1].getStones() > houses[houses.length - 1].getStones();
	}
	
	public int getPlayerScore()
	{
		return houses[player2Store].getStones();
	}
	
	public int getAIScore()
	{
		return houses[player1Store].getStones();
	}
	
	public int getBoardScore()	// for AI 
	{
		return getPlayerScore() - getAIScore();
	}

	public int getHousesCount()
	{
		return houses.length;
	}
	
	public House[] getHouses()
	{
		return houses;
	}
}
