import java.io.*;
import javax.swing.*;
import javax.sound.sampled.*;


public class StartingCountDown
{	
	
	static void control(PlayerData p,int x)
	{
		display(p,x);
	}
	
	static void display(PlayerData p,int x)
	{
		playCountDown();
		
		JFrame frame=new JFrame();
		
		JLabel label=new JLabel();

		frame.setResizable(false);

		frame.setSize(1200,800);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Game starts in..."); 
		
		label.setIcon(new ImageIcon("Images/countdown.gif"));
		
		frame.add(label);
			    	 
		frame.setVisible(true);				
		
		try
		{
			Thread.sleep(6000);
			frame.dispose();
			
			 if(x==1)
			 {
				 SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							PacMan_Level1.control(p);
						}
					});	
			 }
			 
			 else if(x==2)
			 {
				 SwingUtilities.invokeLater(new Runnable()
					{
						public void run()
						{
							PacMan_Level2.control(p);
						}
					});		
			 }
			 

		}
		catch(InterruptedException e)
		{
			e.getStackTrace();
		}
	}

	static void playCountDown()
	{	
		try {
		      File file = new File("sounds/countdown.wav");
		      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		      Clip clip = AudioSystem.getClip();
		      clip.open(stream);
		      clip.start();
		      stream.close();
		 
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
	}
	public static void main(String args[]) 
	{
		PlayerData tmp=new PlayerData("Test",java.time.LocalDateTime.now().toString());		
		tmp.setScore(10);
		control(tmp,1);
	}

}
