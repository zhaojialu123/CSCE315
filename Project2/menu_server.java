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
import java.util.*;

public class menu_server extends JFrame
{
    private int _holeNum = 0;
    private int _seedNum = 0;
    private boolean _random;
    private LinkedList<Integer> nums;
    private int _mode;
    private int turn_time;
    // define all panels we need
    JPanel menu_panel;
    JPanel model_panel;
    JPanel choice_panel;
    JPanel seeds_panel;
    JPanel seeds_number;
    JPanel time_panel;
    JPanel start_panel;
    
    // define all labels we need
    JLabel game_name;
    JLabel game_welcome;
    JLabel game_instruction;
    JLabel model_choice;
    JLabel holes_choice;
    JLabel seeds_choice;
    JLabel seeds;
    JLabel time_option;
    JLabel start_words;
    
    // define all Buttons we need
    JButton next_button;
    /*JButton quit_button;*/
    
    private JButton[] allbuttons;
    
    // setting for timer
    JButton start1;
    
    
    public menu_server()
    {
        // initalize all the panels
        menu_panel = new JPanel();
        model_panel = new JPanel();
        choice_panel = new JPanel();
        seeds_panel = new JPanel();
        seeds_number = new JPanel();
        time_panel = new JPanel();
        start_panel = new JPanel();
        
        // set the size for panels
        setSize(800, 500);
        
        // remove all the default settings for panels
        menu_panel.setLayout(null);
        model_panel.setLayout(null);
        choice_panel.setLayout(null);
        seeds_panel.setLayout(null);
        seeds_number.setLayout(null);
        time_panel.setLayout(null);
        start_panel.setLayout(null);
        
        // declare all the labels
        game_name = new JLabel("MANCALA");
        game_welcome = new JLabel("Welcome to MANCALA game!!");
        game_instruction = new JLabel("Please follow the instructions to make your choices!");
        model_choice = new JLabel("which model do you want?");
        holes_choice = new JLabel("How many holes do you want?");
        seeds_choice = new JLabel("Which way do you want to use for seeds distribution?");
        seeds = new JLabel("How many seeds do you want for each holes?");
        time_option = new JLabel("How long do you want for one turn?");
        start_words = new JLabel("Are you ready to start?");
        
        // add all labels to their panels
        menu_panel.add(game_name);
        menu_panel.add(game_welcome);
        menu_panel.add(game_instruction);
        model_panel.add(model_choice);
        choice_panel.add(holes_choice);
        seeds_panel.add(seeds_choice);
        seeds_number.add(seeds);
        time_panel.add(time_option);
        start_panel.add(start_words);
        
        // set the locations and size to all labels
        game_name.setLocation(350,30);
        game_name.setSize(300,100);
        game_welcome.setLocation(300,80);
        game_welcome.setSize(250,100);
        game_instruction.setLocation(220,140);
        game_instruction.setSize(350,100);
        model_choice.setLocation(300,50);
        model_choice.setSize(350,100);
        holes_choice.setLocation(300,80);
        holes_choice.setSize(250,100);
        seeds_choice.setLocation(200,80);
        seeds_choice.setSize(400,100);
        seeds.setLocation(200,80);
        seeds.setSize(400,100);
        time_option.setLocation(350,80);
        time_option.setSize(400,100);
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
        
        allbuttons = new JButton[26];
        
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
        allbuttons[1] = new JButton("2 clients");
        allbuttons[1].setBounds(150,150,100,50);
        model_panel.add(allbuttons[1]);
        allbuttons[1].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _mode = 0;
                getContentPane().removeAll();
                choice_panel.add(allbuttons[0]);
                add(choice_panel);
                choice_panel.revalidate();
                choice_panel.repaint();
            }
        });
        
        allbuttons[2] = new JButton("Human mode");
        allbuttons[2].setBounds(300,150,100,50);
        model_panel.add(allbuttons[2]);
        allbuttons[2].addActionListener(new ActionListener()
                                 {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _mode = 1;
                getContentPane().removeAll();
                choice_panel.add(allbuttons[0]);
                add(choice_panel);
                choice_panel.revalidate();
                choice_panel.repaint();
                
            }
        });
        
        allbuttons[3] = new JButton("AI mode");
        allbuttons[3].setBounds(450,150,100,50);
        model_panel.add(allbuttons[3]);
        allbuttons[3].addActionListener(new ActionListener()
                                        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _mode = 2;
                getContentPane().removeAll();
                choice_panel.add(allbuttons[0]);
                add(choice_panel);
                choice_panel.revalidate();
                choice_panel.repaint();
                
            }
        });
        
        
        //different hole buttons
        allbuttons[4] = new JButton("4");
        allbuttons[4].setBounds(100,200,50,50);
        choice_panel.add(allbuttons[4]);
        // action lstener for four_holes
        allbuttons[4].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 4;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        allbuttons[5] = new JButton("5");
        allbuttons[5].setBounds(200,200,50,50);
        choice_panel.add(allbuttons[5]);
        allbuttons[5].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 5;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        
        allbuttons[6] = new JButton("6");
        allbuttons[6].setBounds(300,200,50,50);
        choice_panel.add(allbuttons[6]);
        allbuttons[6].addActionListener(new ActionListener()
                                    {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 6;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        
        allbuttons[7] = new JButton("7");
        allbuttons[7].setBounds(400,200,50,50);
        choice_panel.add(allbuttons[7]);
        allbuttons[7].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 7;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        
        allbuttons[8] = new JButton("8");
        allbuttons[8].setBounds(500,200,50,50);
        choice_panel.add(allbuttons[8]);
        allbuttons[8].addActionListener(new ActionListener()
                                      {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 8;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        
        allbuttons[9] = new JButton("9");
        allbuttons[9].setBounds(600,200,50,50);
        choice_panel.add(allbuttons[9]);
        allbuttons[9].addActionListener(new ActionListener()
                                     {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _holeNum = 9;
                getContentPane().removeAll();
                seeds_number.add(allbuttons[0]);
                add(seeds_number);
                seeds_number.revalidate();
                seeds_number.repaint();
            }
        });
        
        
        
        // stones number buttons
        allbuttons[12] = new JButton("1");
        allbuttons[12].setBounds(50,200,30,30);
        seeds_number.add(allbuttons[12]);
        allbuttons[12].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _seedNum = 1;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        
        allbuttons[13]= new JButton("2");
        allbuttons[13].setBounds(120,200,30,30);
        seeds_number.add(allbuttons[13]);
        allbuttons[13].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 2;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        allbuttons[14] = new JButton("3");
        allbuttons[14].setBounds(190,200,30,30);
        seeds_number.add(allbuttons[14]);
        allbuttons[14].addActionListener(new ActionListener()
		{
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 3;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        allbuttons[15] = new JButton("4");
        allbuttons[15].setBounds(260,200,30,30);
        seeds_number.add(allbuttons[15]);
        allbuttons[15].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 4;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });
        

        allbuttons[16] = new JButton("5");
        allbuttons[16].setBounds(330,200,30,30);
        seeds_number.add(allbuttons[16]);
        allbuttons[16].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 5;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        allbuttons[17] = new JButton("6");
        allbuttons[17].setBounds(400,200,30,30);
        seeds_number.add(allbuttons[17]);
        allbuttons[17].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 6;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        allbuttons[18] = new JButton("7");
        allbuttons[18].setBounds(470,200,30,30);
        seeds_number.add(allbuttons[18]);
        allbuttons[18].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 7;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });

        allbuttons[19] = new JButton("8");
        allbuttons[19].setBounds(540,200,30,30);
        seeds_number.add(allbuttons[19]);
        allbuttons[19].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 8;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });
        
        allbuttons[20] = new JButton("9");
        allbuttons[20].setBounds(610,200,30,30);
        seeds_number.add(allbuttons[20]);
        allbuttons[20].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 9;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });
        
        allbuttons[21] = new JButton("10");
        allbuttons[21].setBounds(680,200,30,30);
        seeds_number.add(allbuttons[21]);
        allbuttons[21].addActionListener(new ActionListener()
                                {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                 _seedNum = 10;
                getContentPane().removeAll();
                time_panel.add(allbuttons[0]);
                add(time_panel);
                time_panel.revalidate();
                time_panel.repaint();
            }
        });
		
        allbuttons[22] = new JButton("10s");
        allbuttons[22].setBounds(200,200,50,30);
        time_panel.add(allbuttons[22]);
        allbuttons[22].addActionListener(new ActionListener()
                                         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                turn_time = 10;
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        allbuttons[23] = new JButton("30s");
        allbuttons[23].setBounds(400,200,50,30);
        time_panel.add(allbuttons[23]);
        allbuttons[23].addActionListener(new ActionListener()
                                         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                turn_time = 30;
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        allbuttons[24] = new JButton("60s");
        allbuttons[24].setBounds(600,200,50,30);
        time_panel.add(allbuttons[24]);
        allbuttons[24].addActionListener(new ActionListener()
                                         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                turn_time = 60;
                getContentPane().removeAll();
                seeds_panel.add(allbuttons[0]);
                add(seeds_panel);
                seeds_panel.revalidate();
                seeds_panel.repaint();
            }
        });
        
        
        // different stones buttons
        allbuttons[10] = new JButton("Fixed");
        allbuttons[10] .setBounds(250,200,80,80);
        seeds_panel.add(allbuttons[10] );
        allbuttons[10].addActionListener(new ActionListener()
                                         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _random = false;
                nums = null;
                getContentPane().removeAll();
                start_panel.add(allbuttons[0]);
                add(start_panel);
                start_panel.revalidate();
                start_panel.repaint();
            }
        });
        
        
        allbuttons[11]  = new JButton("Randomly");
        allbuttons[11] .setBounds(450,200,80,80);
        seeds_panel.add(allbuttons[11] );
        allbuttons[11].addActionListener(new ActionListener()
                                         {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                _random = true;
                // generate random seeds and holes
                Random rand = new Random();
                
                nums = new LinkedList<Integer>();
                for (int i = 0; i <= _seedNum / 2; i++)
                {
                    int seeds0 = rand.nextInt(_seedNum * 2) + 1;
                    int seeds1 = (_seedNum * 2) - seeds0;
                    nums.add(i, seeds0);
                    nums.add(nums.size() - i, seeds1);
					System.out.println(nums.size());
                    
                }
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
		
		allbuttons[25] = start1;
        
        start_panel.add(start1);
        
        start1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //javaTimer.start();
                //this.setVisible(false);
				System.out.println(nums == null);
                Server s = new Server(_holeNum, _seedNum, _random, nums, _mode, turn_time);
                s.run();
            }
        });
		
		for (int i = 0; i < 25; i++)
		{
			allbuttons[i].setBorder(null);
		}
		next_button.setBorder(null);
		start1.setBorder(null);
    }
	
	public void addButtonListener(int i, ActionListener listener)
	{
		allbuttons[i].addActionListener(listener);
	}
       

    public static void main(String[] args)
    {
        menu_server m = new menu_server();
        m.setVisible(true);
    }
	
	

    
 
}
