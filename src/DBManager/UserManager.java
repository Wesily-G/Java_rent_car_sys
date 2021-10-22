package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.JOptionPane;

import model.BeanUser;
import util.BusinessException;
import util.DBUtil;

public class UserManager {

    public BeanUser addUser(String user_id, String user_name, Boolean user_sex, String user_password,
        String user_phone, String user_mail, String user_city) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(user_id);
            Check.checkNull(user_name);
            Check.checkNull(String.valueOf(user_sex));
            Check.checkNull(user_password);
            Check.checkNull(user_phone);
            Check.matchPhone(user_phone);
            Check.checkNull(user_mail);
            Check.isMail(user_mail);
            Check.checkNull(user_city);
            Check.checkRepeat("user_tbl","user_id", user_id);

            //insert
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "insert into user_tbl (user_id,user_name,user_sex,user_password,user_phone,user_mail,user_city,user_register_time) value(?,?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
            pst.setString(2, user_name);
            pst.setBoolean(3, user_sex);
            pst.setString(4, user_password);
            pst.setString(5, user_phone);
            pst.setString(6, user_mail);
            pst.setString(7, user_city);
            pst.setTimestamp(8, new java.sql.Timestamp(System.currentTimeMillis()));
            pst.execute();

            System.out.println("Add user info success.");
            JOptionPane.showMessageDialog(null, "注册成功", "消息",JOptionPane.PLAIN_MESSAGE);
            
            pst.close();
            conn.close();
            return new BeanUser(user_id,user_name,user_sex,user_password,user_phone,user_mail,user_city,new java.util.Date());

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public BeanUser modifyInfo(String user_id, String user_name, Boolean user_sex,
        String user_phone, String user_mail, String user_city) throws BusinessException{

        try {
            //check null,repeat
            Check.checkNull(user_id);
            Check.checkNull(user_name);
            Check.checkNull(String.valueOf(user_sex));
            Check.checkNull(user_phone);
            Check.matchPhone(user_phone);
            Check.checkNull(user_mail);
            Check.isMail(user_mail);
            Check.checkNull(user_city);
            Check.checkExistence("user_tbl","user_id", user_id);

            //update
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "update user_tbl set user_name=?,user_sex=?,user_phone=?,user_mail=?,user_city=? where user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_name);
            pst.setBoolean(2, user_sex);
            pst.setString(3, user_phone);
            pst.setString(4, user_mail);
            pst.setString(5, user_city);
            pst.setString(6, user_id);
            pst.execute();

            sql = "select user_password,user_register_time from user_tbl where user_id = ? ";
            pst=conn.prepareStatement(sql);
            pst.setString(1, user_id);
			java.sql.ResultSet rs = pst.executeQuery();
            rs.next();
            String user_password = rs.getString(1);
            Date user_register_time = rs.getDate(2);

            System.out.println("Modify user info success.");
            
            pst.close();
            conn.close();
            return new BeanUser(user_id,user_name,user_sex,user_password,user_phone,user_mail,user_city,user_register_time);

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public void deleteUser(String user_id) throws BusinessException{
    	try {
            //check null,existence
            Check.checkNull(user_id);
            Check.checkExistence("user_tbl","stuff_id", user_id);
            
            //delete check
            Check.deleteCheck("order_tbl", "user_id", user_id);

            //delete
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "delete from user_tbl where user_id=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
            pst.execute();
            
            System.out.println("Delete user success.");
            
            pst.close();
            conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public Boolean login(String user_id,String user_password) throws BusinessException{

        try {
            //check null,Existence
            Check.checkNull(user_id);
            Check.checkNull(user_password);
            Check.checkExistence("user_tbl","user_id", user_id);

            //check password
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select user_password,user_id from user_tbl where user_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
			java.sql.ResultSet rs = pst.executeQuery();
            
            if(rs.next() && rs.getString(1).equals(user_password)){
                System.out.println("Welcom to CC rent car system,"+rs.getString(2));
                return true;
            }else
                throw new BusinessException("Wrong password!");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return false;
    }

    public void modifyPwd(String user_id,String oldPwd,String newPwd) throws BusinessException{
        try {
            //check null
            Check.checkNull(oldPwd);
            Check.checkNull(newPwd);

            //check password and change
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select user_password,user_id from user_tbl where user_id = ? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
			java.sql.ResultSet rs = pst.executeQuery();
			
            if(rs.next() && rs.getString(1).equals(oldPwd)){
                sql = "update user_tbl set user_password = ? where user_id = ?";
                pst = conn.prepareStatement(sql);
                pst.setString(1, newPwd);
                pst.setString(2, user_id);
                pst.execute();
            }else
                throw new BusinessException("Wrong old password!");
            
            pst.close();
        	conn.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public BeanUser findUser(String user_id) throws BusinessException{

        try {
            //find
            Connection conn = null;
            conn=DBUtil.getConnection();
            String sql = "select * from user_tbl where user_id = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user_id);
            java.sql.ResultSet rs = pst.executeQuery();
            System.out.println("Find user info success.");
            rs.next();
            BeanUser result = new BeanUser(user_id,rs.getString(2),rs.getBoolean(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getDate(8));
            
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
