import java.util.*;

public class TreeNode<T>
{

	//===============================================[Variables]====================================================

	private T data;
	
	private TreeNode<T> parent;
	
	private Vector<TreeNode<T>> children;
	
	//==============================================[Constructors]==================================================
	
	public TreeNode(T newData)
	{				
		data = newData;
		parent = null;
		children = new Vector<TreeNode<T>>();
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode(T newData, TreeNode<T> newParent)
	{				
		data = newData;
		parent = newParent;
		children = new Vector<TreeNode<T>>();
	}

	//===============================================[Accessors]====================================================
	
	public T getData()
	{
		return data;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> getParent()
	{
		return parent;
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public Vector<TreeNode<T>> getChildren()
	{
		return children;
	}
	
	//===============================================[Functions]====================================================
	
	public int childCount()
	{
		return children.size();
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public boolean isLeaf()
	{
		return (childCount() == 0);
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public TreeNode<T> addChild(T data)
	{
		TreeNode<T> retNode = new TreeNode<T>(data,this);		
		children.add(retNode);
		return retNode; 
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> findDepth(T dataToFind)	// Depth first
	{
		if (data == dataToFind) 
		{
			return this;
		}
		else 
		{
			TreeNode<T> retNode = null;
			for(int i = 0; i < children.size(); i++)
			{
				retNode = children.get(i).findDepth(dataToFind);
				if (retNode != null)
				{
					return retNode;
				}
			}
			return retNode;		
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> findBreadth(T dataToFind)	// Breadth first
	{
		if (data == dataToFind)
		{
			return this;
		}
		else
		{
			
			TreeNode<T> retNode = null;
			for(int i = 0; i < children.size(); i++)
			{
				if (children.get(i).data == dataToFind)
				{
					retNode = children.get(i);
					return retNode;
				}
			}
									
			for(int i = 0; i < children.size(); i++)
			{
				retNode = children.get(i).findBreadth(dataToFind);
				if (retNode != null)
				{
					return retNode;
				}
			}
			
			return retNode;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> getMinMax(int depthMax, boolean useMax)
	{				
		TreeNode<T> retNode = null;
		if (depthMax == 0 || children.size() == 0) {			
			retNode = this;
			return retNode;
		}
		else 
		{
			if (useMax)		// Maximize
			{
				for(int i = 0; i < children.size(); i++)
				{
					TreeNode<T> tempNode = children.get(i).getMinMax(depthMax - 1, false);										
					retNode = max(retNode,tempNode);
				}	
			}
			else			// Minimize
			{
				for(int i = 0; i < children.size(); i++)
				{
					TreeNode<T> tempNode = children.get(i).getMinMax(depthMax - 1, true);
					retNode = min(retNode,tempNode);
				}	
			}			
			return retNode;
		}	
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> getMinMaxPruning(int depthMax,boolean useMax, TreeNode<T> alpha, TreeNode<T> beta)
	{
		
		
		TreeNode<T> retNode = null;
		if (depthMax == 0 || children.size() == 0) {			
			retNode = this;
			return retNode;
		}
		else 
		{
			if (useMax)		// Maximize
			{
				for(int i = 0; i < children.size(); i++)
				{
					TreeNode<T> tempNode = children.get(i).getMinMaxPruning(depthMax - 1, false,alpha,beta);
					retNode = max(retNode,tempNode);
					alpha = max(alpha,retNode);
					if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
						return retNode;
					}
				}	
			}
			else			// Minimize
			{
				for(int i = 0; i < children.size(); i++)
				{
					TreeNode<T> tempNode = children.get(i).getMinMaxPruning(depthMax - 1, true,alpha,beta);
					retNode = min(retNode,tempNode);
					alpha = min(alpha,retNode);
					if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
						return retNode;
					}
				}	
			}			
			return retNode;
		}		
	}
	
	//--------------------------------------------------------------------------------------------------------------
	
	public TreeNode<T> getMinMaxPruningIterative(int depthMax,boolean useMax, TreeNode<T> alpha, TreeNode<T> beta, HashMap<Integer,Integer> transitionTable)
	{
		TreeNode<T> retNode = null;
		
		if (transitionTable.get(this.hashCode()) != null) 
		{
			if (transitionTable.get(this.hashCode()) == -1)
			{
				retNode = this;
				return retNode;
			}
		} 
		else 
		{
			if (depthMax == 0 || children.size() == 0) 
			{			
				transitionTable.put(this.hashCode(), -1);
				retNode = this;
				return retNode;
			}
		}
		
		if (useMax)		// Maximize
		{
			for(int i = 0; i < children.size(); i++)
			{
				TreeNode<T> tempNode = children.get(i).getMinMaxPruningIterative(depthMax - 1, false,alpha,beta,transitionTable);
				
				if (transitionTable.get(children.get(i).hashCode()) != null) 
				{
					if (transitionTable.get(children.get(i).hashCode()) == 0)
					{
						retNode = max(retNode,tempNode);
						alpha = max(alpha,retNode);
						if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
							return retNode;
						}
					}
					else if (transitionTable.get(children.get(i).hashCode()) == 1)
					{
						continue;
					}
				}
				else
				{
					retNode = max(retNode,tempNode);
					alpha = max(alpha,retNode);
					if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
						transitionTable.put(transitionTable.get(children.get(i).hashCode()), 0);
						return retNode;
					} else {
						transitionTable.put(transitionTable.get(children.get(i).hashCode()), 1);	
					}
				}
				
			}	
		}
		else			// Minimize
		{
			for(int i = 0; i < children.size(); i++)
			{
				TreeNode<T> tempNode = children.get(i).getMinMaxPruningIterative(depthMax - 1, false,alpha,beta,transitionTable);
				
				if (transitionTable.get(children.get(i).hashCode()) != null) 
				{
					if (transitionTable.get(children.get(i).hashCode()) == 0)
					{
						retNode = min(retNode,tempNode);
						alpha = min(alpha,retNode);
						if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
							return retNode;
						}
					}
					else if (transitionTable.get(children.get(i).hashCode()) == 1)
					{
						continue;
					}
				}
				else
				{
					retNode = min(retNode,tempNode);
					alpha = min(alpha,retNode);
					if (beta == null || ((Comparable)beta.data).compareTo((Comparable)alpha.data) <= 0) {
						transitionTable.put(transitionTable.get(children.get(i).hashCode()), 0);
						return retNode;
					} else {
						transitionTable.put(transitionTable.get(children.get(i).hashCode()), 1);	
					}
				}
				
			}	
		}			
		return retNode;
			
	}
	
	//--------------------------------------------------------------------------------------------------------------

	public int size()
	{
		int retInt = 0;
		for(int i = 0; i < children.size(); i++)
		{
			retInt += children.get(i).size();
		}
		return retInt + 1;
	}
	
	//--------------------------------------------------------------------------------------------------------------		
	
	public TreeNode<T> max(TreeNode<T> a, TreeNode<T> b){
		if (a == null) 
		{
			return b;
		} 
		else if (b == null) 
		{
			return a;
		}
		
		if (((Comparable)a.getData()).compareTo((Comparable)b.getData()) >= 0) 
		{
			return a;
		} 
		else 
		{
			return b;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------	

	public TreeNode<T> min(TreeNode<T> a, TreeNode<T> b){
		if (a == null) 
		{
			return b;
		} 
		else if (b == null) 
		{
			return a;
		}
		
		if (((Comparable)a.getData()).compareTo((Comparable)b.getData()) <= 0) 
		{
			return a;
		} 
		else 
		{
			return b;
		}
	}
	
	//--------------------------------------------------------------------------------------------------------------	

	//--------------------------------------------------------------------------------------------------------------	

	//--------------------------------------------------------------------------------------------------------------	

	//--------------------------------------------------------------------------------------------------------------	

	//--------------------------------------------------------------------------------------------------------------	

	//--------------------------------------------------------------------------------------------------------------	

	
	//--------------------------------------------------------------------------------------------------------------	
	
}	// End of class