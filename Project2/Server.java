import java.net.*;
import java.lang.Thread;
import java.util.*;
import java.lang.Character.*;
import java.io.IOException;
import java.awt.event.*;
import javax.swing.Timer;

public class Server
{
	private ServerSocket serverSocket;
	private GameBoard board;
	private int playerNum;
	private int holeNum;
	private int count;
	private int seedNum;
	private boolean random;
	private int mode;
	private Timer serverTimer;
	private LinkedList<Integer> seedDistro;
	private Queue<String> otherThreadMoves;
	private int currentPlayer;
	private GUI humanPlayer;
	private ArtInt ai;
	private int seconds;
	
	public Server(int _holeNum, int _seedNum, boolean _random, LinkedList<Integer> nums, int _mode, int _seconds)
	{
		try
		{
			serverSocket = new ServerSocket(7896);
			holeNum = _holeNum;
			seedNum = _seedNum;
			random = _random;
			seedDistro = nums;
			if (seedDistro == null)
			{
				board = new GameBoard(random, holeNum, seedNum, null);
			}
			else 
			{
				board = new GameBoard(random, holeNum, seedNum, new LinkedList(seedDistro));
			}
			playerNum = 0;
			otherThreadMoves = new LinkedList<String>();
			mode = _mode;
			currentPlayer = 0;
			humanPlayer = null;
			ai = null;
			seconds = _seconds;
		}
		catch(IOException e)
		{
			System.out.println("error");
		}
	}
	public void run()
	{
		try 
		{	
			if (mode == 0) //2 clients
			{	
				GameThread first = new Server.GameThread(serverSocket.accept(), playerNum++, holeNum, seedNum, random, seedDistro);
				GameThread second = new Server.GameThread(serverSocket.accept(), playerNum++, holeNum, seedNum, random, seedDistro);
				first.setOpponent(second);
				second.setOpponent(first);
				first.start();
				second.start();
			}
			else if (mode == 1)//human mode
			{
				System.out.println("mode 1");
				humanPlayer = new GUI(holeNum);
				GameThread client = new Server.GameThread(serverSocket.accept(), 1, holeNum, seedNum, random, seedDistro);
				System.out.println("client connected");
				humanPlayer.setOpponent(client);
				humanPlayer.start();
				client.start();
			}
			else //AI mode
			{
				ai = new ArtInt();
				GameThread client = new Server.GameThread(serverSocket.accept(), 1, holeNum, seedNum, random, seedDistro);
				ai.setOpponent(client);
				ai.start();
				client.start();
			}
		}
		catch (IOException e)
		{
			System.out.println("error in Server.run()");
		}
		
	}
	
	public class ArtInt extends Thread
	{
		private AI artInt;
		public int playerNum;
		private int count;
		private GameThread opponent;
		public Timer time;
		boolean gameOver;
		
		public ArtInt() 
		{
			artInt = new AI(5);
			count = seconds;
			gameOver = false;
			time = new Timer(1000, new TimerListener());
			time.setRepeats(true);
		}
		
