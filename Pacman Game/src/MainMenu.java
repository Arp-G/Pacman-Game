import java.awt.*;

import java.io.*;

import java.awt.event.*;

import javax.swing.*;

import javax.swing.border.EmptyBorder;

import javax.sound.sampled.*;

class AnimChar
{
	public int first_posx,first_posy;
	
	public int posx,posy;
	
	public int xAngle=30,yAngle=300;
	
	public boolean flag=true;
	
	Color color=null;
	
	AnimChar(int posx,int posy,Color color)
	{
		this.posx=posx;
		
		this.posy=posy;
		
		first_posx=posx;
		
		first_posy=posy;
		
		this.color=color;
	}
}


public class MainMenu extends JLabel implements ActionListener,LineListener
{
	
	AnimChar c[]=new AnimChar[4];
	
		
	MainMenu()

	{
		c[0]=new AnimChar(250,200,Color.RED);
		
		c[1]=new AnimChar(250,680,Color.GREEN);
		
		c[2]=new AnimChar(1000,680,Color.BLUE);
		
		c[3]=new AnimChar(1000,200,Color.ORANGE);
		
		t.start();
	}
	
	static boolean Soundflag=true; //to alternatively play two different menu musics
	
	public void update(LineEvent event)  // Detects an event on Line(sound playing)
	 {
        if (event.getType() == LineEvent.Type.STOP) //If the sound playing has stopped
        {
       	 this.playMenuMusic();       	 //play next sound
        }
	 }
	
