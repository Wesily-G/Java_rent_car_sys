package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import model.BeanCar;
import model.BeanCarModel;
import model.BeanCarType;
import model.BeanDiscard;
import model.BeanNet;
import util.BusinessException;
import util.DBUtil;

public class CarManager {

    //CarType
    public List<BeanCarType> loadAllType() throws BusinessException{

        List<BeanCarType> result=new ArrayList<BeanCarType>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from car_type_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanCarType t = new BeanCarType();
                t.setType_id(rs.getString(1));
                t.setType_name(rs.getString(2));
                t.setType_describe(rs.getString(3));
                result.add(t);
            }
            pst.execute();

            System.out.println("Load car type info success.");
            pst.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCarType addCarType(String type_id, String type_name, String type_describe) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(type_id);
            Check.checkNull(type_name);
            Check.checkRepeat("car_type_tbl","type_id", type_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into car_type_tbl (type_id,type_name,type_describe) value(?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, Check.nextID("car_type_tbl","type_id","type"));
            pst.setString(2, type_name);
            pst.setString(3, type_describe);
            pst.execute();

            System.out.println("Add car type info success.");
            pst.close();
            conn.close();
            return new BeanCarType(type_id,type_name,type_describe);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCarType modifyCarType(String type_id, String type_name, String type_describe) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(type_id);
            Check.checkNull(type_name);
            Check.checkExistence("car_type_tbl","type_id", type_id);

            //update
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update car_type_tbl set type_name = ?,type_describe = ? where type_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, type_name);
            pst.setString(2, type_describe);
            pst.setString(3, type_id);
            pst.execute();

