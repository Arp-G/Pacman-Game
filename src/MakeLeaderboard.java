import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.FocusListener;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

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

		//MakeLeaderboard background=new MakeLeaderboard(this.str);
		
		this.setIcon(new ImageIcon("Images/Leaderboards.gif"));
			
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
