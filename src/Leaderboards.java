import java.awt.*;

import javax.swing.*;

import java.io.FileOutputStream;

import java.io.IOException;

import java.io.ObjectOutputStream;

import java.io.*;

import java.util.*;

import java.awt.event.*;


public class Leaderboards
{
	
	static java.util.List<PlayerData> list;
	
	public static void main(String args[])
	{
		PlayerData p=new PlayerData("Adi",java.time.LocalDateTime.now().toString());
		p.setScore(1);
		addData(p);
				
		p=new PlayerData("Arp",java.time.LocalDateTime.now().toString());
		p.setScore(10);
		addData(p);
		
		p=new PlayerData("Debo",java.time.LocalDateTime.now().toString());
		p.setScore(11);
		addData(p);
		
		
		p=new PlayerData("Sap",java.time.LocalDateTime.now().toString());
		p.setScore(9);
		addData(p);
		
		
		java.util.List<PlayerData> PlayerList=GetPlayerData();
		
		Comparator<PlayerData> byScore=Comparator.comparingInt(d->d.score);
		
		PlayerList.sort(byScore.reversed()); //Sort Leader board data by descending order of score
		
		for(PlayerData data:PlayerList)
		{
			System.out.println(data);
		}
		
		readyString();
		
	}
	
	static java.util.List<PlayerData> GetPlayerData()
	{
		java.util.List<PlayerData> Playerlist=new ArrayList<>();
		
		File file = new File("Leaderboards");
		
		if(!file.exists())
			return Playerlist;
		

		try(ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(file)) )
		{		
			while(true)
			{									
				PlayerData data=(PlayerData)objectIn.readObject();		
				Playerlist.add(data);
			}			
		}
		catch(IOException | ClassNotFoundException e)
		{
			e.getStackTrace();
		}
		
		return Playerlist;
	}
		
	static void addData(PlayerData obj) 
	{
		
		java.util.List<PlayerData> PlayerList=GetPlayerData();
		
		PlayerList.add(obj);
		
		File file=new File("Leaderboards");
		
		if(file.exists())
			file.delete();
			
		try(ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream("Leaderboards")))
		{	
			for(PlayerData tmp:PlayerList)
			{
				objectOut.writeObject(tmp);
			}
		}
		catch(IOException e)
		{
			 e.getStackTrace();
		}
	}

	static void readyString()
	{
		java.util.List<PlayerData> list=GetPlayerData();
		
		Comparator<PlayerData> byScore=Comparator.comparingInt(d->d.score);
	
		list.sort(byScore.reversed()); //Sort Leader board data by descending order of score
		
		String top3[]=new String[3];
		
		for(int i=0;i<list.size() && i<3;i++)
			top3[i]=list.get(i).toString();

		new MakeLeaderboard(top3);
	}
	

}
