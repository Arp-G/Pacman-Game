import java.io.*;

import java.awt.*;

import java.awt.event.*;

import java.awt.geom.*;

import javax.swing.*;

import java.util.Collections;

import javax.sound.sampled.*;

public class PacMan_Level2 extends JPanel implements ActionListener,KeyListener
{
	
	class Enemy
	{
		int x;
		int y;
		int scatter;		
		String name;		
		String path;
		String Direction;

		 Enemy(int x,int y,String name,String path,String Direction,int scatter)
		 {
		        this.x=x;
		        this.y=y;
		        this.name=name;
		        this.path=path;
		        this.scatter=scatter;
		        this.Direction=Direction;
		 }

		 Enemy(int x,int y,String name,String path,String Direction)
		 {
		        this.x=x;
		        this.y=y;
		        this.name=name;
		        this.path=path;
		        this.Direction=Direction;
		 }
	}


	class PatrolEnemy extends Enemy
	{
		
		int patrol=0;
		
		boolean ForwardPatrol=true;
		
		PatrolEnemy(int x,int y,String name,String path,String Direction)
		{
			super(x,y,name,path,Direction);
		}
	}

	
	private static final long serialVersionUID = 1L;
	
	Timer t=new Timer(400,this); // Set a 400 ms timer
	
	int x=40,y=40,velx=40,vely=00;
	
	int prevX=0,prevY=0; //Previous x,y
	
	int xAngle=30,yAngle=300; // PacMan mouth angle for fillArc() method
	
	boolean flag=true; // PacMan Mouth open/close animation
	
	int count=0; // PacMan Mouth open for how long
		
	int maze[][]=new int[1000][800];
	
	static int score=0;
	
	boolean LoseFlag=false;
	
	static PlayerData p;
	
	int ScatterCount=0;
	
	Enemy EatenBy;
	
	static JFrame frame;

	java.util.List<Enemy> enemyList=new java.util.ArrayList<>();

	PacMan_Level2()
	{
		try {
		      File file = new File("sounds/pacman_beginning.wav");
		      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
		      Clip clip = AudioSystem.getClip();
		      clip.open(stream);
		      clip.start();
		      stream.close();
		 
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
		
		
		mazeReady();
		
		t.start();
		
		addKeyListener(this);
		
		setFocusable(true);
		
		setFocusTraversalKeysEnabled(false);
		
		//add Enemy's
		
		
		enemyList.add(new Enemy(880,40,"Red","Red_ghost","_right",5));
		
		enemyList.add(new Enemy(880,640,"Green","Green_ghost","_right",7));
		
		enemyList.add(new Enemy(480,360,"Choclate","Choclate_ghost","_left",10));
		
		enemyList.add(new PatrolEnemy(120,80,"Patrol Guard 1","Patrol_ghost",""));
		
		enemyList.add(new PatrolEnemy(720,80,"Patrol Guard 2","Patrol_ghost",""));
		
		enemyList.add(new PatrolEnemy(120,600,"Patrol Guard 3","Patrol_ghost",""));
		
		enemyList.add(new PatrolEnemy(720,600,"Patrol Guard 4","Patrol_ghost",""));
	}
	
	
	public static void main(String args[])
	{
		p=new PlayerData("Test",java.time.LocalDateTime.now().toString());
		display();		
	}
	
	public static void control(PlayerData p)
	{
		score=p.score;
		display();
	}
	
	
	public static void display()
	{
		PacMan_Level2 ob=new PacMan_Level2();
		
		frame=new JFrame();
		
		frame.setResizable(false);
		
		frame.add(ob);  
		
		frame.setVisible(true);
		
		frame.setSize(1200,790);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("PAC MAN");
	}
	
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		
		if(!LoseFlag)
		{
			makeMaze(g);
			
			for(int i=0;i<enemyList.size();i++)
			{
				Enemy tmp=enemyList.get(i);
				
				if(tmp instanceof PatrolEnemy)
					g.drawImage(new ImageIcon("Images/"+tmp.path+tmp.Direction+".png").getImage(), tmp.x, tmp.y, null); //Draw Patrol enemy
				
				else	
					g.drawImage(new ImageIcon("Images/"+tmp.path+tmp.Direction+".png").getImage(), tmp.x, tmp.y, null); //Draw enemys
				
						 	 
			}
			
			g.setColor(Color.decode("#FFFF00")); //Set pac man color
		 
		 if(flag==true ) //Mouth open
		 {
			 g.fillArc(x,y,40,40,xAngle,yAngle);
			 flag=false;
		 }
		 else //Mouth close
		 {
			 g.fillArc(x,y,40,40,0,360);
			 flag=true;	 
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
		        
		        s="You where Eaten By "+EatenBy.name;
		        
		        g.setFont(new Font("Helvetica", Font.BOLD, 50));
		        
		        g.drawString(s ,300, 600);
		     
		        
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
					g.drawImage(new ImageIcon("Images/food1.png").getImage(), i, j, null); //Draw food
					
				else if(maze[i][j]==4)
					g.drawImage(new ImageIcon("Images/food2.png").getImage(), i, j, null); //Draw food	
					
				else if(maze[i][j]==5)
					g.drawImage(new ImageIcon("Images/food3.png").getImage(), i, j, null); //Draw food
					
				else if(maze[i][j]==6)
					g.drawImage(new ImageIcon("Images/food4.png").getImage(), i, j, null); //Draw food
					
				else if(maze[i][j]==7)
					g.drawImage(new ImageIcon("Images/food5.png").getImage(), i, j, null); //Draw food
				
				else if(maze[i][j]==8)
					g.drawImage(new ImageIcon("Images/food6.png").getImage(), i, j, null); //Draw food
		
					 
				
				
			}
		}
		
		drawScore((Graphics2D)g);
		
		ScatterCount++;
		
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		chkWin();
		
		chkLose();
		
		chkFood();
		
		repaint();
		
		for(int i=0;i<enemyList.size();i++)
		{
			
			Enemy tmp=enemyList.get(i);
			
			
			if(tmp instanceof PatrolEnemy)
				
				tmp=PatrolAI((PatrolEnemy)tmp); // Update Patrol enemy's new location
			
			else
		
				tmp=followAI(tmp); // Update Follow enemy's new location	
				
				
			enemyList.set(i, tmp); // Set the updated enemy to the enemy list
				
		}
				
		/* here frame size is 1000,800 and character size is 40,40 so to balance (1000-40=960) and (800-40=760)  */
		
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
		
	}
	
