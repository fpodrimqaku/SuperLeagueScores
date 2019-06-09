package javafxapplication7;
public class Match
{
	int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	private String firstTeam;
	private String secondTeam;
	private String date;
	private int week;
	private int season;
	private String firstTeamS;
	private String secondTeamS;
	
	Match(int id, String a, String b, String c, int d, int e, String f, String g)
	{
		this.id = id;
		this.firstTeam = a;
		this.secondTeam = b;
		this.date = c;
		this.week = d;
		this.season = e;
		this.firstTeamS = f;
		this.secondTeamS = g;
	}
	
	public String getFirstTeamS() {
		return firstTeamS;
	}

	public void setFirstTeamS(String firstTeamS) {
		this.firstTeamS = firstTeamS;
	}

	public String getSecondTeamS() {
		return secondTeamS;
	}

	public void setSecondTeamS(String secondTeamS) {
		this.secondTeamS = secondTeamS;
	}

	public String getFirstTeam() {
		return firstTeam;
	}
	public void setFirstTeam(String firstTeam) {
		this.firstTeam = firstTeam;
	}
	public String getSecondTeam() {
		return secondTeam;
	}
	public void setSecondTeam(String secondTeam) {
		this.secondTeam = secondTeam;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getWeek() {
		return week;
	}
	public void setWeek(int week) {
		this.week = week;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
}
