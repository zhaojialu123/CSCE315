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

public class menu extends JFrame
{
    // define all panels we need
    JPanel menu_panel;
    JPanel mode_panel;
    JPanel choice_panel;
    JPanel seeds_panel;
    JPanel seeds_number;
    JPanel start_panel;
    
    // define all labels we need
    JLabel game_name;
    JLabel game_welcome;
    JLabel game_instruction;
    JLabel mode_choice;
    JLabel holes_choice;
    JLabel seeds_choice;
    JLabel seeds;
    JLabel start_words;
    
    // define all Buttons we need
    JButton next_button;
    /*JButton quit_button;*/
    
    private JButton[] allbuttons;
    
    /*JButton server;
    JButton client;
    JButton four_holes;
    JButton five_holes;
    JButton six_holes;
    JButton seven_holes;
    JButton eight_holes;
    JButton nine_holes;
    JButton fixed;
    JButton random;
    JButton one;
    JButton two;
    JButton three;
    JButton four;
    JButton five;
    JButton six;
    JButton seven;
    JButton eight;
    JButton nine;
    JButton ten;*/

    
    // setting for timer
    JButton start1;
    JLabel label;
    JLabel timer1;
    TimerListener listener;
    int count; // totaly time is 150s
    Timer javaTimer;

    
    
