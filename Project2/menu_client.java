import javax.swing.*;
import java.awt.event.*;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.JOptionPane;

public class menu_client extends JFrame
{
    private boolean isAI;
    // define all panels we need
    JPanel menu_panel;
    JPanel model_panel;
    JPanel start_panel;
    
    // define all labels we need
    JLabel game_name;
    JLabel game_welcome;
    JLabel game_instruction;
    JLabel model_choice;
    JLabel start_words;
    
    String IP_address = JOptionPane.showInputDialog("Please input your IP address");
    
    // define all Buttons we need
    JButton next_button;
    /*JButton quit_button;*/
    
    private JButton[] allbuttons;
    

    
    // setting for timer
    JButton start1;
    

    
    
    public menu_client()
    {
        // initalize all the panels
        menu_panel = new JPanel();
        model_panel = new JPanel();
        start_panel = new JPanel();
        
        // set the size for panels
        setSize(800, 500);
        
        // remove all the default settings for panels
        menu_panel.setLayout(null);
        model_panel.setLayout(null);
        start_panel.setLayout(null);
        
        // declare all the labels
        game_name = new JLabel("MANCALA");
        game_welcome = new JLabel("Welcome to MANCALA game!!");
        game_instruction = new JLabel("Please follow the instructions to make your choices!");
        model_choice = new JLabel("which model do you want?");
        start_words = new JLabel("Are you ready to start?");
        
        // add all labels to their panels
        menu_panel.add(game_name);
        menu_panel.add(game_welcome);
        menu_panel.add(game_instruction);
        model_panel.add(model_choice);
        start_panel.add(start_words);
        
        // set the locations and size to all labels
        game_name.setLocation(350,30);
        game_name.setSize(350,150);
        game_welcome.setLocation(300,80);
        game_welcome.setSize(300,150);
        game_instruction.setLocation(220,140);
        game_instruction.setSize(350,150);
        model_choice.setLocation(300,50);
        model_choice.setSize(400,150);
        start_words.setLocation(350,80);
        start_words.setSize(400,100);

        // add panel to our frame
        menu_panel.validate();
        add(menu_panel);
        
        // next button for going next step
        next_button = new JButton("Next");
        next_button.setBounds(300,300,50,50);
        menu_panel.add(next_button);
        // action lstener for next_button
        next_button.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                model_panel.add(allbuttons[0]);
                add(model_panel);
                model_panel.revalidate();
                model_panel.repaint();
            }
        });
        
        
        allbuttons = new JButton[4];
        
        // quit button to exit the game
        allbuttons[0] = new JButton("Quit");
        allbuttons[0].setBounds(450,300,50,50);
        menu_panel.add(allbuttons[0]);
        allbuttons[0].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });
        

        // different mode buttons
        allbuttons[1] = new JButton("Computer");
        allbuttons[1].setBounds(250,150,100,50);
        model_panel.add(allbuttons[1]);
        allbuttons[1].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isAI = true;
               getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
        
        allbuttons[2] = new JButton("Human");
        allbuttons[2].setBounds(450,150,100,50);
        model_panel.add(allbuttons[2]);
        allbuttons[2].addActionListener(new ActionListener()
                                 {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                isAI =false;
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
                
            }
        });
        
        setLocationRelativeTo(null);
        

        
        start1 = new JButton("Start");
        start1.setBounds(300,300,50,50);
		
		allbuttons[3] = start1;
        start_panel.add(start1);
        
        start1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //javaTimer.start();
                //getContentPane().removeAll();
                //this.setVisible(false);
                Client c = new Client(isAI, IP_address);
                c.run();
            }
        });
        
        for (int i = 0; i < 4; i++)
        {
			allbuttons[i].setBorder(null);
		}
		next_button.setBorder(null);
    }
	
	public void addButtonListener(int i, ActionListener listener)
	{
		allbuttons[i].addActionListener(listener);
	}
       

    public static void main(String[] args)
    {
        menu_client m = new menu_client();
        m.setVisible(true);
    }
	

 
}
