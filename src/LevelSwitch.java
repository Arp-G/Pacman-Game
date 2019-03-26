import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class LevelSwitch extends JFrame
{	
	int nextLevel;
	PlayerData p;
	
	public LevelSwitch(int nextLevel,PlayerData p)
	{
		this.nextLevel=nextLevel;
		this.p=p;
	}
	
	void display()
	{		
		JFrame winFrame=this;		      
	    winFrame.setLayout( new FlowLayout() );		      
	    JButton b = new JButton ("OK"); 
	    b.addActionListener ( new ActionListener()  
	    {  
	          public void actionPerformed( ActionEvent e )  
	          {                  		                
	        	  new Thread(new Runnable()
	        	  {
	        			public void run()
	        			{
	        				winFrame.dispose();
	        				if(nextLevel==1)
	        					StartingCountDown.display(p,1);
	        				else if(nextLevel==2)
	        					StartingCountDown.display(p,2);	
	        				else if(nextLevel==3)
	        					StartingCountDown.display(p,3);	
	        				
	        				else 
	        					MainMenu.display();
	        			
	        					
	        			}
	        	  }).start();	
	           }  
	    });
	    
	    winFrame.setResizable(false);		
        winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(nextLevel==4) {
        	winFrame.setSize(400, 100);
        	winFrame.add( new JLabel ("Congratulations")); 
        	winFrame.add( new JLabel ("You have won the game. Go back to main Menu ?"));
        }
        else {
	    	winFrame.add( new JLabel ("Continue to Next Level ?")); 		     
	    	winFrame.setSize(200,100);
        }
	    winFrame.add(b);
	    winFrame.setTitle("Promt");
	    winFrame.setVisible(true);
	
	}
}