	private void drawScore(Graphics2D g) 
	{
        String s;
        
        Font smallFont = new Font("Helvetica", Font.BOLD, 30);
        
        g.setFont(smallFont);
        g.setColor(Color.BLACK);
        
        s = "   Score: "+score ;
        			
        g.drawString(s, 1000, 100);
        
    }
	
	void chkFood()
	{
		if(maze[x][y]==3 || maze[x][y]==4 || maze[x][y]==5 || maze[x][y]==6 || maze[x][y]==7 || maze[x][y]==8)
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
				if(maze[i][j]==3 || maze[i][j]==4 || maze[i][j]==5 || maze[i][j]==6 || maze[i][j]==7 || maze[i][j]==8) //Food left
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
		      
		      p.setScore(score);
		      //PacMan_Level2.control(p);
		      
		      
		     JDialog d = new JDialog(frame, "Continue ?", true); 
		     
		        d.setLayout( new FlowLayout() ); 
		        
		        JButton b = new JButton ("OK");  
		        
		        b.addActionListener ( new ActionListener()  
		        {  
		            public void actionPerformed( ActionEvent e )  
		            {  
		                d.setVisible(false);  
		                
		                StartingCountDown.control(p,3); 
		  		      	frame.dispose();
		            }  
		        });
		        
		        d.add( new JLabel ("Continue to Next Level ?")); 
		        d.setLocation(400,400);
		        d.add(b);
		        d.setSize(200,100);
		        d.setVisible(true);  

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
	        	Thread.sleep(1500);
	        }
	        catch(Exception e)
	        {
	        	e.getStackTrace();
	        }
			
			frame.dispose();
			MainMenu.display();
			
