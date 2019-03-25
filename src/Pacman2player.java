import java.io.*;

import java.awt.*;

import java.awt.event.*;

import java.awt.geom.*;

import javax.swing.*;

import javax.sound.sampled.*;



public class Pacman2player extends JPanel implements ActionListener,KeyListener
{
	
	
	private static final long serialVersionUID = 1L;

	Timer t=new Timer(400,this); // Set a 400 ms timer
	
	int x=40,y=40,velx=40,vely=00;
	
	int a=760;
	int b=40;
	
	int vela=-40;
	
	int velb=0;
	
	int prevX=0,prevY=0; //Previous x,y
	
	int prevA=600,prevB=40;
	
	int xAngle=30,yAngle=300; // PacMan mouth angle for fillArc() method
	
	int aAngle=210,bAngle=300;
	

	
	boolean flag=true,flag1=true; // PacMan Mouth open/close animation
	

	
	
	int maze[][]=new int[1000][800];
	
	boolean LoseFlag=false;
	
	int score=0;


	Pacman2player()
	{
		try {
		      File file = new File("sounds/pacman_beginning.wav");
		      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		      Clip clip = AudioSystem.getClip();
		      clip.open(stream);
		      clip.start();
		 
		      // sleep to allow enough time for the clip to play
		      Thread.sleep(5000);
		 
		      stream.close();
		 
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
		
		
		mazeReady();
		
		t.start();
		
		addKeyListener(this);
		
		setFocusable(true);
		
		setFocusTraversalKeysEnabled(false);
		
	}
	
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		if(!LoseFlag)
		{
		makeMaze(g);
		
		 
		 Graphics2D g1=(Graphics2D)g;
		 
		 
		 g1.setColor(Color.decode("#FFFF00"));
		 
		 
		 
		 if(flag==true ) //Mouth open
		 {
			 g1.fillArc(x,y,40,40,xAngle,yAngle);
			
			 flag=false;
		 }
		 else //Mouth close
		 {
			 g1.fillArc(x,y,40,40,0,360);
			
			 flag=true;	 
		 }	
		 	
		 
		 Graphics2D g11=(Graphics2D)g;
		 
		 
		 g11.setColor(Color.RED);
		 
		 
		 
		 if(flag==true ) //Mouth open
		 {
			 g11.fillArc(a,b,40,40,aAngle,bAngle);
			
			 flag1=false;
		 }
		 else //Mouth close
		 {
			 g11.fillArc(a,b,40,40,0,360);
			
			 flag1=true;	 
		 }		
		 
		}
		else
		 {
			
			 	String s;
		        
		        Font smallFont = new Font("Helvetica", Font.BOLD, 100);
		        
		        g.setFont(smallFont);
		        g.setColor(Color.BLACK);
		        
		        s = "GAME OVER !";
		        			
		        g.drawString(s, 300, 300);
		        
		        s="Your Score: "+score ;
		        
		        g.drawString(s, 300, 500);
		     
		        
		 }	 
		
	}
	

	
	
	public void makeMaze(Graphics g)
	{	
		boolean flag=true;
		
		for(int i=0;i<1000;i++)
		{
			for(int j=0;j<800;j++)
			{
				
				if(maze[i][j]==2)
				{
					Graphics2D g1=(Graphics2D)g;
					
					Rectangle2D block = new Rectangle2D.Float(i,j,40,40); // Each tile is 40 by 40 in size
					
					if(flag) 
					{
						g1.setColor(Color.decode("#962938"));
						flag=false;
					}
					else
					{
						g1.setColor(Color.decode("#470D0D"));
						flag=true;
					}
					
					g1.fill(block);
					
				}
				
				
				if(maze[i][j]==3)
				{
					Graphics2D g2=(Graphics2D)g;
					
					Ellipse2D food=new Ellipse2D.Double(i+10,j,20,20);
					
					g2.setColor(Color.MAGENTA);
					
					g2.fill(food);
					 
				}
				
			}
		}
		
		drawScore((Graphics2D)g);
		
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		chkWin();
		
		chkLose();
		
		chkFood();
		
		repaint();
				
		
		
		/* here frame size is 1000,800 and ball size is 40,40 so to balance (1000-40=960) and (800-40=760)  */
		
		prevX=x;
		prevY=y;
				
		x=x+velx;
		y=y+vely;
		
		if(x<0 )
			x=960;
			
		if(x>960)
			x=0;
		
		if(y<0)
			y=760;
		
		if(y>760)
			y=0;
		
		prevA=a;
		prevB=b;
				
		a=a+vela;
		b=b+velb;
		
		if(a<0 )
			a=960;
			
		if(a>960)
			a=0;
		
		if(b<0)
			b=760;
		
		if(b>760)
			b=0;
		
		chkBarrier();	//Check if next tile is a barrier	
	}
	
