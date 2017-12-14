public class House
{
	
	private House next;
	private House across;
	private House store;
	private int stones;
	
	public House()
	{
		this.next = null;
		this.across = null;
		this.store = null;
		this.stones = 4;
	}
	
	public House(int stones) 
	{
		this.next = null;
		this.across = null;
		this.store = null;
		this.stones = stones;
	}
	
	public void setNext(House next)
	{
		this.next = next;
	}
	
	public void setAcross(House across)
	{
		this.across = across;
	}
	
	public void setStore(House store)
	{
		this.store = store;
	}
	
	public int getStones() 
	{
		return this.stones;
	}
	
	public void setStones(int stones) 
	{
		this.stones = stones;
	}
	
	//the move sibliings recurse to cascade the games movements and return 
	//true if the player has earned another turn
	public boolean move()
	{
		if(this.stones != 0)
		{
			int remainingStones = this.stones;
			this.stones = 0;
			return this.next.moveHelper(remainingStones, this.store);
		}
		return true;
	}
	
	public Pair<Integer,Boolean> fakeMove()	// returns <score,gets another move>
	{
		int retInt = 0;
		boolean retBool = false;
		
		int tempInt = stones;
		
		House endHouse = this;
		
		while(tempInt != 0) 
		{
			endHouse = endHouse.next;
			if (!(endHouse.across == null && endHouse.across != this.store)) 
			{
				tempInt--;
			}
		}
		

		
		if (endHouse.across != null) 
		{
			if (endHouse.stones == 0) {
				retInt = endHouse.across.stones + 1;
				retBool = true;
			}				
		}
		else
		{
			retInt = 1;
			retBool = true;
		}		
		
		return new Pair<Integer,Boolean>(retInt,retBool);
	}
	
	public boolean moveHelper(int passStones, House store)
	{
		
		if (this.across == null && this != store)
		{
			return this.next.moveHelper(passStones, store);
		}
		
		--passStones;
		this.stones++;
		if (passStones != 0)
		{
			return this.next.moveHelper(passStones, store);
		}
		else if (this.across == null)
		{
			return true;
		}
		if (this.stones == 1 && this.store == store)
		{
			this.steal(this.across, store);
			this.stones--;
			return false;
		}
		return false;
	}
	
	//when we land on an empty space the houes across from this one had all
	//of its stones stole and placed in the store
	private void steal(House victim, House robber)
	{
		robber.setStones( (this.stones + victim.getStones()) + this.store.getStones() );
		victim.setStones(0);
	}
	
	public void end(int total)
	{
		total += this.stones;
		if (this.next.across != null)
		{
			this.stones = 0;
			this.next.end(total);
		}
		else 
		{
			this.store.setStones(this.store.getStones() + total);
		}
	}
}
