import java.util.*;

public class Node
{
	private GameBoard board;
	private int move;
	private int heuristic;
	private boolean maximize;
	private ArrayList<Node> children;
	
	public Node(GameBoard _board, int _move, boolean repeat)
	{
		board = _board;
		move = _move;
		maximize = repeat;
		children = null;
		calcH();
	}
	
	public ArrayList<Node> children()
	{
		return children;
	}
	
	public boolean type()
	{
		return maximize;
	}
	
	public int eval()
	{
		return heuristic;
	}
	
	public void addChild(Node child)
	{
		if (children == null)
		{
			children = new ArrayList<Node>();
		}
		children.add(child);
	}
	
	public GameBoard board()
	{
		return board; 
	}
	
	public void calcH()
	{
		int myLoc = (board.size() / 2) - 1;
		int enemyLoc = board.size() - 1;
		if (board.gameOver && board.at(myLoc) > board.at(enemyLoc))
		{
			heuristic = 10000;
		}
		else if (board.gameOver && board.at(enemyLoc) > board.at(myLoc))
		{
			heuristic = -10000;
		}
		else {
			heuristic = board.at(myLoc) - board.at(enemyLoc);
		}
	}
	
	public int move()
	{
		return move;
	}
	

}