import java.time.*;

public class PlayerData implements java.io.Serializable
{
	private static final long serialVersionUID = 1L;

	String name;
	
	int score=0;
	
	String date;
	
	PlayerData(String name,String date)
	{
		this.name=name;		
		this.date=date;
	}
	
	void setScore(int score)
	{
		this.score=score;
	}
	
	public String toString()
	{
		return name+"    "+score+"    "+date;
	}
}