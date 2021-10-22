package model;

import java.util.Date;

public class BeanUser {
	
	public static final String[] tableTitles={"用户编号","用户姓名","用户性别","手机号码","邮箱地址","所在城市","注册时间"};
	
	public String user_id;
	public String user_name;
	public boolean user_sex;
	public String user_password;
	public String user_phone;
	public String user_mail;
	public String user_city;
	public Date user_register_itme;
	
	
	
	public BeanUser() {
		
	}
	
	public BeanUser(String user_id, String user_name, boolean user_sex, String user_password, String user_phone,
			String user_mail, String user_city, Date user_register_itme) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.user_sex = user_sex;
		this.user_password = user_password;
		this.user_phone = user_phone;
		this.user_mail = user_mail;
		this.user_city = user_city;
		this.user_register_itme = user_register_itme;
	}
	
	
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public boolean isUser_sex() {
		return user_sex;
	}
	
	public void setUser_sex(boolean user_sex) {
		this.user_sex = user_sex;
	}
	
	public String getUser_password() {
		return user_password;
	}
	
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	
	public String getUser_phone() {
		return user_phone;
	}
	
	public void setUser_phone(String user_phone) {
		this.user_phone = user_phone;
	}
	
	public String getUser_mail() {
		return user_mail;
	}
	
	public void setUser_mail(String user_mail) {
		this.user_mail = user_mail;
	}
	
	public String getUser_city() {
		return user_city;
	}
	
	public void setUser_city(String user_city) {
		this.user_city = user_city;
	}
	
	public Date getUser_register_itme() {
		return user_register_itme;
	}
	
	public void setUser_register_itme(Date user_register_itme) {
		this.user_register_itme = user_register_itme;
	}
	
	
	public Object getCell(int col) {
		// TODO Auto-generated method stub
		if(col==0) return this.user_id;
		else if(col==1) return this.user_name;
		else if(col==2) {
			if(user_sex)
				return "男性";
			else 
				return "女性";
		}
		else if(col==3) return this.user_phone;
		else if(col==4) return this.user_mail;
		else if(col==5) return this.user_city;
		else if(col==6) return String.valueOf(this.user_register_itme);
		else return "";
	}
	
}
