// game_end

import javax.swing.*;
import java.awt.event.*;

import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class game_end extends JFrame
{
	// declare the last panel 
	JPanel last_panel;

	// declare all instructions we need 
	JLabel end_instructions;
	JLabel win_instructions;
	JLabel lose_instructions;
	JLabel score;

	// declare all buttons we need 
	JButton restart_button;
	JButton quit_button;

	//constructor
	public game_end()
	{
		last_panel = new JPanel();

		setSize(800, 500);

		last_panel.setLayout(null); // remove default setting 

		win_instructions = new JLabel("Congraduations! You winned!");
		lose_instructions = new JLabel("Sorry! You lost!");
		score = new JLabel("The score you got is:");
		end_instructions = new JLabel("Do you want to start a new game?");


		last_panel.add(win_instructions);
		last_panel.add(lose_instructions);
		last_panel.add(end_instructions);

		win_instructions.setLocation(300,30);
        win_instructions.setSize(300,100);
        score.setLocation(300,80);
        score.setSize(250,100);
        end_instructions.setLocation(300,150);
        end_instructions.setSize(250,100);


        restart_button = new JButton("restart");
		restart_button.setBounds(300,300,80,50);
        last_panel.add(restart_button);

        quit_button = new JButton("Quit");
        quit_button.setBounds(450,300,80,50);
        last_panel.add(quit_button);
        quit_button.addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        });

        last_panel.validate();
        add(last_panel);

	}

	public static void main(String[] args)
    {
        game_end ge = new game_end();
        ge.setVisible(true);
    }
}