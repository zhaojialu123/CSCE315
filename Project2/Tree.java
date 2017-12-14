import java.util.*;

public class Tree<T>
{

	//===============================================[Variables]====================================================

	private TreeNode<T> rootNode;
	
	private long startTime;
	
	//==============================================[Constructors]==================================================
	
	public Tree()
	{
		rootNode = null;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public Tree(T newData)
	{
		rootNode = new TreeNode<T>(newData);
	}	
	
	//--------------------------------------------------------------------------------------------------------------
	
	public Tree(TreeNode<T> newRoot)
	{
		rootNode = newRoot;
	}
	
	//===============================================[Accessors]====================================================
	
	public TreeNode<T> getRoot()
	{
		return rootNode;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public void setRoot(TreeNode<T> newRootNode)
	{
		rootNode = newRootNode;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public void setRoot(T data)
	{
		TreeNode<T> tempNode = new TreeNode<T>(data);
		rootNode = tempNode;
	}
	
	//===============================================[Functions]====================================================

	public TreeNode<T> find(T dataToFind)
	{
		return rootNode.findBreadth(dataToFind);
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public boolean contains(T dataToFind)
	{
		if (rootNode == null) 
		{
			return false;
		}
		else
		{
			return (find(dataToFind) != null);
		}		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> getMinMax(int depthMax)
	{
		if (rootNode == null) 
		{
			return null;
		}
		else
		{
			return (rootNode.getMinMax(depthMax, true));	
		}		
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public Vector<TreeNode<T>> getMinMaxVectorIterative(int depthMax, long maxTime)
	{		
		Vector<TreeNode<T>> retVec = new Vector<TreeNode<T>>();
		
		if (rootNode == null)
		{
			return retVec;
		}
		
		startTime = System.nanoTime();
		long endTime = startTime + maxTime;
		
		TreeNode<T> tempNode = null;
		
		HashMap<Integer,Integer> transitionTable = new HashMap<Integer,Integer>();		
		
		for (int i = 0; i < depthMax && System.nanoTime() < endTime; i++) 
		{
			long tempStart = System.nanoTime();
			tempNode = rootNode.getMinMaxPruningIterative(i, true,null, null,transitionTable);
			long stepTime = System.nanoTime() - tempStart;
			if (stepTime + System.nanoTime() >= endTime) {
				break;
			}
		}
		
		retVec.add(tempNode);
		
		while(tempNode.getParent() != null) 
		{
			tempNode = tempNode.getParent();
			retVec.add(tempNode); 				
		}
		
		Collections.reverse(retVec);
		
		return retVec;		
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public Vector<TreeNode<T>> getMinMaxVectorPruning(int depthMax)
	{		
		Vector<TreeNode<T>> retVec = new Vector<TreeNode<T>>();
		
		if (rootNode == null)
		{
			return retVec;
		}
				
		TreeNode<T> tempNode = rootNode.getMinMaxPruning(depthMax, true,null, null);
		
		retVec.add(tempNode);
		
		while(tempNode.getParent() != null) 
		{
			tempNode = tempNode.getParent();
			retVec.add(tempNode); 				
		}
		
		Collections.reverse(retVec);
				
		return retVec;		
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public Vector<TreeNode<T>> getMinMaxVector(int depthMax)
	{		
		Vector<TreeNode<T>> retVec = new Vector<TreeNode<T>>();
		
		if (rootNode == null)
		{
			return retVec;
		}
						
		TreeNode<T> tempNode = rootNode.getMinMax(depthMax, true);
		
		retVec.add(tempNode);
		
		while(tempNode.getParent() != null) 
		{
			tempNode = tempNode.getParent();
			retVec.add(tempNode); 				
		}
		
		Collections.reverse(retVec);
				
		return retVec;		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	boolean hasTime() 
	{
		return true;
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public void clear() 
	{
		rootNode = null;
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public int size()
	{
		if (rootNode == null) 
		{
			return -1;
		}
		else
		{
			return rootNode.size();	
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------
	
	//--------------------------------------------------------------------------------------------------------------

	
	//--------------------------------------------------------------------------------------------------------------
	
	
	//--------------------------------------------------------------------------------------------------------------

}	// End of class

/*
 * 
 * Possible usage of said tree
 * 
 * Tree(0) <- AI turn
 * func extendTree(node, depth, playersTurn)
 * 	if (pt){
 * 		foreach player move m
 * 			extendTree(node.addChild(m),depth - 1, false);
 * 	}
 * 	else
 * 	{
 *		foreach AI move m
 * 			extendTree(node.addChild(m),depth - 1, true);
 * 	}
 * 
 * 
 * 
 * extendTree(tree.rootNode,3,false)
 * 
 * */

//=================================================[Enums]======================================================

//===============================================[Variables]====================================================

//==============================================[Constructors]==================================================

//===============================================[Accessors]====================================================

//===============================================[Functions]====================================================


//--------------------------------------------------------------------------------------------------------------

