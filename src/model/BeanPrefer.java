package model;

import java.util.Date;

public class BeanPrefer {
	
	public static final String[] tableTitles={"优惠编号","限定网点","限定车型","优惠金额","开始时间","结束时间"};
	
	public String prefer_id;
	public String prefer_net;
	public String prefer_model;
	public float prefer_amount;
	public Date prefer_start;
	public Date prefer_end;
	
	
	
	public BeanPrefer() {
		
	}
	
	public BeanPrefer(String prefer_id, String prefer_net, String prefer_model, float prefer_amount, Date prefer_start,
			Date prefer_end) {
		super();
		this.prefer_id = prefer_id;
		this.prefer_net = prefer_net;
		this.prefer_model = prefer_model;
		this.prefer_amount = prefer_amount;
		this.prefer_start = prefer_start;
		this.prefer_end = prefer_end;
	}
	
	
	
	public String getPrefer_id() {
		return prefer_id;
	}
	
	public void setPrefer_id(String prefer_id) {
		this.prefer_id = prefer_id;
	}
	
	public String getPrefer_net() {
		return prefer_net;
	}
	
	public void setPrefer_net(String prefer_net) {
		this.prefer_net = prefer_net;
	}
	
	public String getPrefer_model() {
		return prefer_model;
	}
	
	public void setPrefer_model(String prefer_model) {
		this.prefer_model = prefer_model;
	}
	
	public float getPrefer_amount() {
		return prefer_amount;
	}
	
	public void setPrefer_amount(float prefer_amount) {
		this.prefer_amount = prefer_amount;
	}
	
	public Date getPrefer_start() {
		return prefer_start;
	}
	
	public void setPrefer_start(Date prefer_start) {
		this.prefer_start = prefer_start;
	}
	
	public Date getPrefer_end() {
		return prefer_end;
	}
	
	public void setPrefer_end(Date prefer_end) {
		this.prefer_end = prefer_end;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.prefer_id;
		else if(col==1) return this.prefer_net;
		else if(col==2) return this.prefer_model;
		else if(col==3) return String.valueOf(this.prefer_amount);
		else if(col==4) return String.valueOf(this.prefer_start);
		else if(col==5) return String.valueOf(this.prefer_end);
		else return "";
	}
	
}
