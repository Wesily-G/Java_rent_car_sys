package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.BeanNet;
import util.BusinessException;
import util.DBUtil;

public class NetManager {
    //Net
    public List<BeanNet> loadAllNet() throws BusinessException{

        List<BeanNet> result=new ArrayList<BeanNet>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from net_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
                BeanNet t = new BeanNet();
                t.setNet_id(rs.getString(1));
                t.setNet_name(rs.getString(2));
                t.setNet_city(rs.getString(3));
                t.setNet_address(rs.getString(4));
                t.setNet_phone(rs.getString(5));
                result.add(t);
            }
            pst.execute();

            System.out.println("Load net info success.");
            
            pst.close();
            conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanNet addNet(String net_id,String net_name,String net_city,String net_address,String net_phone)throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(net_id);
            Check.checkNull(net_name);
            Check.checkNull(net_city);
            Check.checkNull(net_address);
            Check.checkNull(net_phone);
            Check.matchPhone(net_phone);
            Check.checkRepeat("net_tbl","net_id", net_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into net_tbl (net_id,net_name,net_city,net_address,net_phone) "+
                "value(?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, Check.nextID("net_tbl","net_id","net"));
            pst.setString(2, net_name);
            pst.setString(3, net_city);
            pst.setString(4, net_address);
            pst.setString(5, net_phone);
            pst.execute();

            System.out.println("Add net info success.");
            
            pst.close();
            conn.close();
            return new BeanNet(net_id,net_name,net_city,net_address,net_phone);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanNet modifyNet(String net_id,String net_name,String net_city,String net_address,String net_phone)throws BusinessException{

        try {
            //check null,repeat,existence
            Check.checkNull(net_id);
            Check.checkNull(net_name);
            Check.checkNull(net_city);
            Check.checkNull(net_address);
            Check.checkNull(net_phone);
            Check.matchPhone(net_phone);
            Check.checkExistence("net_tbl","net_id", net_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update net_tbl set net_name=?,net_city=?,net_address=?,net_phone=?  where net_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(5, net_id);
            pst.setString(1, net_name);
            pst.setString(2, net_city);
            pst.setString(3, net_address);
            pst.setString(4, net_phone);
            pst.execute();

            System.out.println("Modify net info success.");
            
            pst.close();
            conn.close();
            return new BeanNet(net_id,net_name,net_city,net_address,net_phone);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    public void deleteNet(String net_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(net_id);
            Check.checkExistence("net_tbl","net_id", net_id);
            
            //delete check
            Check.deleteCheck("order_tbl", "order_rent_net", net_id);
            Check.deleteCheck("order_tbl", "order_return_net", net_id);
            Check.deleteCheck("car_assign_tbl","assign_in",net_id);
            Check.deleteCheck("car_assign_tbl","assign_out",net_id);
            Check.deleteCheck("car_tbl", "net_id", net_id);
            Check.deleteCheck("stuff_tbl","net_id",net_id);
            Check.deleteCheck("prefer_tbl","prefer_net",net_id);
            Check.deleteCheck("discount_tbl","discount_net",net_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from net_tbl where net_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, net_id);
            pst.execute();
            
            System.out.println("Delete net success.");
            
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public BeanNet findNet(String net_id) throws BusinessException {
    	try {
    		String net_name =null ;
    		String net_city = null;
    		String net_address = null;
    		String net_phone = null;
    		
            //check null,existence
            Check.checkNull(net_id);
            Check.checkExistence("net_tbl","net_id", net_id);

            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from net_tbl where net_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, net_id);
            ResultSet rs = pst.executeQuery();
            if(rs.next()) {
            	net_name=rs.getString(2);
            	net_city=rs.getString(3);
            	net_address=rs.getString(4);
            	net_phone=rs.getString(5);
            }

            System.out.println("Find net info success.");
            
            pst.close();
            conn.close();
            return new BeanNet(net_id,net_name,net_city,net_address,net_phone);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public String nameToID(String net_name) throws BusinessException {
    	HashMap<String, String> netList = new HashMap<String, String>();
    	List<BeanNet> allNet = new NetManager().loadAllNet();
		for (int i1 = 0; i1 < allNet.size(); i1++) {
			netList.put(allNet.get(i1).getNet_name(), allNet.get(i1).getNet_id());
		}
		return netList.get(net_name);
    }
    
}
