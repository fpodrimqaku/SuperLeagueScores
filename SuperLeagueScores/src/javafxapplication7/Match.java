package javafxapplication7;
public class Match
{
	private String firstTeam;
	private String secondTeam;
	private String date;
	private int week;
	private int season;
	
	Match(String a, String b, String c, int d, int e)
	{
		this.firstTeam = a;
		this.secondTeam = b;
		this.date = c;
		this.week = d;
		this.season = e;
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
