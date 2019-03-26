import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class MakeLeaderboard extends JLabel
{
	String str[];
	
	public MakeLeaderboard(String str[])
	{
		this.str=str;
		
		display();
	}
	
	void display()
	{		
		JFrame frame=new JFrame();

		frame.setResizable(false);

		frame.setSize(800,800);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Leaderboards");  
		
		this.setIcon(new ImageIcon("Images/Leaderboards.gif"));
		
		JButton button_Back = new JButton("Back");
		
		 button_Back.addActionListener(new ActionListener() 
        {
      		      public void actionPerformed(ActionEvent arg0) 
      		      {
      		    	 {
      					 SwingUtilities.invokeLater(new Runnable()
      						{
      							public void run()
      							{
      								MainMenu.display();
      							}
      						});		
      				 }
      		    	  
      		    	  
      		    	  frame.dispose();
      		      }
      		      
        });
		 
		 button_Back.addMouseListener(new java.awt.event.MouseAdapter()
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
	      	    	
	      	    	
	      	    	button_Back.setBackground(Color.WHITE);
	      	    }

	      	    public void mouseExited(java.awt.event.MouseEvent evt) {
	      	    	button_Back.setBackground(UIManager.getColor("control"));
	      	    }
	      	});
		 
		this.setLayout(new BorderLayout());
		
		button_Back.setPreferredSize(new Dimension(40, 80));
		
		button_Back.setFont(new Font("Arial", Font.BOLD, 40));
		 
	    this.add(button_Back, BorderLayout.SOUTH);
				 
		frame.add(this);


		frame.setVisible(true);

	}
	
	public void paintComponent(Graphics g)
	{		
		super.paintComponent(g);
		
		g.setFont(new Font("Helvetica", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        
        g.drawString("LeaderBoards", 250,150);
        
        g.setFont(new Font("Helvetica", Font.BOLD, 20));
		
		if(str[0]==null)
			g.drawString("No data Avialabe", 250, 300);
		else
			for(int i=0;i<3;i++)
				g.drawString(str[i], 250, 300+i*50);
			
	}
}