	void chkBarrier()
	{
		
		if(maze[x][y]==2 && vely!=0)
		{
			vely=0;
			y=prevY; //if next tile is barrier in Y direction then reset y position
		}
		
		if(maze[x][y]==2 && velx!=0)
		{
			velx=0;
			x=prevX; //if next tile is barrier in X direction then reset x position
		}
		
		if(maze[a][b]==2 && velb!=0)
		{
			velb=0;
			b=prevB; //if next tile is barrier in Y direction then reset y position
		}
		
		if(maze[a][b]==2 && vela!=0)
		{
			vela=0;
			a=prevA; //if next tile is barrier in X direction then reset x position
		}
		
	}
	
	private void drawScore(Graphics2D g) {

     
        String s;
        
        Font smallFont = new Font("Helvetica", Font.BOLD, 30);
        
        g.setFont(smallFont);
        g.setColor(Color.BLACK);
        
        s = "   Score: "+score ;
        		
      
        	
        g.drawString(s, 1000, 100);

     
        
    }
	
	void chkFood()
	{
		if(maze[x][y]==3)
		{
			maze[x][y]=1;
			
			score++;
			
			try {
			      File file = new File("sounds/pacman_chomp.wav");
			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			      Clip clip = AudioSystem.getClip();
			      clip.open(stream);
			      clip.start();
			 
			      stream.close();
			 
			    } catch (Exception ex) {
			      System.out.println(ex.getMessage());
			    }
		}
	}
	
