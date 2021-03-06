import java.io.*;

import java.awt.*;

import java.awt.event.*;

import java.awt.geom.*;

import javax.swing.*;

import java.util.Collections;

import javax.sound.sampled.*;


public class PacMan_Level3 extends JPanel implements ActionListener,KeyListener
{
	private static final long serialVersionUID = 1L;
	
	Timer t=new Timer(400,this); // Set a 400 ms timer
	
	int x=40,y=40,velx=40,vely=00;
	
	int prevX=0,prevY=0; //Previous x,y
	
	int xAngle=30,yAngle=300; // PacMan mouth angle for fillArc() method
	
	boolean flag=true; // PacMan Mouth open/close animation
	
	int count=0; // PacMan Mouth open for how long
		
	int maze[][]=new int[1500][1500];
	
	static int score=0;
	
	static int food=1;
	
	boolean LoseFlag=false;
	
	static JFrame frame;
	
	static PlayerData p1;
	
	static int scaredGhost=0;
	
	static int ExtendedScared=0;
	
	static void control(PlayerData p)
	{
		p1=p;
		
		score=p.score;
		
		display();
	}
	
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
			this.Direction=Direction;
			this.scatter=scatter;
		}
	}
	
	int ScatterCount=0;
	
	Enemy EatenBy;

	java.util.List<Enemy> enemyList=new java.util.ArrayList<>();

	PacMan_Level3()
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
		
		enemyList.add(new Enemy(880,40,"Red","Red_ghost","_left",5));
		
		enemyList.add(new Enemy(880,640,"Green","Green_ghost","_right",7));
		
		enemyList.add(new Enemy(40,640,"Choclate","Choclate_ghost","_left",10));
		
		enemyList.add(new Enemy(480,320,"Pink","Pink_ghost","_left",8));
		
	}
	
	public static void display()
	{
		PacMan_Level3 ob=new PacMan_Level3();
		
		frame=new JFrame();
		
		frame.setResizable(false);
		
		frame.add(ob);  
		
		frame.setVisible(true);
		
		frame.setSize(1200,790);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setTitle("PAC MAN");
		
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
	}
			
	public static void main(String args[])
	{
		p1=new PlayerData("Test",java.time.LocalDateTime.now().toString());
		display();			
	}
	
	public void paintComponent(Graphics g)
	{
		
		super.paintComponent(g);
		
		if(ExtendedScared!=0)
			ExtendedScared--;
		
		
		if(!LoseFlag)
		{
			makeMaze(g);
			
			if(ExtendedScared==0)
			{
				for(int i=0;i<enemyList.size();i++)
				{
					Enemy tmp=enemyList.get(i);
	
					g.drawImage(new ImageIcon("Images/"+tmp.path+tmp.Direction+".png").getImage(), tmp.x, tmp.y, null); //Draw enemys
						 	 
				}
			}
			else
			{
				for(int i=0;i<enemyList.size();i++)
				{
					Enemy tmp=enemyList.get(i);
	
					g.drawImage(new ImageIcon("Images/Frightned_ghost.png").getImage(), tmp.x, tmp.y, null); //Draw scared enemys
						 	 
				}
				
				if(scaredGhost!=0)
					scaredGhost--;
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

				else if(maze[i][j]==3)
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
				else if(maze[i][j]==9)
					g.drawImage(new ImageIcon("Images/food_power.png").getImage(), i, j, null); //Draw food
				
				
		
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
			Enemy tmp=enemyList.get(i); // Get the enemy
			
			if(ExtendedScared==0 || (scaredGhost>=8 && scaredGhost<=10))
				tmp=followAI(tmp); // Update enemy's new location
			else
				tmp=escapeAI(tmp);
			
			enemyList.set(i, tmp); // Set the updated enemy to the enemy list
		}
				
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
		
		if(maze[x][y]==9)
		{
			maze[x][y]=1;
			scaredGhost=10;
			ExtendedScared=13;
			
			try {
			      File file = new File("sounds/scared_ghost.wav");
			      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
			      Clip clip = AudioSystem.getClip();
			      clip.open(stream);
			      clip.start();
			      stream.close();
			 
			    } catch (Exception ex) {
			      System.out.println(ex.getMessage());
			    }
			
		}
		
		
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
	
	@SuppressWarnings("deprecation")
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
		      
		      p1.setScore(score);
		      
			  Leaderboards.addData(p1);
		      
		      frame.dispose();
		      
		      new Thread(new Runnable()
			    {		            	   
						public void run()
						{	
							try {
									File file = new File("sounds/pacman_win.wav");
								      AudioInputStream stream = AudioSystem.getAudioInputStream(file);
								      Clip clip = AudioSystem.getClip();
								      clip.open(stream);
								      clip.start();
								      stream.close();
								      new LevelSwitch(4,p1).display();
								}
								catch(Exception e)
								{
									e.getStackTrace();	
								}
								}
					  }).start();
				
			  Thread.currentThread().stop(); // Stop the current thread;
		      		      
		     
		    } catch (Exception ex) {
		      System.out.println(ex.getMessage());
		    }
		
		System.exit(1);
		
	}
	
	@SuppressWarnings("deprecation")
	void chkLose()
	{
		
		if(LoseFlag)
		{
			p1.setScore(score);
			
			Leaderboards.addData(p1);
			
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
		
		if(ExtendedScared==0)
		{
			for(int i=0;i<enemyList.size();i++)
			{
				Enemy tmp=enemyList.get(i);
			
				if(tmp.x==x && tmp.y==y)
				{
					enemyList.remove(i);				
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
		else
		{
			for(int i=0;i<enemyList.size();i++)
			{
				Enemy tmp=enemyList.get(i);
							
				if(tmp.x==x && tmp.y==y)
				{
					enemyList.remove(i);
					score=score+10;
					try {
							File file = new File("sounds/pacman_eatghost.wav");							
							AudioInputStream stream = AudioSystem.getAudioInputStream(file);
							Clip clip = AudioSystem.getClip();
							clip.open(stream);
							clip.start();
			 
			    		} catch (Exception ex) 
						{
			    			System.out.println(ex.getMessage());
						}
				}
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
			
			maze[200][40]=2;
			maze[200][80]=2;
			maze[200][120]=2;
			maze[200][160]=2;
			
			maze[240][40]=2;
			maze[240][80]=2;
			maze[240][120]=2;
			maze[240][160]=2;

			maze[520][200]=2;
			maze[520][240]=2;
			maze[520][280]=2;
			maze[520][320]=2;
			

			maze[520][400]=2;
			maze[520][440]=2;
			maze[520][480]=2;
			maze[520][520]=2;
			maze[520][560]=2;



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
			
			maze[200][680]=2;
			maze[200][640]=2;
			maze[200][600]=2;
			maze[200][560]=2;
			
			maze[240][680]=2;
			maze[240][640]=2;
			maze[240][600]=2;
			maze[240][560]=2;
			
		
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
			maze[0][320]=2;
			maze[0][360]=2;
			maze[0][400]=2;
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
			maze[960][320]=2;
			maze[960][360]=2;
			maze[960][400]=2;
			maze[960][440]=2;	
			maze[960][480]=2;
			maze[960][520]=2;
			maze[960][560]=2;
			maze[960][600]=2;
			maze[960][640]=2;
			maze[960][680]=2;	
			maze[960][720]=2;		
			maze[960][760]=2;	
			
			maze[920][360]=2;
			maze[880][360]=2;
			maze[840][360]=2;
			maze[800][360]=2;
			maze[760][360]=2;
			maze[720][360]=2;
			
			maze[920][400]=2;
			maze[880][400]=2;
			maze[840][400]=2;
			maze[800][400]=2;
			maze[760][400]=2;
			maze[720][400]=2;
			

			maze[0][360]=2;
			maze[40][360]=2;
			maze[80][360]=2;
			maze[120][360]=2;
			maze[160][360]=2;
			maze[200][360]=2;
			maze[240][360]=2;
			maze[280][360]=2;
			maze[320][360]=2;
			maze[360][360]=2;
			maze[400][360]=2;
			maze[440][360]=2;
			maze[480][360]=2;
			maze[520][360]=2;
			maze[560][360]=2;
			
			maze[0][400]=2;
			maze[40][400]=2;
			maze[80][400]=2;
			maze[120][400]=2;
			maze[160][400]=2;
			maze[200][400]=2;
			maze[240][400]=2;
			maze[280][400]=2;
			maze[320][400]=2;
			maze[360][400]=2;
			maze[400][400]=2;
			maze[440][400]=2;
			maze[480][400]=2;
			maze[520][400]=2;
			maze[560][400]=2;
			
			maze[560][320]=2;
			maze[560][280]=2;
			maze[560][240]=2;
			maze[560][200]=2;
			
			maze[600][200]=2;
			maze[640][200]=2;
			maze[680][200]=2;
			maze[720][200]=2;
			maze[760][200]=2;
			
			
			maze[560][440]=2;
			maze[560][480]=2;
			maze[560][520]=2;
			maze[560][560]=2;
			
			maze[600][560]=2;
			maze[640][560]=2;
			maze[680][560]=2;
			maze[720][560]=2;
			maze[760][560]=2;	
			
				
			//Food
								
			maze[80][120]=3;	
			
			maze[120][120]=6;
			
			maze[80][200]=5;
			
			maze[120][200]=4;
				
			maze[80][280]=3;
			
			maze[120][280]=7;
			
			maze[200][280]=3;
			
			maze[280][280]=6;
			
			maze[360][280]=8;
			
			
			
			
			maze[80][480]=4;
			
			maze[120][480]=3;
			
			maze[80][560]=9;
			
			maze[120][560]=7;
			
			maze[80][640]=3;
			
			maze[120][640]=5;
			
			maze[200][480]=7;
			
			maze[280][480]=5;
			
			maze[360][480]=3;
			
			
			maze[360][200]=4;
			
			maze[360][120]=8;
			
			maze[360][560]=6;
			
			maze[360][640]=5;
			
			
			maze[440][640]=3;	
			
			maze[520][640]=7;
			
			maze[600][640]=4;
			
			maze[680][640]=3;
			
			maze[760][640]=5;
			
			maze[840][640]=9;
			
			
			maze[440][120]=3;	
			
			maze[520][120]=7;
			
			maze[600][120]=4;
			
			maze[680][120]=3;
			
			maze[760][120]=5;
			
			maze[840][120]=9;
			
			
			maze[840][280]=4;
						
			maze[640][360]=9;
			
			maze[640][400]=8;
			
			maze[840][480]=6;
			
			maze[640][480]=3;
			
			maze[640][280]=3;
					
				
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
			
			try {
			
			if(maze[a+40][b]!=2)
				moveList.add(new RandomMove(a+40,b,"_right"));
			
			if(maze[a-40][b]!=2)
				moveList.add(new RandomMove(a-40,b,"_left"));
			
			if(maze[a][b+40]!=2)
				moveList.add(new RandomMove(a,b+40,"_down"));
			
			if(maze[a][b-40]!=2)
				moveList.add(new RandomMove(a,b-40,"_up"));
			
			}
			catch(ArrayIndexOutOfBoundsException e) {
				e.getStackTrace();
				return tmp;
			}
			
			Collections.shuffle(moveList); //Pick a random move and set it
			
			a=moveList.get(0).p;
			
			b=moveList.get(0).q;
			
			Direction=moveList.get(0).Direction;
					
		}
			
			return new Enemy(a,b,tmp.name,tmp.path,Direction,tmp.scatter);
	}
		
	Enemy escapeAI(Enemy tmp)
	{
		int a=tmp.x;
		
		int b=tmp.y;
		
		int x_dist=x-a;
		
		int y_dist=y-b;
		
		try {
		
		if(Math.abs(x_dist)>Math.abs(y_dist) && x_dist>=0 && a-40>=0 && a-40<960 && maze[a-40][b]!=2) //Move left
			a=a-40;

		else if(Math.abs(x_dist)>Math.abs(y_dist) && x_dist<0 && a+40>=0 && a+40<960 && maze[a+40][b]!=2) //Move right
			a=a+40;

		else if(Math.abs(x_dist)<Math.abs(y_dist) && y_dist>=0 && b-40>=0 && b-40<720 && maze[a][b-40]!=2) //Move up
			b=b-40;

		else if(Math.abs(x_dist)<Math.abs(y_dist) && y_dist<0 && b+40>=0 && b+40<720 && maze[a][b+40]!=2) //Move up
			b=b+40;	
		else
		{
			class RandomMove
			{
				int p, q;
				
				RandomMove(int p,int q)
				{
					this.p=p;
					this.q=q;
				}
			}
			
			java.util.List<RandomMove> moveList=new java.util.ArrayList<>();
			
			if(a+40>=0 && a+40<960 && maze[a+40][b]!=2)
				moveList.add(new RandomMove(a+40,b));
			
			if(a-40>=0 && a-40<960 && maze[a-40][b]!=2)
				moveList.add(new RandomMove(a-40,b));
			
			if(b+40>=0 && b+40<720 && maze[a][b+40]!=2)
				moveList.add(new RandomMove(a,b+40));
			
			if(b-40>=0 && b-40<720 && maze[a][b-40]!=2)
				moveList.add(new RandomMove(a,b-40));
			
			Collections.shuffle(moveList); //Pick a random move and set it
			
			a=moveList.get(0).p;
			
			b=moveList.get(0).q;
		}
		}
		catch(ArrayIndexOutOfBoundsException e) {
			e.getStackTrace();
			return tmp;
		}
		
					
		return new Enemy(a,b,tmp.name,tmp.path,"",tmp.scatter);
	}

}















