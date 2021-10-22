package model;

public class BeanCarModel {
	
	public static final String[] tableTitles={"车型编号","类型编号","车型名称","车型品牌","排放量","档位","座位数","价格"};

	public String model_id;
	public String type_id;//FK
	public String model_name;
	public String model_brand;
	public int model_emissions;
	public boolean model_auto;
	public int model_seats;
	public float model_price;
	public String model_image;
	
	
	
	public BeanCarModel() {
		
	}
	
	public BeanCarModel(String model_id, String type_id, String model_name, String model_brand,
			int model_emissions, boolean model_auto, int model_seats, float model_price) {
		super();
		this.model_id = model_id;
		this.type_id = type_id;
		this.model_name = model_name;
		this.model_brand = model_brand;
		this.model_emissions = model_emissions;
		this.model_auto = model_auto;
		this.model_seats = model_seats;
		this.model_price = model_price;
		// this.model_image = model_image;
	}
	
	

	public String getModel_id() {
		return model_id;
	}

	public void setModel_id(String model_id) {
		this.model_id = model_id;
	}

	public String getType_id() {
		return type_id;
	}

	public void setType_id(String type_id) {
		this.type_id = type_id;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getModel_brand() {
		return model_brand;
	}

	public void setModel_brand(String model_brand) {
		this.model_brand = model_brand;
	}

	public int getModel_emissions() {
		return model_emissions;
	}

	public void setModel_emissions(int model_emissions) {
		this.model_emissions = model_emissions;
	}

	public boolean isModel_auto() {
		return model_auto;
	}

	public void setModel_auto(boolean model_auto) {
		this.model_auto = model_auto;
	}

	public int getModel_seats() {
		return model_seats;
	}

	public void setModel_seats(int model_seats) {
		this.model_seats = model_seats;
	}

	public float getModel_price() {
		return model_price;
	}

	public void setModel_price(float model_price) {
		this.model_price = model_price;
	}

	public String getModel_image() {
		return model_image;
	}

	public void setModel_image(String model_image) {
		this.model_image = model_image;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.model_id;
		else if(col==1) return this.type_id;
		else if(col==2) return this.model_name;
		else if(col==3) return this.model_brand;
		else if(col==4) return String.valueOf(this.model_emissions);
		else if(col==5) {
			if(this.model_auto)
				return "自动档";
			else
				return "手动档";
		}else if(col==6) return String.valueOf(this.model_seats);
		else if(col==7) return String.valueOf(this.model_price);
		else return "";
	}
	
}
