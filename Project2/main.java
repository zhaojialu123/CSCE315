import java.util.Scanner;

public class main {
	static Scanner input = new Scanner(System.in);
	public static ArtificialIntelligence AI = new ArtificialIntelligence(ArtificialIntelligence.Difficulty.hard);
	
	public static void main(String args[])
	{
		MVCController con = new MVCController();		
	}
	
	static void printBoard(GameBoard board)
	{
		System.out.print(board.at(13) + " | ");
		for (int i = 12; i > 6; i--)
		{
			System.out.print(board.at(i) + " ");
		}
		System.out.println( "| " + board.at(6));
		System.out.print("  | ");
		for (int i = 0; i < 6; i++)
		{
			System.out.print(board.at(i) + " " );
		}
		System.out.println("|  ");
		System.out.println("    0 1 2 3 4 5" ); 
	}
}