		public class TimerListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				count--;
				if (count <= 0)
				{
					gameOver = true;
					if (currentPlayer == playerNum)
					{
						opponent.connection.write("WINNER");
					}
					else
					{
						opponent.connection.write("TIME");
						opponent.connection.write("LOSER");
					}
				}
			}
		}
		
		public void setOpponent(GameThread _opponent)
		{
			opponent = _opponent;
		}
		
		@Override
		public void run() 
		{
			
			while(!board.gameOver)
			{
				System.out.print("");
				if (currentPlayer == 0)
				{
					artInt.setBoard(new GameBoard(board));
					artInt.fillTree(5, null);
					int move = artInt.move();
					boolean again = board.makeMove(move);
					time.stop();
					otherThreadMoves.add(move + " ");
					if (!again)
					{
						currentPlayer = 1;
						opponent.otherPlayerMoved(otherThreadMoves);
					}
					count = seconds;
					time.start();
				}
			}
			if (board.winner == 0)
			{
				opponent.connection.write("LOSER");
			}
			else if (board.winner == 1)
			{
				opponent.connection.write("WINNER");
			}
			else
			{
				opponent.connection.write("TIE");
			}
		}
	}
	
	public class GUI extends Thread 
	{
		private Board view;
		private int numButtons;
		private GameThread opponent;
		public Timer time;
		
		public GUI(int _numButtons)
		{
			view = null;
			numButtons = _numButtons;
			count = seconds;
			time = new Timer(1000, new TimerListener());
			time.setRepeats(true);
		}
		
		public class TimerListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				count--;
				view.updatetime(count);
				if (count <= 0)
				{
					if (currentPlayer == playerNum)
					{
						view.update_stats("Loser");
						opponent.connection.write("WINNER");
						board.gameOver = true;
					}
					else
					{
						view.update_stats("Winner");
						opponent.connection.write("LOSER");
						board.gameOver = true;
					}
				}
			}
		}
		
		
		
		public void setOpponent(GameThread _opponent)
		{
			opponent = _opponent;
		}
		
		public void updateTime(int count)
		{
			view.updatetime(count);
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
			view.update_stats("Your Turn");
			time.start();
		}
		
		public void update()
		{
			if (currentPlayer == 0)
			{
				view.update_stats("Your Turn");
			}
			else 
			{
				view.update_stats("Opponent Turn");
			}
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
				if(currentPlayer == 0 && !board.gameOver)
				{
					time.stop();
					otherThreadMoves.add(move + " ");
					if (!board.makeMove(move)) 
					{
						view.update_stats("Opponent Turn");
						opponent.otherPlayerMoved(otherThreadMoves);
						currentPlayer = 1;
					}
					if(board.gameOver)
					{
						if (board.winner == 0)
						{
							view.update_stats("Winner");
						}
						else if (board.winner == -1)
						{
							view.update_stats("Tie");
						}
						else 
						{
							view.update_stats("Loser");
						}
					}
					count = seconds;
					view.updatetime(count);
					time.start();
					view.update(board);
				}
			}
		}
	}
	
	
	public synchronized boolean legalMove(int location, GameThread player)
	{
		if (player.playerNum == currentPlayer && board.at(location + (currentPlayer * (holeNum + 1))) != 0)
		{
			boolean bounusTurn = board.makeMove(location + (currentPlayer * (holeNum + 1)));
			if (bounusTurn && mode == 0) // if the mode is 0 we have another client to notify
			{
				otherThreadMoves.add(location + " ");
			}
			else if (mode == 0)
			{
				otherThreadMoves.add(location + " ");
				currentPlayer = (currentPlayer + 1) % 2;
				player.otherPlayerMoved(otherThreadMoves);
			}
			else if (mode == 1) //there is a human with a gui that needs to update
			{
				humanPlayer.time.stop();
				if (!bounusTurn) 
				{
					currentPlayer = (currentPlayer + 1) % 2;
				}
				try
				{
					Thread.sleep(100);
				}
				catch (InterruptedException e)
				{
					System.out.println("Thread interupted");
				}
				humanPlayer.update();
				count = seconds;
				humanPlayer.time.start();
			}
			else
			{
				if (!bounusTurn)
				{
					ai.time.stop();
					currentPlayer = 0;
					System.out.println("here98");
					count = seconds;
					ai.time.start();
				}
			}
			return true;
		}
		return false;
	}
	
	
	public class GameThread extends Thread
	{

		public Connection connection;
		public int playerNum;
		private String info;
		private GameThread opponent;
		private Timer time;

		public GameThread(Socket clientSocket, int _playerNum, int holeNum, int seedNum, boolean random, LinkedList<Integer> seedDistro)
		{
			connection = new Connection(clientSocket);
			playerNum = _playerNum;
			opponent = null;
			if (seedDistro != null)
			{
				info = generateInfo(holeNum, seedNum, random, new LinkedList(seedDistro));
			}
			else
			{
				info = generateInfo(holeNum, seedNum, random, null);
			}
			connection.write("WELCOME");
			count = seconds;
			time = new Timer(1000, new TimerListener());
			time.setRepeats(true);
		}
		
		public class TimerListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				count--;
				if (count <= 0)
				{
					if (currentPlayer == playerNum)
					{
						connection.write("TIME");
						connection.write("LOSER");
						if (opponent != null)
						{
							opponent.connection.write("WINNER");
						}
					}
				}
			}
		}
    
		
		public void setOpponent (GameThread enemy)
		{
			opponent = enemy;
		}
		
		public GameThread getOpponent()
		{
			return opponent;
		}
	
		private String generateInfo(int holeNum, int seedNum, boolean random, LinkedList<Integer> nums) 
		{
			StringBuilder info = new StringBuilder("INFO ");
			info.append(holeNum + " ");
			info.append(seedNum + " ");
			info.append(seconds * 1000 + " ");
			info.append( (playerNum == 0 ? 'F' : 'S') + " ");
			if(random)
			{
				info.append('R' + " ");
				Integer seed = nums.poll();
				while (seed != null)
				{
					info.append(seed + " ");
					seed = nums.poll();
				}
			}
			else
			{
				info.append('S' + " ");
			}
			return info.toString();
		}
	
		private synchronized void otherPlayerMoved(Queue<String> otherMoves)
		{
			StringBuilder moveList = new StringBuilder();
			String current = otherMoves.poll();
			while (current != null)
			{
				System.out.println("here9");
				moveList.append(current);
				current = otherMoves.poll();
			}
			connection.write(moveList.toString());
		}
	

		@Override
		public void run()
		{
			connection.write(info);
			while(!board.gameOver) 
			{
				count = seconds;
				time.start();
				String command =  connection.read();
				time.stop();
				int otherPlayerNum = (playerNum + 1) % 2;
				if (!command.equals("READY") && !command.equals("OK"))
				{
					//command from client is a move 
					if (command.equals("P")) 
					{
						board.makeMove(-1);
						LinkedList<String> moveList = new LinkedList<String>();
						moveList.add("-1");
						opponent.otherPlayerMoved(moveList);
						connection.write("OK");
					}
					else 
					{
						ArrayList<Integer> moves = getMoves(command);
						for (int move : moves) 
						{
							if (!legalMove(move, this))
							{
								connection.write("ILLEGAL");
								break;
							}
						}
						connection.write("OK");
					}
				}
			}
			if (board.winner == playerNum)
			{
				connection.write("WINNER");
			}
			else if (board.winner == -1)
			{
				connection.write("TIE");
			}
			else 
			{
				connection.write("LOSER");
			}
		}

		private ArrayList<Integer> getMoves(String command)
		{
			int start = 0;
			int end = 0;
			boolean streak = false;
			ArrayList<Integer> returnList = new ArrayList<Integer>();
			for (int i = 0; i < command.length(); i++)
			{
				if(Character.isDigit(command.charAt(i)))
				{
					if(streak)
					{
						end++;
					}
					else
					{
						start = i;
						end = i;
						streak = true;
					}
				}
				else
				{
					String sNum = command.substring(start,end + 1);
					if (!sNum.equals(""))
					{
						returnList.add(Integer.parseInt(sNum));
					}
					streak = false;
				}
			}
			return returnList;
		}
	}
    
	public static void main (String[] args)
	{
		Server serv = new Server(6,4,false,null,2,10);
		serv.run();
	}
}


