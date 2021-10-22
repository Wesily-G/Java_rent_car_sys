package DBManager;

import util.BusinessException;

public class Test {
    
    public static void main(String[] args) throws BusinessException {
		// TODO Auto-generated method stub
		System.out.println(new NetManager().nameToID("–ÏÍœ’æ"));

	}
    
//    view table code:
//    CREATE VIEW state_tbl AS
//    SELECT
//    car_tbl.car_id,
//    car_tbl.car_state,
//    car_model_tbl.model_name,
//    net_tbl.net_name
//    FROM
//    car_tbl ,
//    car_model_tbl ,
//    net_tbl
//    WHERE
//    car_tbl.model_id = car_model_tbl.model_id AND
//    car_tbl.net_id = net_tbl.net_id

    
}
