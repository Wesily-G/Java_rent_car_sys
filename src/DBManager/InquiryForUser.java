package DBManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.BeanCar;
import util.BusinessException;
import util.DBUtil;

public class InquiryForUser {

    public List<BeanCar> inquiryCar(String model_name,String net_name) throws BusinessException{
        
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
}
