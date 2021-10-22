package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.BeanDiscount;
import model.BeanPrefer;
import util.BusinessException;
import util.DBUtil;

public class DiscountManager {
	public List<BeanDiscount> loadAllDiscount() throws BusinessException{

        List<BeanDiscount> result=new ArrayList<BeanDiscount>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from discount_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanDiscount t = new BeanDiscount();
                t.setDiscount_id(rs.getString(1));
                t.setDiscount_net(rs.getString(2));
                t.setDiscount_model(rs.getString(3));
                t.setDiscount(rs.getFloat(4));
                t.setDiscount_count(rs.getInt(5));
                t.setDiscount_start(rs.getTimestamp(6));
                t.setDiscount_end(rs.getTimestamp(7));
                result.add(t);
            }

            System.out.println("Load discount info success.");
            pst.close();
            conn.close();

            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanDiscount addDiscount(String discount_id,String discount_net,String discount_model,float discount,int discount_count,Date discount_start,Date discount_end)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(discount_id);
            Check.checkNull(discount_net);
            Check.checkNull(discount_model);
            Check.checkNull(String.valueOf(discount));
            Check.checkFushu(discount);
            Check.checkFushu(discount_count);
            Check.checkNull(String.valueOf(discount_count));
            Check.checkNull(String.valueOf(discount_start));
            Check.checkNull(String.valueOf(discount_end));
            Check.checkRepeat("discount_tbl","discount_id", discount_id);
            Check.dayCheck(discount_start, discount_end);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into discount_tbl (discount_id,discount_net,discount_model,discount,discount_count,discount_start,discount_end) "+
                "value(?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, discount_id);
            pst.setString(2, discount_net);
            pst.setString(3, discount_model);
            pst.setFloat(4, discount);
            pst.setInt(5, discount_count);
            pst.setDate(6, new java.sql.Date(discount_start.getTime()));
            pst.setDate(7, new java.sql.Date(discount_end.getTime()));
            pst.execute();

            System.out.println("Add discount info success.");
            pst.close();
            conn.close();
            return new BeanDiscount(discount_id,discount_net,discount_model,discount,discount_count,discount_start,discount_end);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanDiscount modifyDiscount(String discount_id,String discount_net,String discount_model,float discount,int discount_count,Date discount_start,Date discount_end)throws BusinessException{

        try {
        	//check null,repeat,existence
            Check.checkNull(discount_id);
            Check.checkNull(discount_net);
            Check.checkNull(discount_model);
            Check.checkNull(String.valueOf(discount));
            Check.checkFushu(discount);
            Check.checkFushu(discount_count);
            Check.checkNull(String.valueOf(discount_count));
            Check.checkNull(String.valueOf(discount_start));
            Check.checkNull(String.valueOf(discount_end));
            Check.checkExistence("discount_tbl","discount_id", discount_id);
            Check.dayCheck(discount_start, discount_end);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update discount_tbl set discount_net=?,discount_model=?,discount=?,discount_count=?,discount_start=?,discount_end=? where discount_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(7, discount_id);
            pst.setString(1, discount_net);
            pst.setString(2, discount_model);
            pst.setFloat(3, discount);
            pst.setInt(4, discount_count);
            pst.setDate(5, new java.sql.Date(discount_start.getTime()));
            pst.setDate(6, new java.sql.Date(discount_end.getTime()));
            pst.execute();

            System.out.println("Modify prefer info success.");
            pst.close();
            conn.close();
            return new BeanDiscount(discount_id,discount_net,discount_model,discount,discount_count,discount_start,discount_end);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteDiscount(String discount_id) throws BusinessException{
    	try {
            //check null
            Check.checkNull(discount_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from discount_tbl where discount_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, discount_id);
            pst.execute();
            
            System.out.println("Delete prefer success.");
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
	
	public BeanDiscount findDiscount(String discount_id) throws BusinessException {
    	try {
            Check.checkExistence("discount_tbl", "discount_id", discount_id);
            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from discount_tbl where discount_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, discount_id);
            java.sql.ResultSet rs = pst.executeQuery();
            System.out.println("Find discount info success.");
            rs.next();
            BeanDiscount result = new BeanDiscount(discount_id,rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getInt(5),rs.getDate(6),rs.getDate(7));
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
