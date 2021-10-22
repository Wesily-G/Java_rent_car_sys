package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.BeanNet;
import model.BeanStuff;
import model.BeanUser;
import util.BusinessException;
import util.DBUtil;

public class StuffManager {
	
	public List<BeanStuff> loadAllStuff() throws BusinessException{

        List<BeanStuff> result=new ArrayList<BeanStuff>();
        try {

            //load
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from stuff_tbl";
            PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            while(rs.next()){
            	BeanStuff t = new BeanStuff();
            	t.setStuff_id(rs.getString(1));
                t.setNet_id(rs.getString(2));
                t.setStuff_name(rs.getString(3));
                t.setStuff_password(rs.getString(4));
                result.add(t);
            }
            pst.execute();

            System.out.println("Load stuff info success.");
            
            pst.close();
        	conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanStuff addStuff(String stuff_id, String net_id, String stuff_name, String stuff_password) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(stuff_id);
            Check.checkNull(stuff_name);
            Check.checkNull(stuff_password);
            Check.checkRepeat("stuff_tbl","stuff_id", stuff_id);
            Check.checkExistence("net_tbl", "net_id", net_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into stuff_tbl (stuff_id,net_id,stuff_name,stuff_password) value(?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
            pst.setString(2, net_id);
            pst.setString(3, stuff_name);
            pst.setString(4, stuff_password);
            pst.execute();

            System.out.println("Add stuff info success.");
            
            pst.close();
            conn.close();
            return new BeanStuff(stuff_id,net_id,stuff_name,stuff_password);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanStuff modifyInfo(String stuff_id, String net_id, String stuff_name ,String stuff_password) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(stuff_id);
            Check.checkNull(net_id);
            Check.checkNull(stuff_name);
            Check.checkNull(stuff_password);
            Check.checkExistence("stuff_tbl","stuff_id", stuff_id);

            //update
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update stuff_tbl set net_id=?,stuff_name=?,stuff_password=? where stuff_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, net_id);
            pst.setString(2, stuff_name);
            pst.setString(3, stuff_password);
            pst.setString(4, stuff_id);
            pst.execute();

            System.out.println("Modify stuff info success.");
            
            pst.close();
            conn.close();
            return new BeanStuff(stuff_id,net_id,stuff_name,stuff_password);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteStuff(String stuff_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(stuff_id);
            Check.checkExistence("stuff_tbl","stuff_id", stuff_id);
            
            //delete check

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from stuff_tbl where stuff_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
            pst.execute();
            
            System.out.println("Delete stuff success.");
            
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public Boolean login(String stuff_id,String stuff_password) throws BusinessException{

        try {
            //check null,Existence
            Check.checkNull(stuff_id);
            Check.checkNull(stuff_password);
            Check.checkExistence("stuff_tbl","stuff_id", stuff_id);

            //check password
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select stuff_password,stuff_name from stuff_tbl where stuff_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
			java.sql.ResultSet rs = pst.executeQuery();
			
			
            
            if(rs.next() && rs.getString(1).equals(stuff_password)){
                System.out.println("Welcom to CC rent car system,"+rs.getString(2));
                pst.close();
                conn.close();
                return true;
            }else {
                pst.close();
                conn.close();
                throw new BusinessException("Wrong password!");
            }

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public void modifyPwd(String stuff_id,String oldPwd,String newPwd) throws BusinessException{
        try {
            //check null
            Check.checkNull(oldPwd);
            Check.checkNull(newPwd);

            //check password and change
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select stuff_password,stuff_id from stuff_tbl where stuff_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
			java.sql.ResultSet rs = pst.executeQuery();
			
			pst.close();
            conn.close();
            
            if(rs.next() && rs.getString(1).equals(oldPwd)){
                sql = "update stuff_tbl set stuff_password = ? where stuff_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, newPwd);
                pst.setString(2, stuff_id);
                pst.execute();
            }else
                throw new BusinessException("Wrong old password!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public BeanStuff findStuff(String stuff_id) throws BusinessException{

        try {
        	//check existence
            Check.checkExistence("stuff_tbl", "stuff_id", stuff_id);
            
            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from stuff_tbl where stuff_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
            java.sql.ResultSet rs = pst.executeQuery();
            System.out.println("Find stuff info success.");
            rs.next();
            BeanStuff result = new BeanStuff(stuff_id,rs.getString(2),rs.getString(3),rs.getString(4));
            
            pst.close();
            conn.close();
            return result;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
    
    public String nameToID(String stuff_name) throws BusinessException {
    	HashMap<String, String> stuffList = new HashMap<String, String>();
    	List<BeanStuff> allStuff = new StuffManager().loadAllStuff();
		for (int i1 = 0; i1 < allStuff.size(); i1++) {
			stuffList.put(allStuff.get(i1).getStuff_name(), allStuff.get(i1).getStuff_id());
		}
		return stuffList.get(stuff_name);
    }
    
}
