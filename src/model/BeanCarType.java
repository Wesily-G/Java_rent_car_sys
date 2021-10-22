package model;

public class BeanCarType {
	
	public static final String[] tableTitles={"类型编号","类型名称","类型描述"};

	public String type_id;
	public String type_name;
	public String type_describe;
	
	
	
	public BeanCarType() {
		
	}
	
	public BeanCarType(String type_id,String type_name,String type_describe) {
		this.type_id = type_id;
		this.type_name = type_name;
		this.type_describe = type_describe;
	}
	
	
	
	public String getType_id() {
		return type_id;
	}
	public void setType_id(String type_id) {
		this.type_id = type_id;
	}
	public String getType_name() {
		return type_name;
	}
	public void setType_name(String type_name) {
		this.type_name = type_name;
	}
	public String getType_describe() {
		return type_describe;
	}
	public void setType_describe(String type_describe) {
		this.type_describe = type_describe;
	}
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.type_id;
		else if(col==1) return this.type_name;
		else if(col==2) return this.type_describe;
		else return "";
	}
	
}