    public menu()
    {
        // initalize all the panels
        menu_panel = new JPanel();
        mode_panel = new JPanel();
        choice_panel = new JPanel();
        seeds_panel = new JPanel();
        seeds_number = new JPanel();
        start_panel = new JPanel();
        
        // set the size for panels
        setSize(800, 500);
        
        // remove all the default settings for panels
        menu_panel.setLayout(null);
        mode_panel.setLayout(null);
        choice_panel.setLayout(null);
        seeds_panel.setLayout(null);
        seeds_number.setLayout(null);
        start_panel.setLayout(null);
        
        // declare all the labels
        game_name = new JLabel("MANCALA");
        game_welcome = new JLabel("Welcome to MANCALA game!!");
        game_instruction = new JLabel("Please follow the instructions to make your choices!");
        mode_choice = new JLabel("which mode do you want?");
        holes_choice = new JLabel("How many holes do you want?");
        seeds_choice = new JLabel("Which way do you want to use for seeds distribution?");
        seeds = new JLabel("How many seeds do you want for each holes?");
        start_words = new JLabel("Are you ready to start?");
        
        // add all labels to their panels
        menu_panel.add(game_name);
        menu_panel.add(game_welcome);
        menu_panel.add(game_instruction);
        mode_panel.add(mode_choice);
        choice_panel.add(holes_choice);
        seeds_panel.add(seeds_choice);
        seeds_number.add(seeds);
        start_panel.add(start_words);
        
        // set the locations and size to all labels
        game_name.setLocation(350,30);
        game_name.setSize(300,100);
        game_welcome.setLocation(300,80);
        game_welcome.setSize(250,100);
        game_instruction.setLocation(220,140);
        game_instruction.setSize(350,100);
        mode_choice.setLocation(300,50);
        mode_choice.setSize(350,100);
        holes_choice.setLocation(300,80);
        holes_choice.setSize(250,100);
        seeds_choice.setLocation(200,80);
        seeds_choice.setSize(400,100);
        seeds.setLocation(200,80);
        seeds.setSize(400,100);
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
                mode_panel.add(allbuttons[0]);
                add(mode_panel);
                mode_panel.revalidate();
                mode_panel.repaint();
            }
        });
        
        allbuttons = new JButton[22];
        
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
        allbuttons[1] = new JButton("server mode");
        allbuttons[1].setBounds(250,150,100,50);
        mode_panel.add(allbuttons[1]);
        allbuttons[1].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
               getContentPane().removeAll();
            }
        });
        
        allbuttons[2] = new JButton("client mode");
        allbuttons[2].setBounds(450,150,100,50);
        mode_panel.add(allbuttons[2]);
        allbuttons[2].addActionListener(new ActionListener()
                                 {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                choice_panel.add(allbuttons[0]);
                add(choice_panel);
                choice_panel.revalidate();
                choice_panel.repaint();
                
            }
        });
        
        //different hole buttons
        allbuttons[3] = new JButton("4");
        allbuttons[3].setBounds(100,200,50,50);
        choice_panel.add(allbuttons[3]);
        // action lstener for four_holes
        allbuttons[3].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        allbuttons[4] = new JButton("5");
        allbuttons[4].setBounds(200,200,50,50);
        choice_panel.add(allbuttons[4]);
        allbuttons[4].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        allbuttons[5] = new JButton("6");
        allbuttons[5].setBounds(300,200,50,50);
        choice_panel.add(allbuttons[5]);
        allbuttons[5].addActionListener(new ActionListener()
                                    {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        allbuttons[6] = new JButton("7");
        allbuttons[6].setBounds(400,200,50,50);
        choice_panel.add(allbuttons[6]);
        allbuttons[6].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        allbuttons[7] = new JButton("8");
        allbuttons[7].setBounds(500,200,50,50);
        choice_panel.add(allbuttons[7]);
        allbuttons[7].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        allbuttons[8] = new JButton("9");
        allbuttons[8].setBounds(600,200,50,50);
        choice_panel.add(allbuttons[8]);
        allbuttons[8].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        // different stones buttons
        allbuttons[9] = new JButton("Fixed");
        allbuttons[9] .setBounds(250,200,80,80);
        seeds_panel.add(allbuttons[9] );
        allbuttons[9].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });

        
        allbuttons[10]  = new JButton("Randomly");
        allbuttons[10] .setBounds(450,200,80,80);
        seeds_panel.add(allbuttons[10] );
        
        // stones number buttons
        allbuttons[11] = new JButton("1");
        allbuttons[11].setBounds(50,200,30,30);
        seeds_number.add(allbuttons[11]);
        allbuttons[11].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        
        allbuttons[12]= new JButton("2");
        allbuttons[12].setBounds(120,200,30,30);
        seeds_number.add(allbuttons[12]);
        allbuttons[12].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        allbuttons[13] = new JButton("3");
        allbuttons[13].setBounds(190,200,30,30);
        seeds_number.add(allbuttons[13]);
        allbuttons[13].addActionListener(new ActionListener()
		    {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        allbuttons[14] = new JButton("4");
        allbuttons[14].setBounds(260,200,30,30);
        seeds_number.add(allbuttons[14]);
        allbuttons[14].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
        

        allbuttons[15] = new JButton("5");
        allbuttons[15].setBounds(330,200,30,30);
        seeds_number.add(allbuttons[15]);
        allbuttons[15].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        allbuttons[16] = new JButton("6");
        allbuttons[16].setBounds(400,200,30,30);
        seeds_number.add(allbuttons[16]);
        allbuttons[16].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        allbuttons[17] = new JButton("7");
        allbuttons[17].setBounds(470,200,30,30);
        seeds_number.add(allbuttons[17]);
        allbuttons[17].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });

        allbuttons[18] = new JButton("8");
        allbuttons[18].setBounds(540,200,30,30);
        seeds_number.add(allbuttons[18]);
        allbuttons[18].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
        
        allbuttons[19] = new JButton("9");
        allbuttons[19].setBounds(610,200,30,30);
        seeds_number.add(allbuttons[19]);
        allbuttons[19].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
        
        allbuttons[20] = new JButton("10");
        allbuttons[20].setBounds(680,200,30,30);
        seeds_number.add(allbuttons[20]);
        allbuttons[20].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
		
        setLocationRelativeTo(null);
        
        // settings for timer
        count = 150; // totaly time is 150s
        label = new JLabel("Remaining Time:");
        timer1 = new JLabel("150s");
        javaTimer = new Timer(1000, new TimerListener());
        javaTimer.setRepeats(true);

        label.setLocation(600,30);
        label.setSize(300,100);
        timer1.setLocation(710,30);
        timer1.setSize(300,100);

        
        start1 = new JButton("Start");
        start1.setBounds(300,300,50,50);
		
		allbuttons[21] = start1;
        
        start_panel.add(label);
        start_panel.add(timer1);
        start_panel.add(start1);
        
        start1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                javaTimer.start();
            }
        });
    }
	
	public void addButtonListener(int i, ActionListener listener)
	{
		allbuttons[i].addActionListener(listener);
	}
       
    /*
    public static void main(String[] args)
    {
        menu m = new menu();
        m.setVisible(true);
    }
	*/
	

    // works for timer 
    public class TimerListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (count == 0) {
                javaTimer.stop();
            } else {
                count--;
                timer1.setText("" + count + "s");
            }
        }
    }

 
}
