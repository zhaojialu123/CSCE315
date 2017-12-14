import java.net.*;
import java.util.*;
import java.io.*;

public class Connection {

	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	
	public Connection (Socket _socket) {
		try
		{	
			socket = _socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e)
		{
			System.out.println("error in Connection()");
		}
	}
	
	public Connection (String hostName, int portNumber) {
		try
		{
			socket = new Socket(hostName, portNumber);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		catch (IOException e)
		{
			System.out.println("error in Connection()");
		}
	}
	
	public String read() {
		try
		{
			return in.readLine();
		}
		catch (IOException e)
		{
			System.out.println("error in Connection()");
			return null;
		}
	}
	
	public void write(String message) {
		//try
		//{
			out.println(message);
		//}
		//catch (IOException e)
		//{
			//System.out.println("error in Connection()");
		//}
	}
	
	public boolean ready() {
		try
		{
			return in.ready();
		}
		catch (IOException e)
		{
			System.out.println("error in Connection()");
			return false;
		}
	}
	
	
	
	

}
