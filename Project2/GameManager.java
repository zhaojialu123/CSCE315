import java.util.*;

public class GameManager
{

	//=================================================[Enums]======================================================

	public enum GameState{
		MainMenu,
		PlayerTurn,
		ResultsMenu
	}	
	
	//===============================================[Variables]====================================================

	private GameState currentGameState;
	
	public GameBoard gameBoard;	
	
	//private ArtificallIntelligence AI;
	//i dont know if the AI is working right now so for testing im just using a random number
	private ArtificialIntelligence AI;
	private int currentPlayer;
	
	//==============================================[Constructors]==================================================

	public GameManager(boolean random, int holeNum, int seedNum)
	{
		gameBoard = new GameBoard(random, holeNum, seedNum);
		AI = new ArtificialIntelligence(ArtificialIntelligence.Difficulty.easy);
	}
	
	//===============================================[Accessors]====================================================

	public GameState getState()
	{
		return currentGameState;
	}

	//--------------------------------------------------------------------------------------------------------------

	public void setState(GameState newState)
	{
		currentGameState = newState;
	}
	
	//===============================================[Functions]====================================================
	/*
	public void startGame()
	{
		changeToNextPlayer(true);
	}
	*/
	
	//--------------------------------------------------------------------------------------------------------------
	
	public void makeMove(int choice)
	{
		gameBoard.makeMove(choice);
	}
	
	//---------------------------------------------------------------------------------------------------------------
	
	//this will be for the AI's move evntuall, also vurnabke to change, just to get it working for now
	public void makeMove()
	{
		//random gens a int between 0, and 6(exculsive 6)
		int choice = AI.chooseMove(new GameBoard(gameBoard));
		gameBoard.makeMove(choice);
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public boolean endGame()
	{
		return gameBoard.gameOver;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public GameBoard board()
	{
		//pass a copy so nothing gets messed up
		return new GameBoard(gameBoard);
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public int turn()
	{
		return gameBoard.playerMove;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	/*
	public boolean checkEndConditions()
	{
		return board.gameOver;
	}
	*/
	
	//--------------------------------------------------------------------------------------------------------------
	
	/*
	public void changeToNextPlayer(boolean firstMove)
	{
		if (!firstMove)
		{
			currentPlayer++;
			if (currentPlayer > players.length - 1) 
			{
				currentPlayer = 0;
			}
		}
		
		setState(GameState.PlayerTurn);
		// wait for player input
		
		if (checkEndConditions()) 
		{
			changeToResultsScreen(board.didPlayerWin());
		} 
		else
		{
			changeToNextPlayer(false);
		}			
	}
	*/
	
	//--------------------------------------------------------------------------------------------------------------
	
	public void changeToResultsScreen(boolean didPlayerWin)
	{
		setState(GameState.ResultsMenu);
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

	//--------------------------------------------------------------------------------------------------------------

}	// End of class
