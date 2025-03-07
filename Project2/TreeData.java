public class TreeData implements Comparable<TreeData>
{

	//===============================================[Variables]====================================================
	
	private int whichMove;
	private boolean isPlayer;
	private GameBoard boardState;
	
	//==============================================[Constructors]==================================================

	public TreeData(int wm, boolean ip, GameBoard b)
	{
		whichMove = wm;
		isPlayer = ip;
		boardState = b;
	}
	
	//===============================================[Accessors]====================================================

	public boolean getIsPlayer()
	{
		return isPlayer;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public int getWhichMove()
	{
		return whichMove;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public GameBoard getBoard()
	{
		return boardState;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public int getBoardScore()
	{		
		return boardState.getBoardScore();
	}
	
	
	//===============================================[Functions]====================================================

	@Override
	public int compareTo(TreeData arg0) {
		if (getBoardScore() == arg0.getBoardScore())
		{
			return 0;
		} 
		else if (getBoardScore() > arg0.getBoardScore())
		{
			return 1;
		}
		else 
		{
			return -1;
		}		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
}	// End of class