public class GameThread extends Thread {

	private Connection connection;
	private GameManager gm;
	private Lock lock;
	private Condition empty;

	public GameThread(Socket clientSocket, GameManager _gm)
	{
		connection = new Connection(clientSocket);
		gm = _gm;
		connection.write("memes");
	}
	
	

	@override
	public void run()
	{
		for(;;) 
		{
			//grab a command and handle the command
			String command =  connection.read();
			handle(command)
		}
	}
	
	public void handle(string command)
	{
		if (command != "READY" && command != "OK") {
			//command from client is a move 
			if (command == "P") {
				gm.pieRule();
			}
			else 
			{
				ArrayList<int> moves = getMoves(command);
				for (int move : moves) 
				{
					gm.makeMove(move);
				}
			}
		}
	} 
		
