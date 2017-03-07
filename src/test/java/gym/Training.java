package gym;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Training {
	private int id;
	private Date date;
	private String dayOfWeek;
	
	public Training(int id, Date date) {
		this.id = id;
		this.date = date;
		setDayOfWeek(date);
	}
	
	public Training(Date date) {
		this.date = date;
		setDayOfWeek(date);
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	private void setDayOfWeek(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("EEEE");
		dayOfWeek = dateFormat.format(date);
		System.out.println(dayOfWeek);
	}
	
	public String getDayOfWeek() {
		return dayOfWeek;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(date);
	}
	
}
