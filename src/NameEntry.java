import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class NameEntry
{
	
	static JFrame frame=null;
	
	static void display(int x)
	{
		frame=new JFrame();

		frame.setResizable(false);

		frame.setSize(800,500);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setTitle("Enter Player Name");  

		JLabel background=new JLabel(new ImageIcon("Images/EnterPlayerName.gif"));

		background.setBorder(new EmptyBorder(new Insets(150, 200, 150, 200))); //Set location of label

		background.setLayout(new java.awt.FlowLayout()); //Use Box Layout to order buttons vertically

		JButton button_Ok = new JButton("OK");

		JTextField textField = new JTextField("Your name here",15);
		
		textField.setToolTipText("Please enter your name here");
		
		textField.setFont(new Font("Arial", Font.ITALIC | Font.BOLD, 20));
		
		textField.setForeground(Color.black);
		
		textField.setBackground(Color.WHITE);

		textField.requestFocusInWindow();
		
		textField.selectAll();
		//Mouse click event

		button_Ok.addActionListener(new ActionListener() 
		{
				      public void actionPerformed(ActionEvent arg0) 
				      {
				        String text=textField.getText();
		        
				        if(text!=null)
				        {	   
				        	
				        	frame.dispose();
				        	
				        	new Thread(new Runnable()
							{
								public void run()
								{
									StartingCountDown.control(new PlayerData(text,java.time.LocalDateTime.now().toString()),x);
								}
							}).start();
				        	
				        }
				      }
		});
				     

		button_Ok.setFont(button_Ok.getFont().deriveFont(Font.BOLD, 20)); //Set button size by changing fonts

		button_Ok.setAlignmentX(Component.CENTER_ALIGNMENT); //Set button alignment to center

		background.add(textField);
		
		background.add(button_Ok);
		
		frame.add(background);

		frame.setVisible(true);
	}
	
	static void control(int x)
	{
		display(x);			
	}
	
	
	
	public static void main(String args[])
	{
		control(1);
		
	}
}


