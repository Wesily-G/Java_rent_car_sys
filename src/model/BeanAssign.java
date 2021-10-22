package model;

import java.util.Date;

public class BeanAssign {
	
	public static final String[] tableTitles={"汽车编号","调入网点","调出网点","调出时间"};
	
	public String car_id;//FK
	public String assign_in;//FK
	public String assign_out;//FK
	public Date assign_time;
	
	
	
	public BeanAssign() {
		
	}
	
	public BeanAssign(String car_id, String assign_in, String assign_out, Date assign_time) {
		super();
		this.car_id = car_id;
		this.assign_in = assign_in;
		this.assign_out = assign_out;
		this.assign_time = assign_time;
	}
	
	
	
	public String getCar_id() {
		return car_id;
	}
	
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	
	public String getAssign_in() {
		return assign_in;
	}
	
	public void setAssign_in(String assign_in) {
		this.assign_in = assign_in;
	}
	
	public String getAssign_out() {
		return assign_out;
	}
	
	public void setAssign_out(String assign_out) {
		this.assign_out = assign_out;
	}
	
	public Date getAssign_time() {
		return assign_time;
	}
	
	public void setAssign_time(Date assign_time) {
		this.assign_time = assign_time;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.car_id;
		else if(col==1) return this.assign_in;
		else if(col==2) return this.assign_out;
		else if(col==3) return String.valueOf(this.assign_time);
		else return "";
	}
	
}