	void playMenuMusic()
	{	
		String path="";
		if(Soundflag)
		{
			path="sounds/MainMenuMusic2.wav";
			Soundflag=false;
		}
		else
		{
			path="sounds/MainMenuMusic1.wav";
			Soundflag=true;
		}
		

		try {
		      File file = new File(path);
		      stream = AudioSystem.getAudioInputStream(file);
		      clip = AudioSystem.getClip();
		      clip.open(stream);
		      clip.start();
		 
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
		
		clip.addLineListener(this);
		
	}
			
	private static final long serialVersionUID = 1L;
	
	Timer t=new Timer(20,this); // Set a 500 ms timer

	static int x=0,y=0;
	
	static int count=0; // PacMan Mouth open for how long	
	
	static int longcount=0;
	
	static JFrame frame;
	
	static Clip clip;
	
	static AudioInputStream stream;
		
	public static void main(String args[])
	{
		SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						display();
					}
				});	
				
		return;
	}
	
	public static void display()
	{
		MainMenu background=new MainMenu();
		
		background.playMenuMusic();
		
		frame=new JFrame();
 		
        frame.setResizable(false);
	
        frame.setSize(1200,790);
		
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setTitle("Main Menu");         
 
        background.setIcon(new ImageIcon("Images/MainMenuBackground.jpg"));
        
        background.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200))); //Set location of label
        
        background.setLayout(new BoxLayout(background, BoxLayout.Y_AXIS)); //Use Box Layout to order buttons vertically
        
        JButton button_NewGame = new JButton("New Game");
        
        JButton button_LevelSelect = new JButton("Level Select");
        
        JButton button_Multiplayer = new JButton("Multiplayer");
        
        JButton button_Leaderboards = new JButton("Leaderboards");
        
        JButton button_Credits = new JButton("Credits");
         
        JButton button_Exit = new JButton("Exit");
                         
        //Mouse click event
        
        button_NewGame.addActionListener(new ActionListener() 
        {
      		      public void actionPerformed(ActionEvent arg0) 
      		      {
      		    	 {
      					 SwingUtilities.invokeLater(new Runnable()
      						{
      							public void run()
      							{
      								NameEntry.control(1);
      							}
      						});		
      				 }
      		    	  
      		    	  
      		    	  try
      		    	  {
      		    		  clip.close();   //Close the clip that is playing
      		    		  stream.close(); //Close the audio input stream to avoid any other clips to play
      		    	  }
      		    	  catch(IOException e) 
      		    	  {
      		    		  e.getStackTrace();
      		    	  }
      		    	  
      		    	  frame.dispose();
      		      }
      		      
        });
        
        button_LevelSelect.addActionListener(new ActionListener() 
        {
    		      public void actionPerformed(ActionEvent arg0) 
    		      {
    		        //On click do this
    		      }
    		      
    	  });
        
        button_Multiplayer.addActionListener(new ActionListener() 
        {
    		      public void actionPerformed(ActionEvent arg0) 
    		      {
    		        //On click do this
    		      }
    		      
    	  });
        
        button_Leaderboards.addActionListener(new ActionListener() 
        {
        	public void actionPerformed(ActionEvent arg0) 
		      {		    	  
		    	  try
		    	  {
		    		  clip.close();   //Close the clip that is playing
		    		  stream.close(); //Close the audio input stream to avoid any other clips to play
		    	  }
		    	  catch(IOException e) 
		    	  {
		    		  e.getStackTrace();
		    	  }
		    	  
		    	  frame.dispose();
		    	    	  
		    	  Leaderboards.readyString();

		      }
		      
        });
        
        button_Credits.addActionListener(new ActionListener() 
        {
		      public void actionPerformed(ActionEvent arg0) 
		      {
		        //On click do this
		      }
		      
        });
 
        button_Exit.addActionListener(new ActionListener() 
        {
		      public void actionPerformed(ActionEvent arg0) 
		      {
		        //On click do this
		      }
		      
        });
        
        
        //Mouse hover event
        
        button_NewGame.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_NewGame.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) {
      	    	button_NewGame.setBackground(UIManager.getColor("control"));
      	    }
      	});
        
        button_LevelSelect.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_LevelSelect.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) {
      	    	button_LevelSelect.setBackground(UIManager.getColor("control"));
      	    }
      	});
                
        button_Multiplayer.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_Multiplayer.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) 
      	    {
      	    	button_Multiplayer.setBackground(UIManager.getColor("control"));
      	    }
      	});
        
        button_Leaderboards.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_Leaderboards.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) {
      	    	button_Leaderboards.setBackground(UIManager.getColor("control"));
      	    }
      	});
        
        button_Credits.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_Credits.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) {
      	    	button_Credits.setBackground(UIManager.getColor("control"));
      	    }
      	});
        
        button_Exit.addMouseListener(new java.awt.event.MouseAdapter()
        {
      	    public void mouseEntered(java.awt.event.MouseEvent evt) 
      	    {
      	    	try {
      			      File file = new File("sounds/Button Hover.wav");
      			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
      			      Clip clip = AudioSystem.getClip();
      			      clip.open(stream);
      			      clip.start();
      			      stream.close();
      			 
      			    } catch (Exception ex) {
      			      System.out.println(ex.getMessage());
      			    }
      	    	
      	    	
      	    	button_Exit.setBackground(Color.WHITE);
      	    }

      	    public void mouseExited(java.awt.event.MouseEvent evt) {
      	    	button_Exit.setBackground(UIManager.getColor("control"));
      	    }
      	});
        
        button_NewGame.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20)); //Set button size by changing fonts
        
        button_LevelSelect.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20));
        
        button_Multiplayer.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20));
        
        button_Leaderboards.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20));
        
        button_Credits.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20));
        
        button_Exit.setFont(button_NewGame.getFont().deriveFont(Font.BOLD, 20));
        
        
        button_NewGame.setAlignmentX(Component.CENTER_ALIGNMENT); //Set button alignment to center
        
        button_LevelSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button_Multiplayer.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button_Leaderboards.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button_Credits.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        button_Exit.setAlignmentX(Component.CENTER_ALIGNMENT);
           
        background.add(button_NewGame);
        
        background.add(Box.createRigidArea(new Dimension(0, 60)));  //Add an invisible component to create a vertical spacing between two adjacent buttons in the panel
       
        background.add(button_LevelSelect);
        
        background.add(Box.createRigidArea(new Dimension(0, 60)));
        
        background.add(button_Multiplayer);
        
        background.add(Box.createRigidArea(new Dimension(0, 60)));
        
        background.add(button_Leaderboards);
        
        background.add(Box.createRigidArea(new Dimension(0, 60)));
        
        background.add(button_Credits);
        
        background.add(Box.createRigidArea(new Dimension(0, 60)));
        
        background.add(button_Exit);
     
        frame.add(background);

        frame.setVisible(true);
			
	}
	
	public void actionPerformed(ActionEvent e)
	{
		this.repaint();

		Point p = MouseInfo.getPointerInfo().getLocation();
		
		x=(int)p.getX();
		
		y=(int)p.getY();	

	    if(count<20)
	    	count++;
	    else
	    	count=0;
	    
	    if(longcount<400)
	    	longcount++;
	    else
	    	longcount=0;
	     	    
	}
	
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
		for(int i=0;i<4;i++)
		{
			g.setColor(c[i].color); //Set pac man color
			
			updateMenuAnimation(c[i]);
		 
			if(c[i].flag==true ) //Mouth open
			{
				g.fillArc(c[i].posx,c[i].posy,40,40,c[i].xAngle,c[i].yAngle);
				
				if(count==20)
					c[i].flag=false;
				
				if(longcount==400)
				{
					c[i].posx=c[i].first_posx;
					c[i].posy=c[i].first_posy;
				}
			}
			else //Mouth close
			{
				g.fillArc(c[i].posx,c[i].posy,40,40,0,360);
				
				if(count==20)
					c[i].flag=true;
				
			}		 
			
		}
				 	
	}
	
	static void updateMenuAnimation(AnimChar ob)
	{
		int x_dist=x-ob.posx;
		
		int y_dist=y-ob.posy;
		
		if(Math.abs(x_dist)>Math.abs(y_dist))
		{
			if(x_dist>0)
			{
				ob.xAngle=30;
				
				ob.yAngle=300;

				ob.posx=ob.posx+1;
			}
			else
			{
				ob.xAngle=210;
				
				ob.yAngle=300;
				
				ob.posx=ob.posx-1;
			}
		}
		
		else if(Math.abs(y_dist)>Math.abs(x_dist))
		{
			if(y_dist>0)
			{
				ob.posy=ob.posy+1;
				
				
					ob.xAngle=300;
				
					ob.yAngle=300;
				
			}
			else
			{
				
					ob.xAngle=120; 
				
					ob.yAngle=300;
				
				
				ob.posy=ob.posy-1;
				
			}
		}
			
	}
	
}

		
	













