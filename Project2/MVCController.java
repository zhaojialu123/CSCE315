import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MVCController 
{
	private menu m;
	private int holeNum;
	private int seedNum;
	private boolean random;
	
	public MVCController()
	{
		m = new menu();
		
		
		m.addButtonListener(0, new QuitListener());
		
		for (int i = 1; i <  3; i++) 
		{
			m.addButtonListener(i, new TypeListener(i - 1));
		}
		
		for (int i = 3; i < 9; i++)
		{
			m.addButtonListener(i, new HoleListener(i + 1));
		}
		
		for (int i = 9; i <  10; i++)
		{
			m.addButtonListener(i, new GameTypeListener(i - 9));
		}
		
		for (int i = 11; i < 21; i++)
		{
			m.addButtonListener(i, new SeedListener(i - 10));
		}
		
		m.addButtonListener(21, new StartListener());
		m.setVisible(true);
	}
	
	class QuitListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			m.setVisible(false);
			m.dispose();
		}
	}
	
	class TypeListener implements ActionListener
	{
		private boolean isClient;
		
		public TypeListener(int i)
		{
			isClient = i == 1;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (isClient)
			{
				m.setVisible(false);
				m.dispose();
				Client client =  new Client();
				client.run();
			}
		}
	}
	
	class HoleListener implements ActionListener
	{
		private int numHoles;
		
		public HoleListener(int i)
		{
			numHoles = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			holeNum = numHoles;
		}
	}
	
	class GameTypeListener implements ActionListener
	{
		private boolean isRandom;
		
		public GameTypeListener(int i)
		{
			isRandom = i == 1;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			random = isRandom;
		}
	}
	
	class SeedListener implements ActionListener
	{
		private int numSeeds;
		
		public SeedListener(int i)
		{
			numSeeds = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			seedNum = numSeeds;
		}
	}
	
	class StartListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
			m.setVisible(false);
			m.dispose();
			LinkedList<int> nums = null;
			if (isRandom) 
			{
				Random rand = new Random();
				nums = new LinkedList<int>();
				for (int i = 0; i < numSeeds / 2; i++)
				{
					seeds0 = rand.nextInt(numSeeds * 2) + 1
					seeds1 = (numSeeds * 2) - seed0;
					nums.add(i, seeds);
					nums.add(nums.length - i, seeds1);
					
				}
			}
			Server server = new Server(holeNum, seedNum, random, nums);
			Server.run();
		}
	}
	
	class ButtonListener implements ActionListener
	{
		private int move;
		
		public ButtonListener(int i)
		{
			super();
			move = i;
		}
		
		//player actions drive the entire game, we can change this to be more
		//versitile to differant player types later since this assumes that there
		//will always be a humanplayer who goes first and a computer player who goes second.
		//this is just a super adhok idea to get the game working;
		@Override
		public void actionPerformed(ActionEvent e)
		{
			//player turns dont have a loop since they will press differant buttons
			if(gm.turn() == 0)
			{
				gm.makeMove(move);
				view.update(gm.board());
			}
			//this is in a loop since the AI needs to keep making move when its their turn
			while(gm.turn() == 1)
			{
				gm.makeMove();
				view.update(gm.board());
				if(gm.endGame())
				{
					break;
				}
			}
		}
	}
}
