package model;

public class BeanCar {

	public static final String[] tableTitles={"Æû³µ±àºÅ","ËùÊôÍøµã","³µĞÍ±àºÅ","ÅÆÕÕºÅ","×´Ì¬"};
	
	public String car_id;
	public String net_id;//FK
	public String model_id;
	public String car_license;
	public String car_state;
	
	
	
	public BeanCar() {
		
	}
	
	public BeanCar(String car_id, String net_id, String model_id, String car_license, String car_state) {
		super();
		this.car_id = car_id;
		this.net_id = net_id;
		this.model_id = model_id;
		this.car_license = car_license;
		this.car_state = car_state;
	}
	
	
	
	public String getCar_id() {
		return car_id;
	}
	
	public void setCar_id(String car_id) {
		this.car_id = car_id;
	}
	
	public String getNet_id() {
		return net_id;
	}
	
	public void setNet_id(String net_id) {
		this.net_id = net_id;
	}
	
	public String getModel_id() {
		return model_id;
	}
	
	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}
	
	public String getCar_license() {
		return car_license;
	}
	
	public void setCar_license(String car_license) {
		this.car_license = car_license;
	}
	
	public String getCar_state() {
		return car_state;
	}
	
	public void setCar_state(String car_state) {
		this.car_state = car_state;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.car_id;
		else if(col==1) return this.net_id;
		else if(col==2) return this.model_id;
		else if(col==3) return this.car_license;
		else if(col==4) return this.car_state;
		else return "";
	}
	
}
