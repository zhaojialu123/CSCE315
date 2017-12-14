/*
@Jialu Zhao
*/
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.imageio.ImageIO;

public class Board extends JFrame
{
    // declartions
    image_panel jp;
    JLabel j_board;

    // Button for players
    private JButton[] playerButtons;
    
    // box for computers
    private JButton[] computerButtons;
    
    
    // box for store place
    JButton jleft;
    JButton jright;
    JLabel  stats;
    JLabel label;
    JLabel timer1; // shown timer

    
    public static int DIST = 12;


    public Board(int number, int timer_count)
    {
        // definitons
    	jp = new image_panel(null); // remove default panel
        j_board = new JLabel();
        
        // Button for players
    	playerButtons = new JButton[number];
    	for (int i = 0; i < playerButtons.length; i++)
    	{
			playerButtons[i] = new JButton("P");
            // set positions
			playerButtons[i].setBounds(155 + i * (12-number)*DIST, 205, 36, 36);
			jp.add(playerButtons[i]);
			
		}
        
        // Button for computer
        computerButtons = new JButton[number];
        for (int i = 0; i < computerButtons.length; i++)
        {
            computerButtons[i] = new JButton("C");
            // set positions
            computerButtons[i].setBounds(155 + i * (12-number)*DIST, 100, 36, 36);
            jp.add(computerButtons[i]);
            
        }
		
        // box for store place
        jleft = new JButton("L");
        jright = new JButton("R");
        stats = new JLabel("stats");
        label = new JLabel("Remaining Time:");
        timer1 = new JLabel("150s");
        timer1.setText("" + timer_count + "s");
        // set panel
        setTitle("Board");
        setVisible(true);
        setSize(800, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // set position for store box
        jleft.setBounds(56,140,70,70);
        jright.setBounds(600,140,70,70);
        stats.setLocation(350,300);
        stats.setSize(300,100);
        label.setLocation(600,3);
        label.setSize(300,100);
        timer1.setLocation(720,3);
        timer1.setSize(300,100);
        
        // add all labels to the panel 
        jp.add(j_board);

        jp.add(jleft);
        jp.add(jright);
        jp.add(stats);
        jp.add(label);
        jp.add(timer1);
        
        jp.validate();
        // add the panel to frame window
        add(jp);
    }
    
    public void update_stats(String status)
    {
        stats.setText(status);
    }
    
    public void updatetime(int count)
    {
         timer1.setText("" + count + "s");
    }
    
    public void addButtonListener(int num, ActionListener listener)
    {
		playerButtons[num].addActionListener(listener);
	}
    
    // update the information after receiving some information from game manager 
    public void update(GameBoard gameBoard)
    {
        
        // update player button's information
        for(int i = 0; i < playerButtons.length; i++)
        {
			playerButtons[i].setText(Integer.toString(gameBoard.at(i)));
			playerButtons[i].setBorder(null);
		}
        
        // update computer button's information
        for(int i = 0; i < playerButtons.length; i++)
        {
            computerButtons[i].setText(Integer.toString(gameBoard.at(12 -i)));
            computerButtons[i].setBorder(null);
        }
		//System.out.println(arr6 + " " + arr13);
        
        // update store box information
        jleft.setText(Integer.toString(gameBoard.at(playerButtons.length*2+1)));
        jleft.setBorder(null);
        jright.setText(Integer.toString(gameBoard.at(playerButtons.length)));
        jright.setBorder(null);
    }
	
    public static void main(String[] args)
    {
        Board b = new Board(6,150);
		b.setVisible(true);
    }
    
}
