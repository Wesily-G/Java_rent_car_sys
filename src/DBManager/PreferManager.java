package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JOptionPane;

import model.BeanOrder;
import model.BeanPrefer;
import util.BusinessException;
import util.DBUtil;

public class PreferManager {
    public List<BeanPrefer> loadAllPrefer() throws BusinessException{

        List<BeanPrefer> result=new ArrayList<BeanPrefer>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from prefer_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanPrefer t = new BeanPrefer();
                t.setPrefer_id(rs.getString(1));
                t.setPrefer_net(rs.getString(2));
                t.setPrefer_model(rs.getString(3));
                t.setPrefer_amount(rs.getFloat(4));
                t.setPrefer_start(rs.getDate(5));
                t.setPrefer_end(rs.getDate(6));
                result.add(t);
            }

            System.out.println("Load prefer info success.");
            
            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanPrefer addPrefer(String prefer_id,String prefer_net,String prefer_model,float prefer_amount,Date prefer_start,Date prefer_end)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(prefer_id);
            Check.checkNull(prefer_net);
            Check.checkNull(prefer_model);
            Check.checkFushu(prefer_amount);
            Check.checkNull(String.valueOf(prefer_amount));
            Check.checkNull(String.valueOf(prefer_start));
            Check.checkNull(String.valueOf(prefer_end));
            Check.checkRepeat("prefer_tbl","prefer_id", prefer_id);
            Check.dayCheck(prefer_start, prefer_end);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into prefer_tbl (prefer_id,prefer_net,prefer_model,prefer_amount,prefer_start,prefer_end) "+
                "value(?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, prefer_id);
            pst.setString(2, prefer_net);
            pst.setString(3, prefer_model);
            pst.setFloat(4, prefer_amount);
            pst.setDate(5, new java.sql.Date(prefer_start.getTime()));
            pst.setDate(6, new java.sql.Date(prefer_end.getTime()));
            pst.execute();

            System.out.println("Add prefer info success.");
            
            pst.close();
            conn.close();
            return new BeanPrefer(prefer_id,prefer_net,prefer_model,prefer_amount,prefer_start,prefer_end);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanPrefer modifyPrefer(String prefer_id,String prefer_net,String prefer_model,float prefer_amount,Date prefer_start,Date prefer_end)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(prefer_id);
            Check.checkNull(prefer_net);
            Check.checkNull(prefer_model);
            Check.checkFushu(prefer_amount);
            Check.checkNull(String.valueOf(prefer_amount));
            Check.checkNull(String.valueOf(prefer_start));
            Check.checkNull(String.valueOf(prefer_end));
            Check.checkExistence("prefer_tbl","prefer_id", prefer_id);
            Check.dayCheck(prefer_start, prefer_end);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update prefer_tbl set prefer_net=?,prefer_model=?,prefer_amount=?,prefer_start=?,prefer_end=? where prefer_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(6, prefer_id);
            pst.setString(1, prefer_net);
            pst.setString(2, prefer_model);
            pst.setFloat(3, prefer_amount);
            pst.setDate(4, new java.sql.Date(prefer_start.getTime()));
            pst.setDate(5, new java.sql.Date(prefer_end.getTime()));
            pst.execute();

            System.out.println("Modify prefer info success.");
            
            pst.close();
            conn.close();
            return new BeanPrefer(prefer_id,prefer_net,prefer_model,prefer_amount,prefer_start,prefer_end);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deletePrefer(String prefer_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(prefer_id);
            Check.checkExistence("prefer_tbl","prefer_id", prefer_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from prefer_tbl where prefer_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, prefer_id);
            pst.execute();
            
            System.out.println("Delete prefer success.");
            
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
 
    public BeanPrefer findPrefer(String prefer_id) throws BusinessException {
    	try {
    		Check.checkExistence("prefer_tbl", "prefer_id", prefer_id);
            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from prefer_tbl where prefer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, prefer_id);
            java.sql.ResultSet rs = pst.executeQuery();
            System.out.println("Find prefer info success.");
            rs.next();
            BeanPrefer result = new BeanPrefer(prefer_id,rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getDate(5),rs.getDate(6));
            
            pst.close();
            conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    public void usePrefer(String prefer_id,String user_id) throws BusinessException{
        try {
            BeanPrefer useprefer = new BeanPrefer();

            //get info of prefer
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from prefer_tbl where prefer_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, prefer_id);
			ResultSet rs = pst.executeQuery();
            if(rs.next()){
                useprefer.setPrefer_id(rs.getString(1));
                useprefer.setPrefer_net(rs.getString(2));
                useprefer.setPrefer_model(rs.getString(3));
                useprefer.setPrefer_amount(rs.getFloat(4));
                useprefer.setPrefer_start(rs.getDate(5));
                useprefer.setPrefer_end(rs.getDate(6));
            }

            sql = "select * from order_tbl where user_id = ? and order_state != 'finish' ";
            pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
            rs = pst.executeQuery();
            if(rs.next() && !Check.checkNullNoException(rs.getString(11))){
                if(new CarManager().carGetModel(rs.getString(2)).getModel_id().equals(useprefer.getPrefer_model()) == false)
                    throw new BusinessException("Error model.");
                else if(! (rs.getDate(5).getTime() >= useprefer.getPrefer_start().getTime() && rs.getDate(5).getTime() <= useprefer.getPrefer_end().getTime()))
                    throw new BusinessException("Wrong time.");
                else if(rs.getString(11).equals(useprefer.getPrefer_id()))
                	throw new BusinessException("You can not use the same prefer twice");
                else if(!Check.checkNullNoException(rs.getString(11)))
                	throw new BusinessException("You have used one prefer.");
            }
            
            sql = "update order_tbl set order_prefer_id = ? where user_id = ?";
            pst = conn.prepareStatement(sql);
            pst.setString(1, useprefer.getPrefer_id());
            pst.setString(2, user_id);
            pst.execute();
            
            System.out.println("Use prefer success.");
            JOptionPane.showMessageDialog(null, "优惠券使用成功", "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();


        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
