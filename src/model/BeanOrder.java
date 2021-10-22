package model;

import java.util.Date;

public class BeanOrder {
	
	public static final String[] tableTitles={"订单编号","车辆编号","租车网点","租车时间","还车网点","还车时间","价格","订单状态",};
	
	public String user_id;//FK
	public String car_id;//FK
	public String order_id;
	public String order_rent_net;//FK
	public Date order_rent_time;
	public String order_return_net;//FK
	public Date order_return_time;
	public int order_time;
	public float order_origin_price;
	public float order_result_price;
	public String order_prefer_id;
	public String order_state;
	
	
	
	public BeanOrder() {
		
	}

	public BeanOrder(String user_id,String car_id, String order_id, String order_rent_net, Date order_rent_time,
			String order_return_net, Date order_return_time, int order_time, float order_origin_price,
			float order_result_price, String order_prefer_id, String order_state) {
		super();
		this.user_id = user_id;
		this.car_id = car_id;
		this.order_id = order_id;
		this.order_rent_net = order_rent_net;
		this.order_rent_time = order_rent_time;
		this.order_return_net = order_return_net;
		this.order_return_time = order_return_time;
		this.order_time = order_time;
		this.order_origin_price = order_origin_price;
		this.order_result_price = order_result_price;
		this.order_prefer_id = order_prefer_id;
		this.order_state = order_state;
	}

	
	
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getCar_id() {
		return car_id;
	}

	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}

	public String getOrder_id() {
		return order_id;
	}

	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}

	public String getOrder_rent_net() {
		return order_rent_net;
	}

	public void setOrder_rent_net(String order_rent_net) {
		this.order_rent_net = order_rent_net;
	}

	public Date getOrder_rent_time() {
		return order_rent_time;
	}

	public void setOrder_rent_time(Date order_rent_time) {
		this.order_rent_time = order_rent_time;
	}

	public String getOrder_return_net() {
		return order_return_net;
	}

	public void setOrder_return_net(String order_return_net) {
		this.order_return_net = order_return_net;
	}

	public Date getOrder_return_time() {
		return order_return_time;
	}

	public void setOrder_return_time(Date order_return_time) {
		this.order_return_time = order_return_time;
	}

	public int getOrder_time() {
		return order_time;
	}

	public void setOrder_time(int order_time) {
		this.order_time = order_time;
	}

	public float getOrder_origin_price() {
		return order_origin_price;
	}

	public void setOrder_origin_price(float order_origin_price) {
		this.order_origin_price = order_origin_price;
	}

	public float getOrder_result_price() {
		return order_result_price;
	}

	public void setOrder_result_price(float order_result_price) {
		this.order_result_price = order_result_price;
	}

	public String getOrder_prefer_id() {
		return order_prefer_id;
	}

	public void setOrder_prefer_id(String order_prefer_id) {
		this.order_prefer_id = order_prefer_id;
	}

	public String getOrder_state() {
		return order_state;
	}

	public void setOrder_state(String order_state) {
		this.order_state = order_state;
	}

	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.order_id;
		else if(col==1) return this.car_id;
		else if(col==2) return this.order_rent_net;
		else if(col==3) return String.valueOf(this.order_rent_time);
		else if(col==4) return this.order_return_net;
		else if(col==5) return String.valueOf(this.order_rent_time);
		else if(col==6) {
			if(!(order_origin_price==0))
				return String.valueOf(this.order_result_price);
			else
				return String.valueOf(this.order_origin_price);
		}
		else if(col==7) return this.order_state;
		else return "";
	}
	
	
}
