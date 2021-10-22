package DBManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BeanCar;
import model.BeanOrder;
import util.BusinessException;
import util.DBUtil;

public class Inquiry {

    public List<BeanCar> inquiryCarForUser(String model_name,String net_name) throws BusinessException{
        
        List<BeanCar> result=new ArrayList<BeanCar>();

        try {

            //check existence and inquiry
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from state_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);

            if(!Check.checkNullNoException(model_name) && !Check.checkNullNoException(net_name)){
                Check.checkExistence("car_model_tbl", "model_name", model_name);
                Check.checkExistence("net_tbl", "net_name", net_name);
                sql+=" where model_name = ? and net_name = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, model_name);
                pst.setString(2, net_name);
            }else if(Check.checkNullNoException(model_name) && !Check.checkNullNoException(net_name)){
                Check.checkExistence("net_tbl", "net_name", net_name);
                sql+=" where net_name = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, net_name);
            }else{
                Check.checkExistence("car_model_tbl", "model_name", model_name);
                sql+=" where model_name = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, model_name);
            }  

			java.sql.ResultSet rs = pst.executeQuery();

            //ֱ������id��λ�ô洢name
            while(rs.next()){
                BeanCar t = new BeanCar();
                t.setCar_id(rs.getString(1));
                t.setCar_state(rs.getString(2));
                t.setModel_id(rs.getString(3));
                t.setNet_id(rs.getString(4));
                result.add(t);
            }

            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public List<BeanOrder> inquiryUserOrder(String user_id){
    	List<BeanOrder> result=new ArrayList<BeanOrder>();

        try {

            //check existence and inquiry
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from order_tbl where user_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanOrder t = new BeanOrder();
                t.setUser_id(rs.getString(1));
                t.setCar_id(rs.getString(2));
                t.setOrder_id(rs.getString(3));
                t.setOrder_rent_net(rs.getString(4));
                t.setOrder_rent_time(rs.getDate(5));
                t.setOrder_return_net(rs.getString(6));
                t.setOrder_return_time(rs.getDate(7));
                t.setOrder_time(rs.getInt(8));
                t.setOrder_origin_price(rs.getFloat(9));
                t.setOrder_result_price(rs.getFloat(10));
                t.setOrder_prefer_id(rs.getString(11));
                t.setOrder_state(rs.getString(12));
                result.add(t);
            }

            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public List<BeanOrder> inquiryOrder(String model_id,String net_id) throws BusinessException{
        
        List<BeanOrder> result=new ArrayList<BeanOrder>();

        try {

            //check existence and inquiry
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from order_tbl where car_id in (select car_id from car_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);

            if(!Check.checkNullNoException(model_id) && !Check.checkNullNoException(net_id)){
                Check.checkExistence("car_model_tbl", "model_id", model_id);
                Check.checkExistence("net_tbl", "net_id", net_id);
                sql+=" where model_id = ? and net_id = ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, model_id);
                pst.setString(2, net_id);
            }else if(Check.checkNullNoException(model_id) && !Check.checkNullNoException(net_id)){
                Check.checkExistence("net_tbl", "net_id", net_id);
                sql+=" where net_id = ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, net_id);
            }else{
                Check.checkExistence("car_model_tbl", "model_id", model_id);
                sql+=" where model_id = ?)";
                pst = conn.prepareStatement(sql);
                pst.setString(1, model_id);
            }  
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanOrder t = new BeanOrder();
                t.setUser_id(rs.getString(1));
                t.setCar_id(rs.getString(2));
                t.setOrder_id(rs.getString(3));
                t.setOrder_rent_net(rs.getString(4));
                t.setOrder_rent_time(rs.getDate(5));
                t.setOrder_return_net(rs.getString(6));
                t.setOrder_return_time(rs.getDate(7));
                t.setOrder_time(rs.getInt(8));
                t.setOrder_origin_price(rs.getFloat(9));
                t.setOrder_result_price(rs.getFloat(10));
                t.setOrder_prefer_id(rs.getString(11));
                t.setOrder_state(rs.getString(12));
                result.add(t);
            }

            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return null;
    }
}
