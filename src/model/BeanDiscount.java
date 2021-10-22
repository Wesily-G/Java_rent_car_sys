package model;

import java.util.Date;

public class BeanDiscount {
	
	public static final String[] tableTitles={"折扣编号","限定网点","限定车型","促销折扣","折扣数量","开始时间","结束时间"};

	public String discount_id;
	public String discount_net;
	public String discount_model;
	public float discount;
	public int discount_count;
	public Date discount_start;
	public Date discount_end;
	
	
	
	public BeanDiscount() {
		super();
	}

	public BeanDiscount(String discount_id, String discount_net, String discount_model, float discount,
			int discount_count, Date discount_start, Date discount_end) {
		super();
		this.discount_id = discount_id;
		this.discount_net = discount_net;
		this.discount_model = discount_model;
		this.discount = discount;
		this.discount_count = discount_count;
		this.discount_start = discount_start;
		this.discount_end = discount_end;
	}
	
	

	public String getDiscount_id() {
		return discount_id;
	}

	public void setDiscount_id(String discount_id) {
		this.discount_id = discount_id;
	}

	public String getDiscount_net() {
		return discount_net;
	}

	public void setDiscount_net(String discount_net) {
		this.discount_net = discount_net;
	}

	public String getDiscount_model() {
		return discount_model;
	}

	public void setDiscount_model(String discount_model) {
		this.discount_model = discount_model;
	}

	public float getDiscount() {
		return discount;
	}

	public void setDiscount(float discount) {
		this.discount = discount;
	}

	public int getDiscount_count() {
		return discount_count;
	}

	public void setDiscount_count(int discount_count) {
		this.discount_count = discount_count;
	}

	public Date getDiscount_start() {
		return discount_start;
	}

	public void setDiscount_start(Date discount_start) {
		this.discount_start = discount_start;
	}

	public Date getDiscount_end() {
		return discount_end;
	}

	public void setDiscount_end(Date discount_end) {
		this.discount_end = discount_end;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.discount_id;
		else if(col==1) return this.discount_net;
		else if(col==2) return this.discount_model;
		else if(col==3) return String.valueOf(this.discount);
		else if(col==4) return String.valueOf(this.discount_count);
		else if(col==5) return String.valueOf(this.discount_start);
		else if(col==6) return String.valueOf(this.discount_end);
		else return "";
	}
	
}