			Thread.currentThread().stop(); // Stop the current thread;
		}
		
		for(int i=0;i<enemyList.size();i++)
		{
			Enemy tmp=enemyList.get(i);
			
			if(tmp.x==x && tmp.y==y)
			{
				
				try {
						File file = new File("sounds/pacman_lose.wav");
						AudioInputStream stream = AudioSystem.getAudioInputStream(file);
						Clip clip = AudioSystem.getClip();
						clip.open(stream);
						clip.start();
			      
						Thread.sleep(1000);
			 
						stream.close();
			 
			    	} catch (Exception ex) 
					{
			    		System.out.println(ex.getMessage());
			    	}
				LoseFlag=true;
				EatenBy=tmp;
				break;
			}
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
		
	}
	
	
	public void keyTyped(KeyEvent e) {}
	
	public void keyReleased(KeyEvent e) {}
	
	
	
	

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
			
			
			
				
				
				
				//Middle Maze
				
				maze[400][360]=2;
				maze[400][400]=2;
				maze[400][320]=2;
				
				maze[440][400]=2;
				maze[480][400]=2;
				maze[520][400]=2;
				
				maze[560][400]=2;
				maze[560][360]=2;
				maze[560][320]=2;
				
				
				
				
				maze[640][240]=2;
				maze[600][240]=2;
				maze[560][240]=2;
				maze[520][240]=2;
				maze[480][240]=2;
				maze[440][240]=2;
				maze[400][240]=2;
				maze[360][240]=2;
				maze[320][240]=2;
				maze[320][280]=2;
				maze[320][320]=2;
				maze[320][360]=2;
				maze[320][400]=2;
				maze[320][440]=2;
				maze[320][480]=2;
				maze[360][480]=2;
				maze[400][480]=2;
				maze[440][480]=2;
				maze[520][480]=2;
				maze[560][480]=2;
				maze[600][480]=2;
				maze[640][480]=2;
				maze[640][280]=2;
				maze[640][320]=2;
				maze[640][360]=2;
				maze[640][400]=2;
				maze[640][440]=2;
				//Middle Maze
				
				
				
				
				
				
				//Top Left Maze
				maze[120][120]=2;
				maze[120][160]=2;
				maze[120][200]=2;
				maze[160][200]=2;
				maze[200][200]=2;
				maze[240][200]=2;
				maze[240][120]=2;
				maze[240][160]=2;
				maze[240][200]=2;
				
				
				
				//Top Right Maze
				maze[840][120]=2;
				maze[840][160]=2;
				maze[840][200]=2;
				maze[800][200]=2;
				maze[760][200]=2;
				maze[720][200]=2;
				maze[720][160]=2;
				maze[720][120]=2;
				
				
				//Bottom Left Maze
				maze[240][480]=2;
				maze[200][480]=2;
				maze[160][480]=2;
				maze[120][480]=2;
				maze[120][520]=2;
				maze[120][560]=2;
				maze[240][520]=2;
				maze[240][560]=2;
				
				
				//Bottom Right Maze
				
				maze[840][480]=2;
				maze[800][480]=2;
				maze[760][480]=2;
				maze[720][480]=2;
				maze[720][520]=2;
				maze[720][560]=2;
				maze[840][520]=2;
				maze[840][560]=2;
				
				
				
				
				//Food
				
			
			maze[80][200]=3;
				
			maze[80][280]=4;			
			
			maze[80][480]=3;
			
			maze[80][560]=5;
			
			maze[880][640]=6;
			
			maze[880][200]=7;
			
			maze[880][280]=8;			
			
			maze[880][480]=3;
			
			maze[880][560]=3;
			
			maze[880][640]=8;
			
			maze[440][360]=3;
			
			maze[480][360]=6;
			
			maze[520][360]=8;
			
			maze[160][160]=4;
			
			maze[200][160]=3;
			
			maze[760][160]=5;
			
			maze[800][160]=7;
			
			maze[160][520]=8;
			
			maze[200][520]=3;
			
			maze[760][520]=6;
			
			maze[800][520]=8;
			
			//Food End
								
		
				
	}
	
	
	
	PatrolEnemy PatrolAI(PatrolEnemy tmp)
	{
		
		
		if(tmp.ForwardPatrol)
		{
			if(tmp.patrol== 0)
			{
				tmp.x=tmp.x+40;
				tmp.Direction="_right";
			}
		
			else if(tmp.patrol== 1)
			{
				tmp.y=tmp.y+40;
				tmp.Direction="_down";
			}
		
			else if(tmp.patrol== 2)
			{
				tmp.y=tmp.y+40;
				tmp.Direction="_down";
			}
		
			else if(tmp.patrol== 3)
			{
				tmp.x=tmp.x+40;
				tmp.Direction="_down";
			}
		
			else if(tmp.patrol== 4)
			{
				tmp.y=tmp.y-40;
				tmp.Direction="_up";
			}
		
			else if(tmp.patrol== 5)
			{
				tmp.y=tmp.y-40;
				tmp.Direction="_up";
			}
		
			else if(tmp.patrol== 6)
			{
				tmp.x=tmp.x+40;
				tmp.Direction="_right";
				tmp.ForwardPatrol=false;
				return tmp;
			}
		
		}
		
		else
		{
			if(tmp.patrol== 6)
			{
				tmp.x=tmp.x-40;
				tmp.Direction="_left";
			}
		
			else if(tmp.patrol== 5)
			{
				tmp.y=tmp.y+40;
				tmp.Direction="_down";
			}
			
			else if(tmp.patrol== 4)
			{
				tmp.y=tmp.y+40;
				tmp.Direction="_down";
			}
		
			else if(tmp.patrol== 3)
			{
				tmp.x=tmp.x-40;
				tmp.Direction="_left";
			}
		
			else if(tmp.patrol== 2)
			{
				tmp.y=tmp.y-40;
				tmp.Direction="_up";
			}
			
			else if(tmp.patrol== 1)
			{
				tmp.y=tmp.y-40;
				tmp.Direction="_up";
			}
		
			else if(tmp.patrol== 0)
			{
				tmp.x=tmp.x-40;
				tmp.Direction="_left";
				tmp.ForwardPatrol=true;
				return tmp;
			}
		
		}
		
		if(tmp.ForwardPatrol)
			tmp.patrol=tmp.patrol+1;
		else
			tmp.patrol=tmp.patrol-1;
		
		
		
		return tmp;
		
	}
	

	Enemy followAI(Enemy tmp)
	{
		int a=tmp.x;
		
		int b=tmp.y;
		
		String Direction;
		
		int x_dist=x-a;
		
		int y_dist=y-b;
		
		boolean Scatter=false;
		
		if(ScatterCount%tmp.scatter==0)
			Scatter=true;
		
		if(Math.abs(x_dist)>Math.abs(y_dist) && x_dist>=0 && maze[a+40][b]!=2 && !Scatter) //Move right
		{
			 a=a+40;
			 Direction="_right";
		}
		else if(Math.abs(x_dist)>Math.abs(y_dist) && x_dist<0 && maze[a-40][b]!=2 && !Scatter) //Move left
		{
			a=a-40;
			Direction="_left";
		}
		else if(Math.abs(x_dist)<Math.abs(y_dist) && y_dist>=0 && maze[a][b+40]!=2 && !Scatter) //Move down
		{
			b=b+40;
			Direction="_down";
		}
		else if(Math.abs(x_dist)<Math.abs(y_dist) && y_dist<0 && maze[a][b-40]!=2 && !Scatter) //Move up
		{
			b=b-40;	
			Direction="_up";
		}
		else
		{
			
			class RandomMove
			{
				int p, q;
				String Direction;
				
				RandomMove(int p,int q,String Direction)
				{
					this.p=p;
					this.q=q;
					this.Direction=Direction;
				}
			}
			
			java.util.List<RandomMove> moveList=new java.util.ArrayList<>();
			
			if(maze[a+40][b]!=2)
				moveList.add(new RandomMove(a+40,b,"_right"));
			
			if(maze[a-40][b]!=2)
				moveList.add(new RandomMove(a-40,b,"_left"));
			
			if(maze[a][b+40]!=2)
				moveList.add(new RandomMove(a,b+40,"_down"));
			
			if(maze[a][b-40]!=2)
				moveList.add(new RandomMove(a,b-40,"_up"));
			
			Collections.shuffle(moveList); //Pick a random move and set it
			
			a=moveList.get(0).p;
			
			b=moveList.get(0).q;
			
			Direction=moveList.get(0).Direction;
					
		}
			
			return new Enemy(a,b,tmp.name,tmp.path,Direction,tmp.scatter);
	}

	
	


}