            System.out.println("Modify car type info success.");
            pst.close();
            conn.close();
            return new BeanCarType(type_id,type_name,type_describe);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteCarType(String type_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(type_id);
            Check.checkExistence("car_type_tbl","type_id", type_id);
            
            //delete check
            Check.deleteCheck("car_model_tbl", "type_id", type_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from car_type_tbl where type_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, type_id);
            pst.execute();
            
            System.out.println("Delete car type success.");
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public BeanCarType findCarType(String type_id) throws BusinessException {
    	try {
    		String type_name = null;
    		String type_describe = null;
    		
            //check null,existence
            Check.checkNull(type_id);
            Check.checkExistence("type_tbl","type_id", type_id);

            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from type_tbl where type_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, type_id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            	type_name=rs.getString(2);
            	type_describe=rs.getString(3);
            }

            System.out.println("Find type info success.");
            pst.close();
            conn.close();
            return new BeanCarType(type_id,type_name,type_describe);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    
    //CarModel
    public List<BeanCarModel> loadAllModel() throws BusinessException{

        List<BeanCarModel> result=new ArrayList<BeanCarModel>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from car_model_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanCarModel t = new BeanCarModel();
                t.setModel_id(rs.getString(1));
                t.setType_id(rs.getString(2));
                t.setModel_name(rs.getString(3));
                t.setModel_brand(rs.getString(4));
                t.setModel_emissions(rs.getInt(5));
                t.setModel_auto(rs.getBoolean(6));
                t.setModel_seats(rs.getInt(7));
                t.setModel_price(rs.getFloat(8));
                result.add(t);
            }
            pst.execute();

            System.out.println("Load car model info success.");
            pst.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCarModel addCarModel(String model_id,String type_id,String model_name,String model_brand,int model_emission,
        boolean model_auto,int model_seats,Float model_price) throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(model_id);
            Check.checkNull(type_id);
            Check.checkFushu(model_seats);
            Check.checkFushu(model_emission);
            Check.checkFushu(model_price);
            Check.checkNull(model_name);
            Check.checkNull(model_brand);
            Check.checkNull(String.valueOf(model_emission));
            Check.checkNull(String.valueOf(model_auto));
            Check.checkNull(String.valueOf(model_seats));
            Check.checkNull(String.valueOf(model_price));
            Check.checkRepeat("car_model_tbl","model_id", model_id);
            Check.checkExistence("car_type_tbl","type_id", type_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into car_model_tbl (model_id,type_id,model_name,model_brand,model_emissions,model_auto,model_seats,model_price) "+
                "value(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, Check.nextID("car_model_tbl", "model_id", "model"));
            pst.setString(2, type_id);
            pst.setString(3, model_name);
            pst.setString(4, model_brand);
            pst.setInt(5, model_emission);
            pst.setBoolean(6, model_auto);
            pst.setInt(7, model_seats);
            pst.setFloat(8, model_price);
            pst.execute();

            System.out.println("Add car info success.");
            pst.close();
            conn.close();
            return new BeanCarModel(model_id,type_id,model_name,model_brand,model_emission,model_auto,model_seats,model_price);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCarModel modifyCarModel(String model_id,String type_id,String model_name,String model_brand,int model_emission,
        boolean model_auto,int model_seats,Float model_price) throws BusinessException{

        try {
            //check null,existence
            Check.checkNull(model_id);
            Check.checkNull(type_id);
            Check.checkFushu(model_seats);
            Check.checkFushu(model_emission);
            Check.checkFushu(model_price);
            Check.checkNull(model_name);
            Check.checkNull(model_brand);
            Check.checkNull(String.valueOf(model_emission));
            Check.checkNull(String.valueOf(model_auto));
            Check.checkNull(String.valueOf(model_seats));
            Check.checkNull(String.valueOf(model_price));
            Check.checkExistence("car_model_tbl","model_id", model_id);
            Check.checkExistence("car_type_tbl","type_id", type_id);


            //modify
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update car_model_tbl set type_id=?,model_name=?,model_brand=?,model_emissions=?,model_auto=?,model_seats=?,model_price=? where model_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(8, model_id);
            pst.setString(1, type_id);
            pst.setString(2, model_name);
            pst.setString(3, model_brand);
            pst.setInt(4, model_emission);
            pst.setBoolean(5, model_auto);
            pst.setInt(6, model_seats);
            pst.setFloat(7, model_price);
            pst.execute();

            System.out.println("Modify car info success.");
            pst.close();
            conn.close();
            return new BeanCarModel(model_id,type_id,model_name,model_brand,model_emission,model_auto,model_seats,model_price);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteCarModel(String model_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(model_id);
            Check.checkExistence("car_model_tbl","model_id", model_id);
            
            //delete check
            Check.deleteCheck("car_tbl", "model_id", model_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from car_model_tbl where model_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, model_id);
            pst.execute();
            
            System.out.println("Delete car model success.");
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public BeanCarModel findCarModel(String model_id) throws BusinessException {
    	try {
    		String type_id =null ;
    		String model_name = null;
    		String model_brand = null;
    		int model_emissions = 0;
    		Boolean model_auto = null;
    		int model_seats=0;
    		Float model_price = null;
    		
    		
    		
            //check null,existence
            Check.checkNull(model_id);
            Check.checkExistence("car_model_tbl","model_id", model_id);

            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from car_model_tbl where model_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, model_id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            	type_id=rs.getString(2);
            	model_name=rs.getString(3);
            	model_brand=rs.getString(4);
            	model_emissions=rs.getInt(5);
            	model_auto=rs.getBoolean(6);
            	model_seats=rs.getInt(7);
            	model_price=rs.getFloat(8);
            }

            System.out.println("Find model info success.");
            pst.close();
            conn.close();
            return new BeanCarModel(model_id,type_id,model_name,model_brand,model_emissions,model_auto,model_seats,model_price);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    public String nameToID(String model_name) throws BusinessException {
    	HashMap<String, String> modelList = new HashMap<String, String>();
    	List<BeanCarModel> allModel = new CarManager().loadAllModel();
		for (int i1 = 0; i1 < allModel.size(); i1++) {
			modelList.put(allModel.get(i1).getModel_name(), allModel.get(i1).getModel_id());
		}
		return modelList.get(model_name);
    }
    
    //Car
    public List<BeanCar> loadAllCar() throws BusinessException{

        List<BeanCar> result=new ArrayList<BeanCar>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from car_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanCar t = new BeanCar();
                t.setCar_id(rs.getString(1));
                t.setNet_id(rs.getString(2));
                t.setModel_id(rs.getString(3));
                t.setCar_license(rs.getString(4));
                t.setCar_state(rs.getString(5));
                result.add(t);
            }
            pst.execute();

            System.out.println("Load car info success.");
            pst.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCar addCar(String car_id,String net_id,String model_id,String car_license,String car_state)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(car_id);
            Check.checkNull(net_id);
            Check.checkNull(model_id);
            Check.checkNull(car_license);
            Check.checkNull(car_state);
            Check.checkRepeat("car_tbl","car_id", car_id);
            Check.checkExistence("car_model_tbl","model_id", model_id);
            Check.checkExistence("net_tbl", "net_id", net_id);
            Check.isCarNo(car_license);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into car_tbl (car_id,net_id,model_id,car_license,car_state) "+
                "value(?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, Check.nextID("car_tbl", "car_id", "car"));
            pst.setString(2, net_id);
            pst.setString(3, model_id);
            pst.setString(4, car_license);
            pst.setString(5, car_state);
            pst.execute();

            System.out.println("Add car info success.");
            pst.close();
            conn.close();
            return new BeanCar(car_id,net_id,model_id,car_license,car_state);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCar modifyCar(String car_id,String net_id,String model_id,String car_license,String car_state)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(car_id);
            Check.checkNull(net_id);
            Check.checkNull(model_id);
            Check.checkNull(car_license);
            Check.checkNull(car_state);
            Check.checkExistence("car_tbl","car_id", car_id);
            Check.checkExistence("car_model_tbl","model_id", model_id);
            Check.checkExistence("net_tbl", "net_id", net_id);
            Check.isCarNo(car_license);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update car_tbl set net_id=?,model_id=?,car_license=?,car_state=? where car_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(5, car_id);
            pst.setString(1, net_id);
            pst.setString(2, model_id);
            pst.setString(3, car_license);
            pst.setString(4, car_state);
            pst.execute();

            System.out.println("Modify car info success.");
            pst.close();
            conn.close();
            return new BeanCar(car_id,net_id,model_id,car_license,car_state);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanDiscard discardCar(String stuff_id,String car_id,String discard_id,Date discard_time,String discard_illustrate) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(car_id);
            Check.checkExistence("car_tbl","car_id", car_id);
            
            //check stuff&car
            String stuff_net = new StuffManager().findStuff(stuff_id).getNet_id();
            String car_net = new CarManager().findCar(car_id).getNet_id();
            if(!Check.checkNullNoException(stuff_net) && car_net.equals(stuff_net))
            	throw new BusinessException("You have no right to discard the car.");
            
            //delete check
            Check.deleteCheck("order_tbl", "car_id", car_id);
            Check.deleteCheck("car_assign_tbl", "car_id", car_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from car_tbl where car_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, car_id);
            pst.execute();
            
            //discard
            BeanDiscard addDiscard = new BeanDiscard(stuff_id,car_id,discard_id,discard_time,discard_illustrate);
            sql = "insert into discard_tbl (stuff_id,car_id,discard_id,discard_time,discard_illustrate) value(?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
            pst.setString(2, car_id);
            pst.setString(3,discard_id);
            pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(5, discard_illustrate);
            pst.execute();
            
            System.out.println("Discard car success.");
            pst.close();
            conn.close();
            
            return addDiscard;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    	return null;

    }
    
    public BeanCarModel carGetModel(String car_id){
        try {
            String model_id="";

            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from car_tbl where car_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, car_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())
                model_id=rs.getString(3);

            sql = "select * from car_model_tbl where model_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, model_id);
            rs = pst.executeQuery();

            if(rs.next()){
                BeanCarModel carmodel = new BeanCarModel();
                carmodel.setModel_id(rs.getString(1));
                carmodel.setType_id(rs.getString(2));
                carmodel.setModel_name(rs.getString(3));
                carmodel.setModel_brand(rs.getString(4));
                carmodel.setModel_emissions(rs.getInt(5));
                carmodel.setModel_auto(rs.getBoolean(6));
                carmodel.setModel_seats(rs.getInt(7));
                carmodel.setModel_price(rs.getFloat(8));
                return carmodel;
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanCar findCar(String car_id) throws BusinessException {
    	try {
    		String net_id =null ;
    		String model_id = null;
    		String car_license = null;
    		String car_state = null;
    		
            //check null,existence
            Check.checkNull(car_id);
            Check.checkExistence("car_tbl","car_id", car_id);

            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = " select * from car_tbl where car_id=? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, car_id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            	net_id=rs.getString(2);
            	model_id=rs.getString(3);
            	car_license=rs.getString(4);
            	car_state=rs.getString(5);
            }

            System.out.println("Find car info success.");
            pst.close();
            conn.close();
            return new BeanCar(car_id,net_id,model_id,car_license,car_state);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }


}
