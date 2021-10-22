package model;

public class BeanStuff {
	
	public static final String[] tableTitles={"员工编号","所属网点","员工姓名","员工密码"};
	
	public String stuff_id;
	public String net_id;//FK
	public String stuff_name;
	public String stuff_password;
	
	
	
	public BeanStuff() {
		
	}
	
	public BeanStuff(String stuff_id, String net_id, String stuff_name, String stuff_password) {
		super();
		this.stuff_id = stuff_id;
		this.net_id = net_id;
		this.stuff_name = stuff_name;
		this.stuff_password = stuff_password;
	}
	
	
	
	public String getStuff_id() {
		return stuff_id;
	}
	
	public void setStuff_id(String stuff_id) {
		this.stuff_id = stuff_id;
	}
	
	public String getNet_id() {
		return net_id;
	}
	
	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}
	
	public String getStuff_name() {
		return stuff_name;
	}
	
	public void setStuff_name(String stuff_name) {
		this.stuff_name = stuff_name;
	}
	
	public String getStuff_password() {
		return stuff_password;
	}
	
	public void setStuff_password(String stuff_password) {
		this.stuff_password = stuff_password;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.stuff_id;
		else if(col==1) return this.net_id;
		else if(col==2) return this.stuff_name;
		else if(col==3) return this.stuff_password;
		else return "";
	}
	
}
