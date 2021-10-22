package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.BeanAssign;
import model.BeanOrder;
import model.BeanUser;
import util.BusinessException;
import util.DBUtil;

public class OrderManager {

    public List<BeanOrder> loadAllOrder() throws BusinessException{

        List<BeanOrder> result=new ArrayList<BeanOrder>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from order_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
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
            pst.execute();

            System.out.println("Load order info success.");
            
            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public List<BeanOrder> loadAllOrderForUser(String user_id) throws BusinessException{

        List<BeanOrder> result=new ArrayList<BeanOrder>();
        try {

            //load
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
            pst.execute();

            System.out.println("Load order info success.");
            
            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanOrder findOrder(String order_id) throws BusinessException{

        try {
            Check.checkExistence("order_tbl", "order_id", order_id);
            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from order_tbl where order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, order_id);
            java.sql.ResultSet rs = pst.executeQuery();           
            rs.next();
            BeanOrder result = new BeanOrder(order_id,rs.getString(2),rs.getString(3),rs.getString(4),rs.getDate(5),rs.getString(6),rs.getDate(7),rs.getInt(8),rs.getFloat(9),rs.getFloat(10),rs.getString(11),rs.getString(12));
            
            System.out.println("Find order info success.");
            
            pst.close();
            conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanOrder addOrder(String user_id,String car_id,String order_rent_net,String order_return_net) throws BusinessException{

        try {
        	
            //check unfinished & add order
            Connection conn = null;
            conn=DBUtil.getConnection();
            Check.checkExistence("net_tbl", "net_id", order_rent_net);
            Check.checkExistence("net_tbl", "net_id", order_return_net);
            
            String sql = "select * from order_tbl where user_id=? and order_state != 'finish' ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
            java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())
            	throw new BusinessException("You have unfinished order!");
            
            
            BeanOrder addOrder = new BeanOrder(user_id,car_id,Check.nextID("order_tbl", "order_id", "order"),order_rent_net,null,
        			order_return_net,null,0,0,0,null,"waiting");
            sql = "insert into order_tbl(user_id,car_id,order_id,order_rent_net,\r\n" + 
            	"order_return_net,order_state ) value(?,?,?,?,?,?)";
            pst = conn.prepareStatement(sql);
            pst.setString(1,addOrder.getUser_id());
            pst.setString(2, addOrder.getCar_id());
            pst.setString(3, addOrder.getOrder_id());
            pst.setString(4, addOrder.getOrder_rent_net());
//            pst.setDate(5, new java.sql.Date(addOrder.getOrder_rent_time().getTime()));
            pst.setString(5, addOrder.getOrder_return_net());
            pst.setString(6, addOrder.getOrder_state());
            pst.execute();
            
            sql = "update car_tbl set car_state = 'wait' where car_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,car_id);
            pst.execute();
            
            System.out.println("Add order info success.");
            JOptionPane.showMessageDialog(null, "下单成功", "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();
            return addOrder;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void intoCar(String order_id) throws BusinessException {
    	try {
    		if(!(new OrderManager().findOrder(order_id).getOrder_state().equals("waiting")))
    			throw new BusinessException("Wrong operate");
            
            //update
    		BeanOrder currOrder = new OrderManager().findOrder(order_id);
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update order_tbl set order_rent_time=?,order_state='renting' where order_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(2, order_id);
            pst.execute();
            
            sql = "update car_tbl set car_state = 'renting' where car_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,currOrder.getCar_id());
            pst.execute();
            
            System.out.println("Into car success.");
            JOptionPane.showMessageDialog(null, "租车成功", "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    	
    }

    public void outOfCar(String order_id) throws BusinessException {
    	try {
    		String state=null;
    		ResultSet rs;
    		float prefer=0;
    		float discount=1;
    		
    		//check state
    		if(!(new OrderManager().findOrder(order_id).getOrder_state().equals("renting")))
    			throw new BusinessException("Wrong operate");
    		
            //update & check destination & check price
    		BeanOrder currOrder = new OrderManager().findOrder(order_id);
    		currOrder.setOrder_return_time(new Date());
    		if(currOrder.getOrder_return_net().equals(currOrder.getOrder_rent_net()))
    			state="idle";
    		else
    			state="assiging";
    		
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update order_tbl set order_return_time=?,order_state=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.setString(2, "finish");
            pst.execute();
            
            sql = "update car_tbl set car_state = ? where car_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, state);
            pst.setString(2,currOrder.getCar_id());
            pst.execute();
            
            if(state.equals("assiging"))
            	new OrderManager().addAssign(currOrder.getCar_id(), currOrder.getOrder_rent_net(), currOrder.getOrder_return_net(), new Date());
            
            //caculate amount(origin & prefer & discount)
            float result_price = (float) 0;
            @SuppressWarnings("deprecation")
			long days = Math.abs(currOrder.getOrder_return_time().getDay()-currOrder.getOrder_rent_time().getDay())+1;
            float day_price = new CarManager().carGetModel(currOrder.getCar_id()).getModel_price();
            currOrder.setOrder_origin_price(days*day_price);
            currOrder.setOrder_time(Integer.parseInt(String.valueOf(days)));
            result_price = days*day_price;
            
            if(Check.checkNullNoException(currOrder.getOrder_prefer_id()))
            	;
            else {
            	sql="select prefer_amount from prefer_tbl where prefer_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, currOrder.getOrder_prefer_id());
                rs = pst.executeQuery();
                if(rs.next()) {
                	prefer = rs.getFloat(1);
                	System.out.println(prefer);
                	if(result_price - prefer >= 0)
                		result_price -= prefer;
                	else
                		result_price = 0;
                }
            }
            
            sql="select * from discount_tbl where discount_net=? and discount_model=? and DATEDIFF(now(),discount_start)>=0 and DATEDIFF(now(),discount_end)<=0";
            pst=conn.prepareStatement(sql);
            pst.setString(1, currOrder.getOrder_rent_net());
            pst.setString(2, new CarManager().carGetModel(currOrder.getCar_id()).getModel_id());
//            pst.setDate(3, new java.sql.Date(currOrder.getOrder_rent_time().getTime()));
//            pst.setDate(4, new java.sql.Date(currOrder.getOrder_rent_time().getTime()));
            rs = pst.executeQuery();
            if(rs.next()) {
            	if(rs.getInt(5)>0) {
            		discount = rs.getFloat(4);
            		result_price = result_price*discount;
            		System.out.println(rs.getFloat(4));
            		sql="update discount_tbl set discount_count=discount_count-1 where discount_id = ?";
            		pst = conn.prepareStatement(sql);
            		pst.setString(1,rs.getString(1));
            		pst.execute();
            	}
            }
            
            //upload origin,result price
            currOrder.setOrder_result_price(result_price);
            sql = "update order_tbl set order_origin_price=?,order_result_price=?,order_time=? where order_id=? ";
            pst = conn.prepareStatement(sql);
            System.out.println(currOrder.getOrder_origin_price()+"  "+days+"  "+day_price+"  "+"  "+currOrder.getOrder_result_price());
            pst.setFloat(1, currOrder.getOrder_origin_price());
            pst.setFloat(2, currOrder.getOrder_result_price());
            pst.setInt(3, currOrder.getOrder_time());
            pst.setString(4, currOrder.getOrder_id());
            pst.execute();
            
            String output = "原价："+currOrder.getOrder_origin_price()+" = "+days+" * "+day_price+"\n"+"优惠金额："+prefer+",折扣："+discount+"\n"+"还车成功，结算金额:"+currOrder.getOrder_result_price(); 
            System.out.println("Return car success.");
            JOptionPane.showMessageDialog(null, output, "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    	
    }
    
    public void cancelOrder(String order_id) throws BusinessException {
    	try {
            
            //find,cancel
    		BeanOrder currOrder = new OrderManager().findOrder(order_id);
            Connection conn = null;
            conn=DBUtil.getConnection();
            if(!currOrder.getOrder_state().equals("waiting"))
            	throw new BusinessException("You can not cancel your order.");
            
            String sql = "delete from order_tbl where order_id=? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, order_id);
            pst.execute();
            
            sql = "update car_tbl set car_state = 'idle' where car_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,currOrder.getCar_id());
            pst.execute();
            
            System.out.println("Cancel order success.");
            JOptionPane.showMessageDialog(null, "取消成功", "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();
            
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    	
    }
    
    public BeanAssign addAssign(String car_id,String assign_in,String assign_out,Date assign_time) {
    	
    	try {
            
            //add
            Connection conn = null;
            conn=DBUtil.getConnection();
            BeanAssign addAssign = new BeanAssign(car_id,assign_in,assign_out,assign_time); 
            String sql = "insert into car_assign_tbl(car_id,assign_in,assign_out,assign_time) value(?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1,addAssign.getCar_id());
            pst.setString(2, addAssign.getAssign_in());
            pst.setString(3, addAssign.getAssign_out());
            pst.setDate(4, new java.sql.Date(addAssign.getAssign_time().getTime()));
            pst.execute();
            
            sql = "update car_tbl set car_state = 'idle' where car_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1,car_id);
            pst.execute();
            
            System.out.println("Add assign info success.");
            
            pst.close();
            conn.close();
            return addAssign;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
}
