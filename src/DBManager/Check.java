package DBManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import util.BusinessException;
import util.DBUtil;

public class Check {

    public static Boolean checkNull(String checkchars) throws BusinessException{
        if("".equals(checkchars) || checkchars==null)
            throw new BusinessException("空输入");
        else
            return true;
    }

    public static Boolean checkNullNoException(String checkchars){
        if("".equals(checkchars) || checkchars==null)
            return true;
        else
            return false;
    }
    
    public static Boolean checkFushu(Object value) throws BusinessException{
    	if(Float.parseFloat(value.toString())<0) {
    		throw new BusinessException("输入为负数");
    	}else
    		return false;
    }

    public static boolean isCarNo(String carNo) throws BusinessException{
        String pattern = "([京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{1}(([A-HJ-Z]{1}[A-HJ-NP-Z0-9]{5})|([A-HJ-Z]{1}(([DF]{1}[A-HJ-NP-Z0-9]{1}[0-9]{4})|([0-9]{5}[DF]{1})))|([A-HJ-Z]{1}[A-D0-9]{1}[0-9]{3}警)))|([0-9]{6}使)|((([沪粤川云桂鄂陕蒙藏黑辽渝]{1}A)|鲁B|闽D|蒙E|蒙H)[0-9]{4}领)|(WJ[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼·•]{1}[0-9]{4}[TDSHBXJ0-9]{1})|([VKHBSLJNGCE]{1}[A-DJ-PR-TVY]{1}[0-9]{5})";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(carNo);
        if (!m.matches()){
        	throw new BusinessException("错误的车牌号");
        }
        return true;
    }
    
    public static boolean isMail(String mail) throws BusinessException{
        String pattern = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(mail);
        if (!m.matches()){
        	throw new BusinessException("错误的邮箱号");
        }
        return true;
    }
    
    public static boolean matchPhone(String number) throws BusinessException{
        Check.checkNull(number);
        
        String pattern1 = "^[0][1-9]{2,3}-[0-9]{5,10}$";
        String pattern2 = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9]|17[0|6|7|8])\\d{8}$";
        Pattern p11 = Pattern.compile(pattern1);
        Pattern p21 = Pattern.compile(pattern2);
        Matcher m1 = p11.matcher(number);
        Matcher m2 = p21.matcher(number);
        if (m1.matches() || m2.matches()){
        	return true;
        }else
        	throw new BusinessException("错误的电话号");
    }

    //for insert
    public static Boolean checkRepeat(String DataBaseTableName,String sqlID_name,String ID) throws BusinessException{

        try {
			Connection conn = null;
            conn=DBUtil.getConnection();
			String sql = " select * from " + DataBaseTableName + " where " + sqlID_name + " = ? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, ID);
			java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())
                throw new BusinessException("已经存在"+ID);
            
            pst.close();
            conn.close();
            
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }

    //for add and modify
    public static Boolean checkExistence(String DataBaseTableName,String sqlID_name,String ID)throws BusinessException{
        try {
			Connection conn = null;
            conn=DBUtil.getConnection();
			String sql = " select * from " + DataBaseTableName + " where " + sqlID_name + " = ? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, ID);
			java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next()==false)
                throw new BusinessException(ID+"不存在");
            
            pst.close();
            conn.close();
            
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }
    
    //for checking delete reference
    public static Boolean deleteCheck(String DataBaseTableName,String sqlID_name,String ID)throws BusinessException{
        try {
			Connection conn = null;
            conn=DBUtil.getConnection();
			String sql = " select * from " + DataBaseTableName + " where " + sqlID_name + " = ? ";
			if(DataBaseTableName=="order_tbl")
				sql+=" and order_state != 'idle'";
			else if(DataBaseTableName=="car_assign_tbl")
				sql+=" and assign_time is not null";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, ID);
			java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())
                throw new BusinessException("无法删除正在被使用的"+ID);
            
            pst.close();
            conn.close();
            
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }

    public static Boolean isAdmin(String stuff_id) throws BusinessException{
        try {
			Connection conn = null;
            conn=DBUtil.getConnection();
			String sql = "select net_id from stuff_tbl where stuff_id = ? ";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1, stuff_id);
			java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next() && (Check.checkNullNoException(rs.getString(1)) ) ) {
            	pst.close();
            	conn.close();
            	return true;
            }else {
            	pst.close();
            	conn.close();
            	throw new BusinessException("你没有管理员权限");
            }
            
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return false;
    }

    //for insert
    public static String nextID(String DataBaseTableName,String sqlID_name,String prefix) throws BusinessException{
    	
    	String result = null;
        
    	try {
        	
			Connection conn = null;
            conn=DBUtil.getConnection();
			String sql = "SELECT "+sqlID_name+" from "+DataBaseTableName+"\r\n" + 
					"where "+sqlID_name+" like \""+prefix+"%\"\r\n" + 
					"ORDER BY "+sqlID_name+" DESC";
			java.sql.PreparedStatement pst=conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
            if(rs.next())
            	result = rs.getString(1);
            
            if(!Check.checkNullNoException(result)) {
            	int number = Integer.valueOf(result.substring(prefix.length()))+1;
            	result = prefix + String.valueOf(number);
            }else {
            	result = prefix+"1";
            }
            
            pst.close();
        	conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
    }
    
    public static Boolean isMatch(String stuff_id,String net_id) throws BusinessException {
    	String stuff_net = new StuffManager().findStuff(stuff_id).getNet_id();
    	if(Check.checkNullNoException(stuff_net))
    		return true;
    	else if(stuff_net.equals(net_id))
    		return true;
    	throw new BusinessException("你没有权限因为你不属于该网点"+net_id);
    }
    
    public static Boolean dayCheck(Date start,Date end) throws BusinessException {
    	if(start.after(end))
    		throw new BusinessException("日期输入有误");
		return true;
    }
    
    
}
