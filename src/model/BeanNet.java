package model;

public class BeanNet {
	
	public static final String[] tableTitles={"网点编号","网点名称","所在城市","具体地址","联系电话"};
	
	public String net_id;
	public String net_name;
	public String net_city;
	public String net_address;
	public String net_phone;
	
	
	
	public BeanNet() {
		
	}

	public BeanNet(String net_id, String net_name, String net_city, String net_address, String net_phone) {
		super();
		this.net_id = net_id;
		this.net_name = net_name;
		this.net_city = net_city;
		this.net_address = net_address;
		this.net_phone = net_phone;
	}
	
	

	public String getNet_id() {
		return net_id;
	}

	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}

	public String getNet_name() {
		return net_name;
	}

	public void setNet_name(String net_name) {
		this.net_name = net_name;
	}

	public String getNet_city() {
		return net_city;
	}

	public void setNet_city(String net_city) {
		this.net_city = net_city;
	}

	public String getNet_address() {
		return net_address;
	}

	public void setNet_address(String net_address) {
		this.net_address = net_address;
	}

	public String getNet_phone() {
		return net_phone;
	}

	public void setNet_phone(String net_phone) {
		this.net_phone = net_phone;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.net_id;
		else if(col==1) return this.net_name;
		else if(col==2) return this.net_city;
		else if(col==3) return this.net_address;
		else if(col==4) return this.net_phone;
		else return "";
	}
	
	
}
