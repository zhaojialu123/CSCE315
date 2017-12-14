import java.net.*;
import java.util.*;
import java.lang.Integer;
import java.awt.event.*;
import javax.swing.Timer;

public class Client
{
	//private static String IP;
	private Connection connection;
	private GameBoard board;
	private GUI gui;
	private int numHoles;
	private Timer clientTimer;
    private int count;
	private AI ai;
	private boolean myTurn;
	private Queue<Integer> moves;
	private int time;
	
	public Client(boolean isAI, String IP)
	{
        connection = new Connection(IP, 7896);
		gui = null;
		moves = new LinkedList<Integer>();
		if (isAI)
		{
			ai = new AI(5);
		}
		else
		{
			ai = null;
			
		}
		
	}
	
	public void run ()
	{
		count = 150;
		clientTimer = new Timer(1000, new TimerListener());
        clientTimer.setRepeats(true);
		//clientTimer.start();
		for (;;)
		{
			String command = connection.read();
			if (command != null)
			{
				System.out.println("Command is:" + command + ":");
				if (command.startsWith("INFO"))
				{
					setUp(command);
				}
				else if (command.equals("ILLEGAL") || command.equals("LOSER") ||
						 command.equals("WINNER") || command.equals("TIE") || command.equals("TIME"))
				{
					if ((command.equals("ILLEGAL") || command.equals("LOSER") || command.equals("TIME")) && gui != null) 
					{
						gui.setStatus("Loser");
					}
					else if (command.equals("WINNER") && gui != null)
					{
						gui.setStatus("Winner");
					}
					else if (gui != null)
					{
						gui.setStatus("Tie");
					}
					break;
				}
				else if (!command.equals("WELCOME") && !command.equals("READY") && !command.equals("OK"))
				{
					String delims = "[ ]+";
					String[] tokens = command.split(delims);
					for(String move : tokens)
					{
						int numMove = Integer.parseInt(move);
						board.makeMove(numMove + numHoles + 1);
						if (gui != null)
						{
							gui.update();
							try 
							{
								Thread.sleep(1000);
							}
							catch(InterruptedException e)
							{
								System.out.println("interuppted");
							}
						}
					}
					if (gui != null)
					{						
						gui.setStatus("Your Turn");
					}
					setMyTurn(true);
					connection.write("OK");
				}
			}
			while (isMyTurn()) 
			{
				if (ai != null)
				{
					ai.setBoard(new GameBoard(board));
					ai.fillTree(5, null);
					int move = ai.move();
					moves.add(move);
					boolean again = board.makeMove(move);
					if (!again)
					{
						setMyTurn(false);
					}
					System.out.println("here4 " + move);
				}
			}
			System.out.println(moves.peek() == null);
			if (moves.peek() != null)
			{
				String move = grabInput();
				connection.write(move);
			}
			if (gui != null)
			{
				gui.setStatus("Opponent Turn");
			}
		}
		try
		{
			Thread.sleep(10000);
		}
		catch (InterruptedException e)
		{
			System.out.println("Thread Interrupted");
		}
	}
    
    public class TimerListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e){
            count--;
			gui.updateTime(count);
			System.out.println("timer");
        }
    }
	
	private synchronized boolean isMyTurn()
	{
		return myTurn;
	}
	
	private synchronized void setMyTurn(boolean turn)
	{
		myTurn = turn;
	}
	
	private void setUp(String information)
	{
		System.out.println(information);
		String delims = "[ ]+";
		String[] tokens = information.split(delims);
		int nHoles = Integer.parseInt(tokens[1]);
		int numSeeds = Integer.parseInt(tokens[2]);
		time = Integer.parseInt(tokens[3]) / 1000;
		char first = tokens[4].charAt(0);
		char random = tokens[5].charAt(0);
		numHoles = nHoles;
		
		setMyTurn(first == 'F');
		
		count = time;
		LinkedList<Integer> distro = null;
		if (random == 'R')
		{
			distro = new LinkedList<Integer>();
			for (int i = 0; i < numHoles; i++)
			{
				distro.add(Integer.parseInt(tokens[6 + i]));
			}
		}
		
		board = new GameBoard((random == 'R'), numHoles, numSeeds, distro);
		if (!isMyTurn())
		{
			board.playerMove = 1;
		}
		if (ai == null)
		{			
			gui = new GUI(numHoles);
			gui.start();
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
				System.out.println("Thread Interrupted");
			}
			gui.setStatus(isMyTurn() ? "Your Turn" : "Opponent Turn"); 
			System.out.println("here1");
		}
		connection.write("READY");
	}
	
	private String grabInput()
	{
		Integer currMove = moves.poll();
		String sMoves = "";
		while(currMove != null)
		{
			sMoves += (currMove + " ");
			currMove = moves.poll();
		}
		return sMoves;
	}
	
	public class GUI extends Thread 
	{
		private Board view;
		private int numButtons;
		
		public GUI(int _numButtons)
		{
			view = null;
			numButtons = _numButtons;
		}
		
		public void updateTime(int count)
		{
			view.updatetime(count);
		}
		
		public void setStatus(String status)
		{
			if (view != null) 
			{
				view.update_stats(status);
			}
		}
		
		@Override
		public void run()
		{
			view = new Board(numButtons,count);
			for (int i = 0; i < numButtons; i++)
			{
				view.addButtonListener(i, new ButtonListener(i));
			}
			view.setVisible(true);
			view.update(board);
			System.out.println("Here");
		}
		
		public void update()
		{
			view.update(board);
		}
		
		
		public class ButtonListener implements ActionListener
		{
			private int move;
		
			public ButtonListener(int i)
			{
				super();
				move = i;
			}
			
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(isMyTurn())
				{
					moves.add(move);
					if (!board.makeMove(move)) 
					{
						setMyTurn(false);
					}
					view.update(board);
				}
			}
		}
	}
	
	public static void main (String[] args)
	{
		Client theClient = new Client(args[0].equals("true"), args[1]);
		theClient.run();
	}
}
