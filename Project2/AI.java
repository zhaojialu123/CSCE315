public class AI
{
	private GameBoard board;
	private Node root;
	private int depth;
	
	 public AI(int _depth)
	{
		board = null;
		root = null;
		depth = _depth; 
	}
	
	public AI(GameBoard _board, int _depth)
	{
		board = _board;
		Node root = new Node(board, -1, true);
		depth = _depth;
	}
	
	
	
	public void setBoard(GameBoard _board)
	{
		board = _board;
		root = new Node(new GameBoard(_board), -1, true);
	}
	
	public int depth()
	{
		return depth;
	}
	
	
	
	public void fillTree(int depth, Node parent)
	{
		if (depth == 0) { return; }
		if (parent == null)
		{
			parent = root;
		}
		int numChoices  = (board.size() - 2) /  2;
		GameBoard pBoard = parent.board();
		
		for (int i  = 0; i < numChoices; i++)
		{
		

			GameBoard copy = new GameBoard(pBoard);
			
			
			if (copy.playerMove == 0)
			{
				if (copy.at(i) != 0)
				{
					copy.makeMove(i);
					boolean maximizing = (copy.playerMove == 0);
					Node child = new Node(copy, i, maximizing);
					parent.addChild(child);
				}
			}
			
			
			else
			{
				if (copy.at(i + numChoices + 1) != 0)
				{				
					copy.makeMove( i + numChoices + 1);
					boolean maximizing = (copy.playerMove == 0);
					Node child = new Node(copy, i + numChoices + 1, maximizing);
					parent.addChild(child);
				}
			}
		
		
		
		}
		
		if (parent.children() != null)
		{
			for (Node child : parent.children() )
			{
				fillTree(depth - 1, child);
			}
		}
		
		
		
	}
	
	public int move()
	{
		Node choice = null;
		int maxScore = Integer.MIN_VALUE;
		for (Node child : root.children())
		{
			int score = minimax(child, 20, child.type());
			if (choice  == null || score > maxScore)
			{
				maxScore = score;
				choice = child;
			}
			System.out.println(score + ":" + child.move());
		}
		return choice.move();
	}
	
	public int minimax(Node node, int depth, boolean maximizing)
	{
		if  (node == null)
		{
				node = root;
		}
		
		//leaf node or depth limit
		if (depth == 0 || node.children() == null)
		{
			return node.eval();
		}
		
		
		if(maximizing)
		{
			int bestChoice = Integer.MIN_VALUE; // - infinity
			for (Node child : node.children() )
			{
				int choice = minimax(child, depth - 1, child.type() );
				if (bestChoice < choice )
				{
					bestChoice = choice;
				}
			}
			return bestChoice;
		}
		
		else //minimizing
		{
			int bestChoice =Integer.MAX_VALUE; // + infinity
			for (Node child : node.children() )
			{
				int choice = minimax(child, depth - 1, child.type() );
				if (bestChoice > choice)
				{
					bestChoice = choice;
				}
			}
			return bestChoice;
		}
	}
	
	
}