	void chkWin()
	{
		for(int i=0;i<1000;i++)
		{
			for(int j=0;j<800;j++)
			{
				if(maze[i][j]==3) //Food left
					return;
			}
		}
		
		try {
		      File file = new File("sounds/pacman_win.wav");
		      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		      Clip clip = AudioSystem.getClip();
		      clip.open(stream);
		      clip.start();
		      
		      Thread.sleep(1000);
		 
		      stream.close();
		 
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
		
		System.exit(1);
		
	}
	
	void chkLose()
	{
		if(LoseFlag)
		{
			try
	        {
	        	Thread.sleep(800);
	        }
	        catch(Exception e)
	        {
	        	e.getStackTrace();
	        }
	        
			
			System.exit(1);
		}
		
		
		if(x==a && y==b)
		{
			try {
			      File file = new File("sounds/pacman_lose.wav");
			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			      Clip clip = AudioSystem.getClip();
			      clip.open(stream);
			      clip.start();
			      
			      Thread.sleep(3000);
			 
			      stream.close();
			 
			    } catch (Exception ex) {
			      System.out.println(ex.getMessage());
			    }
			LoseFlag=true;
			
			
		}
	}
	
	
	
	public void up()
	{
		
		velx=0;
		
		vely=-40;
		
		xAngle=120; //changes Pacman's mouth angle
		
		yAngle=300;

	}
	
	public void down()
	{
		velx=0;
		
		vely=40;
		
		xAngle=300;
		
		yAngle=300;

	}
	
	public void left()
	{
		velx=-40;
		
		vely=0;
		
		xAngle=210;
		
		yAngle=300;

	}
	
	public void right()
	{
		velx=40;
		
		vely=0;
		
		xAngle=30;
		
		yAngle=300;

	}
	
	public void ABup()
	{
		
		vela=0;
		
		velb=-40;
		
		aAngle=120; //changes Pacman's mouth angle
		
		bAngle=300;

	}
	
	public void ABdown()
	{
		vela=0;
		
		velb=40;
		
		aAngle=300;
		
		bAngle=300;

	}
	
	public void ABleft()
	{
		vela=-40;
		
		velb=0;
		
		aAngle=210;
		
		bAngle=300;
	}
	
	public void ABright()
	{
		vela=40;
		
		velb=0;
		
		aAngle=30;
		
		bAngle=300;

	}
	
	
	public void keyPressed(KeyEvent e)
	{
		int code=e.getKeyCode();
		
		if(code == KeyEvent.VK_UP)
			up();
		
		if(code == KeyEvent.VK_DOWN)
			down();
		
		if(code == KeyEvent.VK_LEFT)
			left();
		
		if(code == KeyEvent.VK_RIGHT)
			right();
		
		if(code == KeyEvent.VK_W)
			ABup();
		
		if(code == KeyEvent.VK_S)
			ABdown();
		
		if(code == KeyEvent.VK_A)
			ABleft();
		
		if(code == KeyEvent.VK_D)
			ABright();
		
	}
	
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	
	
	public static void main(String args[])
	{
		Pacman2player ob=new Pacman2player();
		
		JFrame frame=new JFrame();
		
		frame.setResizable(false);
		
		frame.add(ob);  
		
		frame.setVisible(true);
		
		frame.setSize(1200,790);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("PAC MAN Multiplayer");
		
		
}
	
	

	public void mazeReady()
	{

		for(int i=0;i<1000;i++)
		{
			for(int j=0;j<800;j++)
			{
		
				maze[i][j]=1;
			}
			
		}	
		
		
		// ALL MAZE X,Y LOCATIONS SHOULD BE AT MULTIPLES OF 40
		
		
		
			// Maze Border
		
			//Top Border	
			maze[0][0]=2;
			maze[40][0]=2;
			maze[80][0]=2;
			maze[120][0]=2;
			maze[160][0]=2;
			maze[200][0]=2;	
			maze[240][0]=2;		
			maze[280][0]=2;		
			maze[320][0]=2;		
			maze[360][0]=2;		
			maze[400][0]=2;	
			maze[440][0]=2;
			maze[480][0]=2;
			maze[520][0]=2;
			maze[560][0]=2;
			maze[600][0]=2;
			maze[640][0]=2;	
			maze[680][0]=2;		
			maze[720][0]=2;		
			maze[760][0]=2;		
			maze[800][0]=2;		
			maze[840][0]=2;	
			maze[880][0]=2;	
			maze[920][0]=2;	
			maze[960][0]=2;	
			//Top Border End
			
			
			//Bottom Border	
			maze[0][720]=2;
			maze[40][720]=2;
			maze[80][720]=2;
			maze[120][720]=2;
			maze[160][720]=2;
			maze[200][720]=2;	
			maze[240][720]=2;		
			maze[280][720]=2;		
			maze[320][720]=2;		
			maze[360][720]=2;		
			maze[400][720]=2;	
			maze[440][720]=2;
			maze[480][720]=2;
			maze[520][720]=2;
			maze[560][720]=2;
			maze[600][720]=2;
			maze[640][720]=2;	
			maze[680][720]=2;		
			maze[720][720]=2;		
			maze[760][720]=2;		
			maze[800][720]=2;		
			maze[840][720]=2;	
			maze[880][720]=2;	
			maze[920][720]=2;	
			maze[960][720]=2;	
			//Bottom Border End
			
			
			//Left Border
			maze[0][40]=2;
			maze[0][80]=2;
			maze[0][120]=2;
			maze[0][160]=2;
			maze[0][200]=2;
			maze[0][240]=2;	
			maze[0][280]=2;			
			maze[0][440]=2;	
			maze[0][480]=2;
			maze[0][520]=2;
			maze[0][560]=2;
			maze[0][600]=2;
			maze[0][640]=2;
			maze[0][680]=2;	
			maze[0][720]=2;		
			maze[0][760]=2;		
			//Left Border End
			
			//Right Border
			maze[960][40]=2;
			maze[960][80]=2;
			maze[960][120]=2;
			maze[960][160]=2;
			maze[960][200]=2;
			maze[960][240]=2;	
			maze[960][280]=2;			
			maze[960][440]=2;	
			maze[960][480]=2;
			maze[960][520]=2;
			maze[960][560]=2;
			maze[960][600]=2;
			maze[960][640]=2;
			maze[960][680]=2;	
			maze[960][720]=2;		
			maze[960][760]=2;		
			//Right Border End
			
			//Maze Border End
			
			
			//Left plus
			
			maze[160][320]=2;
			maze[160][360]=2;
			maze[160][400]=2;
			maze[120][360]=2;
			maze[200][360]=2;
			
			//Left plus End
			
			//Right plus
			
			maze[800][320]=2;
			maze[760][360]=2;
			maze[800][400]=2;
			maze[840][360]=2;
			maze[800][360]=2;
			
			//Right plus End
			
			
		
				//Top inverted T
		
				maze[480][120]=2;
				maze[480][160]=2;
				maze[480][200]=2;
				maze[360][200]=2;
				maze[400][200]=2;
				maze[440][200]=2;
				maze[520][200]=2;
				maze[560][200]=2;
				maze[600][200]=2;
				
				//Top inverted T end
				
				
				
				//Top inverted T
				
				maze[480][560]=2;
				maze[480][600]=2;
				maze[480][520]=2;
				maze[360][520]=2;
				maze[400][520]=2;
				maze[440][520]=2;
				maze[520][520]=2;
				maze[560][520]=2;
				maze[600][520]=2;
				
				//Top inverted T end
		
		
				//Top Inverted L
		
				maze[160][120]=2;
				maze[160][160]=2;
				maze[160][200]=2;
				maze[200][120]=2;   
				maze[240][120]=2; 
				
				//Top Inverted L End
	                      
	            
				//Bottom L
				
				maze[800][120]=2;
				maze[800][160]=2;
				maze[800][200]=2;
				maze[760][120]=2;   
				maze[720][120]=2;  
				
				//Bottom L End
				
				
				//Top Right inverted L
				
				maze[160][600]=2;
				maze[160][560]=2;
				maze[160][520]=2;
				maze[200][600]=2;   
				maze[240][600]=2; 
				
				//Top Right inverted L End
				
	            
				//Bottom Right inverted L
	                     
				maze[800][600]=2;
				maze[800][560]=2;
				maze[800][520]=2;
				maze[760][600]=2;   
				maze[720][600]=2; 
				
				//Bottom Right inverted L End
				
				
				
				//Holding pen
				
				maze[400][360]=2;
				maze[400][400]=2;
				maze[400][320]=2;
				
				maze[440][400]=2;
				maze[480][400]=2;
				maze[520][400]=2;
				
				maze[560][400]=2;
				maze[560][360]=2;
				maze[560][320]=2;
				
				//Holding pen End
				
				
				
				//Food
				
			
			maze[80][200]=3;
				
			maze[80][280]=3;			
			
			maze[80][480]=3;
			
			maze[80][560]=3;
			
			maze[880][640]=3;
			
			maze[880][200]=3;
			
			maze[880][280]=3;			
			
			maze[880][480]=3;
			
			maze[880][560]=3;
			
			maze[880][640]=3;
			
			maze[440][360]=3;
			
			maze[480][360]=3;
			
			maze[520][360]=3;
			
			//Food End
								
		
				
	}
	
	



}















