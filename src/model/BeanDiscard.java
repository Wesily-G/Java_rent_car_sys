package model;

import java.util.Date;

public class BeanDiscard {
	
	public static final String[] tableTitles={"Ô±¹¤±àºÅ","Æû³µ±àºÅ","·ÏÆú±àºÅ","·ÏÆúÊ±¼ä","·ÏÆúÃèÊö"};
	
	public String stuff_id;//FK
	public String car_id;//FK
	public String discard_id;//FK
	public Date discard_time;
	public String discard_illustrate;
	
	
	
	public BeanDiscard() {
		
	}
	
	public BeanDiscard(String stuff_id, String car_id, String discard_id, Date discard_time,
			String discard_illustrate) {
		super();
		this.stuff_id = stuff_id;
		this.car_id = car_id;
		this.discard_id = discard_id;
		this.discard_time = discard_time;
		this.discard_illustrate = discard_illustrate;
	}
	
	
	
	public String getStuff_id() {
		return stuff_id;
	}
	
	public void setStuff_id(String stuff_id) {
		this.stuff_id = stuff_id;
	}
	
	public String getCar_id() {
		return car_id;
	}
	
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	
	public String getDiscard_id() {
		return discard_id;
	}
	
	public void setDiscard_id(String discard_id) {
		this.discard_id = discard_id;
	}
	
	public Date getDiscard_time() {
		return discard_time;
	}
	
	public void setDiscard_time(Date discard_time) {
		this.discard_time = discard_time;
	}
	
	public String getDiscard_illustrate() {
		return discard_illustrate;
	}
	
	public void setDiscard_illustrate(String discard_illustrate) {
		this.discard_illustrate = discard_illustrate;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.stuff_id;
		else if(col==1) return this.car_id;
		else if(col==2) return this.discard_id;
		else if(col==3) return this.discard_time;
		else if(col==4) return this.discard_illustrate;
		else return "";
	}
	
}
